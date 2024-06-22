package controller.headteacher;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.foodmenu.FoodMenuDAO;
import models.foodmenu.IFoodMenuDAO;
import models.grade.Grade;
import models.grade.GradeDAO;
import models.grade.IGradeDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.week.IWeekDAO;
import models.week.Week;
import models.week.WeekDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "WaitlistMealTimetable",urlPatterns={"/headteacher/waitlistmealtimetable"})
public class WaitlistMealTimetable extends HttpServlet {
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
        IFoodMenuDAO foodMenuDAO = new FoodMenuDAO();
        ISchoolYearDAO yearDAO = new SchoolYearDAO();
        IWeekDAO weekDAO = new WeekDAO();
        IGradeDAO gradeDAO = new GradeDAO();
        List<String> dataList = new ArrayList<>();
        try {
            dataList = foodMenuDAO.getMenuDetailsforProcess(yearDAO.getCloestSchoolYears().getId());
        }catch(Exception e){

            request.setAttribute("toastType", "fail");
            request.setAttribute("toastMessage", "Không tìm thấy năm học hiện tại!");
            request.setAttribute("status", "năm học không tồn tại !");
            request.getRequestDispatcher("waitlistMealTimetable.jsp").forward(request, response);
            return;
        }
        //List<String> dataList = foodMenuDAO.getMenuDetailsforProcess(yearDAO.getCloestSchoolYears().getId());
        List<Week> weekId = new ArrayList<>();
        List<SchoolYear> schoolyearId = new ArrayList<>();;
        List<Grade> gradeId = new ArrayList<>();
        for(int i=0; i<dataList.size(); i++){
            String[] parts = dataList.get(i).split("-");
            System.out.println(parts[0]);
            System.out.println(parts[1]);
            System.out.println(parts[2]);
            weekId.add(weekDAO.getWeek(parts[0]));
            schoolyearId.add(yearDAO.getSchoolYear(parts[1]));
            gradeId.add(gradeDAO.getGrade(parts[2]));

        }
        request.setAttribute("weekId",weekId);
        request.setAttribute("schoolyearId",schoolyearId);
        request.setAttribute("gradeId",gradeId);

            request.getRequestDispatcher("waitlistMealTimetable.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}