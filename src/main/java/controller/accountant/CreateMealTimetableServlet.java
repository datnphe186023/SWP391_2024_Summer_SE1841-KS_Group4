package controller.accountant;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.classes.Class;
import models.classes.ClassDAO;
import models.classes.IClassDAO;
import models.day.Day;
import models.day.DayDAO;
import models.day.IDayDAO;
import models.foodmenu.FoodMenu;
import models.foodmenu.FoodMenuDAO;
import models.foodmenu.IFoodMenuDAO;

import models.foodmenu.MenuDetail;

import models.grade.Grade;
import models.grade.GradeDAO;
import models.grade.IGradeDAO;
import models.personnel.IPersonnelDAO;
import models.personnel.PersonnelDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.subject.ISubjectDAO;
import models.subject.Subject;
import models.subject.SubjectDAO;
import models.timeslot.ITimeslotDAO;
import models.timeslot.Timeslot;
import models.timeslot.TimeslotDAO;
import models.timetable.ITimetableDAO;
import models.timetable.Timetable;
import models.timetable.TimetableDAO;
import models.user.User;
import models.week.IWeekDAO;
import models.week.Week;
import models.week.WeekDAO;

import java.io.IOException;

import java.text.DecimalFormat;
import java.time.Instant;
import java.util.*;

