package controller.teacher;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.foodmenu.FoodMenu;
import models.foodmenu.FoodMenuDAO;
import models.foodmenu.MenuDetail;
import models.grade.Grade;
import models.grade.GradeDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.week.Week;
import models.week.WeekDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "teacher/ViewMealTimetableServlet", urlPatterns={"/teacher/viewmealtimetable"})
public class ViewMealTimetableServlet extends HttpServlet {

    private FoodMenuDAO foodMenuDAO = new FoodMenuDAO();
    private SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
    private GradeDAO gradeDAO = new GradeDAO();
    private WeekDAO weekDAO = new WeekDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCommonAttributes(request);
        request.setAttribute("menustatus", "not-enough-data");
        request.getRequestDispatcher("viewMealTimetable.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String grade = request.getParameter("grade");
        String week = request.getParameter("week");
        String schoolyear = request.getParameter("schoolyear");

        List<MenuDetail> menuDetailList = new ArrayList<>();
        String menustatus = "not-enough-data";

        if (grade != null && week != null && schoolyear != null) {
            menuDetailList = foodMenuDAO.getMenuDetails(grade, week, schoolyear, "đã được duyệt");
            if (!menuDetailList.isEmpty()) {
                menustatus = "created";
            } else if (weekDAO.checkWeekInSchoolYear(week, schoolyear)) {
                menustatus = "not-created";
            }
        }

        Map<String, Map<String, String>> menuMap = preprocessMenuDetails(menuDetailList);
        setCommonAttributes(request);
        request.setAttribute("menustatus", menustatus);
        request.setAttribute("sltedg", grade);
        request.setAttribute("sltedsy", schoolyear);
        request.setAttribute("sltedw", week);
        request.setAttribute("menuDetailList", menuDetailList);
        request.setAttribute("menuMap", menuMap);
        request.getRequestDispatcher("viewMealTimetable.jsp").forward(request, response);
    }

    private void setCommonAttributes(HttpServletRequest request) {
        List<SchoolYear> schoolYearList = schoolYearDAO.getAll();
        List<Grade> gradeList = gradeDAO.getAll();
        request.setAttribute("schoolYearList", schoolYearList);
        request.setAttribute("gradeList", gradeList);

        String schoolyear = request.getParameter("schoolyear");
        if (schoolyear != null) {
            List<Week> weekList = weekDAO.getWeeks(schoolyear);
            request.setAttribute("weekList", weekList);
        }
    }

    private Map<String, Map<String, String>> preprocessMenuDetails(List<MenuDetail> menuDetailList) {
        Map<String, Map<String, String>> menuMap = new HashMap<>();
        for (MenuDetail menu : menuDetailList) {
            String timeOfDay = menu.getTimeslot().getName();
            String dayOfWeek = menu.getDay().convertToWeekDay();
            menuMap.computeIfAbsent(timeOfDay, k -> new HashMap<>())
                    .put(dayOfWeek, menu.getFoodMenu().getFoodDetails() != null ? menu.getFoodMenu().getFoodDetails() : "(Bữa trống)");
        }
        return menuMap;
    }
}