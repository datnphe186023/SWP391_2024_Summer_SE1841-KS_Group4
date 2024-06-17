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
import models.day.TimeInDay;
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
import java.time.LocalDate;
import java.util.Date;

import java.util.Enumeration;
import java.util.List;

@WebServlet(name = "CreateFoodmenuServlet",urlPatterns={"/accountant/createfoodmenu"})
public class CreateFoodmenuServlet extends HttpServlet {
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
        List<Week> listWeek = weekDAO.getWeeks(yearDAO.getSchoolYearByDate(currentDate).getId());

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
        List<Day> dayList = dayDAO.getDayByWeek(weekId);

        request.setAttribute("dateWeek", dateWeek);
        request.setAttribute("dayList", dayList);
        request.setAttribute("subList", subList);
        request.setAttribute("foodMenuList", foodMenuList);
        request.setAttribute("classList", classList);
        request.setAttribute("listTimeslot", listTimeslot);

        request.setAttribute("newYear", yearDAO.getSchoolYearByDate(currentDate));

        request.setAttribute("listWeek", listWeek);
        request.setAttribute("listGrade", listGrade);
        request.setAttribute("selectedGradeId", selectedGradeId);

        request.getRequestDispatcher("createFoodmenu.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String action = request.getParameter("action");
            if (action == null) {

                response.sendRedirect("createfoodmenu");
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

                String status = "chưa xét duyệt";
                String selectedGradeId = request.getParameter("gradeid");
                System.out.println(selectedGradeId);


                // Retrieve all timeslot and subject selections
                Enumeration<String> parameterNames = request.getParameterNames();
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

                    response.sendRedirect("createfoodmenu");
                } else {
                    session.setAttribute("toastType", "error");
                    session.setAttribute("toastMessage", "Không có dữ liệu được chọn. Vui lòng không để trống !");
                    response.sendRedirect("createfoodmenu");

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