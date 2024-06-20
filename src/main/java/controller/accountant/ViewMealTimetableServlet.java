package controller.accountant;

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
import java.util.List;

@WebServlet(name = "accountant/ViewFoodMenuServlet", urlPatterns={"/accountant/viewmealtimetable"})
public class ViewMealTimetableServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FoodMenuDAO foodMenuDAO = new FoodMenuDAO();
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();

        GradeDAO gradeDAO = new GradeDAO();

        List<SchoolYear> schoolYearList = schoolYearDAO.getAll();
        List<Grade> gradeList = gradeDAO.getAll();
        request.setAttribute("schoolYearList",schoolYearList );
        request.setAttribute("gradeList",gradeList );

        request.getRequestDispatcher("viewMealTimetable.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String grade = request.getParameter("grade");
        String week = request.getParameter("week");
        GradeDAO gradeDAO = new GradeDAO();
        String schoolyear = request.getParameter("schoolyear");
        FoodMenuDAO foodMenuDAO = new FoodMenuDAO();
        WeekDAO weekDAO = new WeekDAO();
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        List<SchoolYear> schoolYearList = schoolYearDAO.getAll();
        List<Grade> gradeList = gradeDAO.getAll();
        List<Week> weekList = weekDAO.getWeeks(schoolyear);
        List<FoodMenu> foodMenuList = foodMenuDAO.getAllFoodMenu();
        List<MenuDetail> menuDetailList = new ArrayList<>();

        if(grade!=null && week!=null && schoolyear!=null){
             menuDetailList = foodMenuDAO.getMenuDetails(grade,week,schoolyear);
        }
        request.setAttribute("sltedg",grade );
        request.setAttribute("sltedsy",schoolyear );
        request.setAttribute("sltedw",week );
        request.setAttribute("schoolYearList",schoolYearList );
        request.setAttribute("gradeList",gradeList );
        request.setAttribute("weekList",weekList);
        request.setAttribute("foodMenuList", foodMenuList);
        request.setAttribute("menuDetailList", menuDetailList);
        request.getRequestDispatcher("viewMealTimetable.jsp").forward(request, response);
    }
}