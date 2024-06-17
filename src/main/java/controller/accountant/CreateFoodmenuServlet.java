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
        // get list grade
        List<Grade> listGrade = gradeDAO.getAll();
        // get list week from now
        List<Week> listWeek = weekDAO.getWeeksFromNow();
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
        request.setAttribute("newYear", schoolYear);
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
                response.sendRedirect("timetable");
            } else if (action.equals("create-timetable")) {
                HttpSession session = request.getSession();
                ITimetableDAO timetableDAO = new TimetableDAO();
                IPersonnelDAO personnelDAO = new PersonnelDAO();
                ITimeslotDAO timeslotDAO = new TimeslotDAO();
                ISubjectDAO subjectDAO = new SubjectDAO();
                IDayDAO dayDAO = new DayDAO();
                IClassDAO classDAO = new ClassDAO();
                Timetable timetable = new Timetable();

                User user = (User) session.getAttribute("user");
                // Get the primary form parameters

                String classId = request.getParameter("classId");

                timetable.setaClass(classDAO.getClassById(classId));
                // Define other required parameters
                timetable.setCreatedBy(personnelDAO.getPersonnelByUserId(user.getId()));
                String status = "chưa xét duyệt";
                timetable.setStatus(status);
                String note = "";
                timetable.setNote(note);
                timetable.setTeacher(personnelDAO.getTeacherByClass(classId));

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
                            String subjectId = timeslotIdValue; // The selected subject ID
                            String timetableId = "TB" + classId + "_" + entryCounter++;
                            timetable.setId(timetableId);
                            timetable.setDay(dayDAO.getDayByID(dayId));
                            timetable.setTimeslot(timeslotDAO.getTimeslotById(timeslotId));
                            timetable.setSubject(subjectDAO.getSubjectBySubjectId(subjectId));
                            // Insert the timetable entry into the database
                            timetableDAO.createTimetable(timetable);
                            entryCreated = true; // An entry was created
                        }
                    }
                }

                if (entryCreated) {
                    session.setAttribute("toastType", "success");
                    session.setAttribute("toastMessage", "Thời khóa biểu đã được tạo thành công.");
                    response.sendRedirect("timetable");
                } else {
                    session.setAttribute("toastType", "error");
                    session.setAttribute("toastMessage", "Không có dữ liệu được chọn. Vui lòng không để trống !");
                    response.sendRedirect("timetable");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}