@WebServlet(name = "CreateMealTimetableServlet",urlPatterns={"/accountant/createmealtimetable"})
public class CreateMealTimetableServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String toastType = "", toastMessage = "";
        if (session.getAttribute("toastType") != null) {
            toastType = session.getAttribute("toastType").toString();
            toastMessage = session.getAttribute("toastMessage").toString();
        }
        session.removeAttribute("toastType");
        session.removeAttribute("toastMessage");
        request.setAttribute("toastType", toastType);
        request.setAttribute("toastMessage", toastMessage);

        ISchoolYearDAO yearDAO = new SchoolYearDAO();
        ITimeslotDAO timeslotDAO = new TimeslotDAO();
        IWeekDAO weekDAO = new WeekDAO();
        IGradeDAO gradeDAO = new GradeDAO();
        IClassDAO classDAO = new ClassDAO();
        ISubjectDAO subjectDAO = new SubjectDAO();
        IDayDAO dayDAO = new DayDAO();
        IFoodMenuDAO foodMenuDAO = new FoodMenuDAO();

        String selectedGradeId = request.getParameter("gradeId");
        String weekId = request.getParameter("weekId");


        Date currentDate = Date.from(Instant.now());
        // get list grade
        List<Grade> listGrade = gradeDAO.getAll();
        // get list week from now
        List<Week> listWeek = new ArrayList<>();

        // get start date and end date
        Week dateWeek = weekDAO.getWeek(weekId);

        //get list of foodmenu
        List<FoodMenu> foodMenuList = foodMenuDAO.getAllFoodMenu();

        // get timeslot
        List<Timeslot> listTimeslot = timeslotDAO.getFoodTimeslots();
        // get school year latest
        String newYear = weekDAO.getYearByWeek(weekId);
        SchoolYear schoolYear = yearDAO.getSchoolYear(newYear);
        // get list subject by grade id
        List<Subject> subList = subjectDAO.getSubjectsByGradeId(selectedGradeId);
        // get list class by grade id
        List<Class> classList = classDAO.getClassByGradeId(selectedGradeId);
        // get list day by week
        List<Day> dayList = dayDAO.getFullDayOfWeek(weekId);
        boolean enable = true ;
        for (Day day : dayList) {
            if ( foodMenuDAO.existsMealTimetableForGradeInCurrentWeek(selectedGradeId, day.getId())) {
                enable = false;
                break;
            }
        }
        try {
            listWeek = weekDAO.getWeeks(yearDAO.getCloestSchoolYears().getId());
        }catch(Exception e){
            request.setAttribute("listTimeslot", listTimeslot);
            enable = false;
            request.setAttribute("enable",enable );
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Không tìm thấy năm học hiện tại!");
            request.setAttribute("status", "năm học không tồn tại !");
            System.out.println("năm học không tồn tại !");
            request.getRequestDispatcher("createMealTimetable.jsp").forward(request, response);
            return;
        }
        List<MenuDetail> menuDetails = new ArrayList<>();
        String status = "";
        if (enable == false){
           menuDetails = foodMenuDAO.getMenuDetailsforCreate(selectedGradeId,weekId,yearDAO.getCloestSchoolYears().getId());
           status = menuDetails.get(0).getStatus();
           request.setAttribute("menuDetails", menuDetails);
           request.setAttribute("status", status);
        }
        System.out.println(enable);
        request.setAttribute("dateWeek", dateWeek);
        request.setAttribute("dayList", dayList);
        request.setAttribute("subList", subList);
        request.setAttribute("foodMenuList", foodMenuList);
        request.setAttribute("classList", classList);
        request.setAttribute("listTimeslot", listTimeslot);

        request.setAttribute("newYear", yearDAO.getCloestSchoolYears());

        request.setAttribute("listWeek", listWeek);
        request.setAttribute("listGrade", listGrade);
        request.setAttribute("selectedGradeId", selectedGradeId);
        request.setAttribute("weekId", weekId);
        request.setAttribute("enable",enable );
        request.getRequestDispatcher("createMealTimetable.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String action = request.getParameter("action");
            if (action == null) {

                response.sendRedirect("createmealtimetable");
            } else if (action.equals("create-foodmenu")) {

                HttpSession session = request.getSession();
                ITimetableDAO timetableDAO = new TimetableDAO();
                IPersonnelDAO personnelDAO = new PersonnelDAO();
                ITimeslotDAO timeslotDAO = new TimeslotDAO();
                ISubjectDAO subjectDAO = new SubjectDAO();
                IDayDAO dayDAO = new DayDAO();
                IClassDAO classDAO = new ClassDAO();

                IFoodMenuDAO foodMenuDAO = new FoodMenuDAO();
                IGradeDAO gradeDAO = new GradeDAO();
                Timetable timetable = new Timetable();
                MenuDetail menuDetail = new MenuDetail();
                User user = (User) session.getAttribute("user");

                String status = "đang chờ xử lý";
                String selectedGradeId = request.getParameter("gradeid");
                String weekId = request.getParameter("weekId");

                // Thu thập tất cả các `dayId` từ tham số đầu vào
                Enumeration<String> parameterNames = request.getParameterNames();
                Set<String> dayIds = new HashSet<>();
                while (parameterNames.hasMoreElements()) {
                    String paramName = parameterNames.nextElement();
                    if (paramName.startsWith("timeslotId_")) {
                        String[] parts = paramName.split("_");
                        String dayId = parts[1];
                        dayIds.add(dayId);
                    }
                }

                // Kiểm tra thời khóa biểu tồn tại cho tất cả `dayId`
                for (String dayId : dayIds) {
                    if ( foodMenuDAO.existsMealTimetableForGradeInCurrentWeek(selectedGradeId, dayId)) {
                        session.setAttribute("toastType", "error");
                        session.setAttribute("toastMessage", "Thực đơn của khối này đã được tạo!");
                        response.sendRedirect("createmealtimetable");
                        return; // Dừng lại nếu thời khóa biểu đã tồn tại
                    }
                }

                // Retrieve all timeslot and subject selections
                 parameterNames = request.getParameterNames();
                int entryCounter = 1;
                boolean entryCreated = false; // Flag to check if any entries are created
                while (parameterNames.hasMoreElements()) {
                    String paramName = parameterNames.nextElement();
                    if (paramName.startsWith("timeslotId_")) {
                        String timeslotIdValue = request.getParameter(paramName);
                        if (!timeslotIdValue.isEmpty()) {
                            String[] parts = paramName.split("_");
                            String dayId = parts[1];
                            String timeslotId = parts[2];

                            String FoodmenuId = timeslotIdValue; // The selected subject ID
                            String menuId = genID();
                            menuDetail.setId(menuId);
                            menuDetail.setFoodMenu(foodMenuDAO.getFoodMenu(FoodmenuId));
                            menuDetail.setGrade(gradeDAO.getGrade(selectedGradeId));
                            menuDetail.setTimeslot(timeslotDAO.getTimeslotById(timeslotId));
                            menuDetail.setStatus(status);
                            menuDetail.setDay(dayDAO.getDayByID(dayId));
                            // Insert the timetable entry into the database
                            foodMenuDAO.createMenuDetail(menuDetail);

                            entryCreated = true; // An entry was created
                        }
                    }
                }

                if (entryCreated) {
                    session.setAttribute("toastType", "success");
                    session.setAttribute("toastMessage", "Thời khóa biểu đã được tạo thành công.");

                    response.sendRedirect("createmealtimetable?weekId="+weekId+"&gradeId="+selectedGradeId);
                } else {

                    session.setAttribute("toastType", "error");
                    session.setAttribute("toastMessage", "Không có dữ liệu được chọn. Vui lòng không để trống !");
                    response.sendRedirect("createmealtimetable");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

          //  request.getRequestDispatcher("error.jsp").forward(request, response);
        }



    }
    private String genID(){
        String id ="";
        int newid = 0 ;
        IFoodMenuDAO foodMenuDAO = new FoodMenuDAO();
        newid= foodMenuDAO.getTotalID()+1;
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        id= "MD" + decimalFormat.format(newid);
        return id;
    }


}