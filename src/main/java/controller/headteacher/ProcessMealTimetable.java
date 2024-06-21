package controller.headteacher;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.foodmenu.FoodMenu;
import models.foodmenu.FoodMenuDAO;
import models.foodmenu.IFoodMenuDAO;
import models.foodmenu.MenuDetail;
import models.grade.GradeDAO;
import models.grade.IGradeDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYearDAO;
import models.week.IWeekDAO;
import models.week.WeekDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProcessMealTimetable",urlPatterns={"/headteacher/processmealtimetable"})
public class ProcessMealTimetable extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ISchoolYearDAO yearDAO = new SchoolYearDAO();
        IWeekDAO weekDAO = new WeekDAO();
        IGradeDAO gradeDAO = new GradeDAO();
            String weekId = request.getParameter("weekId");
            String schoolyearId = request.getParameter("schoolyearId");
            String gradeId = request.getParameter("gradeId");
        System.out.println(weekId);
        System.out.println(schoolyearId);
        System.out.println(gradeId);
        IFoodMenuDAO foodMenuDAO = new FoodMenuDAO();
        List<MenuDetail>  menuDetails = foodMenuDAO.getMenuDetails(gradeId,weekId ,schoolyearId ,"đang chờ xử lý");
        request.setAttribute("week",weekDAO.getWeek(weekId));
        request.setAttribute("schoolyear",yearDAO.getSchoolYear(schoolyearId));
        request.setAttribute("grade",gradeDAO.getGrade(gradeId));
        request.setAttribute("menuDetailList", menuDetails);
        request.getRequestDispatcher("processMealTimetable.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        IFoodMenuDAO foodMenuDAO = new FoodMenuDAO();
    String action = request.getParameter("action");
    String[] menuid = request.getParameterValues("menuid");
    if(action.equalsIgnoreCase("accept")){
        for(int i =0; i<menuid.length;i++){
            foodMenuDAO.AcceptorDenyMenu(menuid[i],"đã được duyệt");
        }
        session.setAttribute("toastType", "success");
        session.setAttribute("toastMessage", "Đã cập nhật dữ liệu ! đã duyệt thực đơn");

        response.sendRedirect("waitlistmealtimetable");
    }else{
        for(int i =0; i<menuid.length;i++){
            foodMenuDAO.AcceptorDenyMenu(menuid[i],"không được duyệt");
        }
        session.setAttribute("toastType", "success");
        session.setAttribute("toastMessage", "Đã cập nhật dữ liệu ! đã từ chối thực đơn");
        response.sendRedirect("waitlistmealtimetable");
    }
    }
}