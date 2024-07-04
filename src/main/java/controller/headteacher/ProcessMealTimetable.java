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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProcessMealTimetable",urlPatterns={"/headteacher/processmealtimetable"})
public class ProcessMealTimetable extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String toastType = "", toastMessage = "";

        ISchoolYearDAO yearDAO = new SchoolYearDAO();
        IWeekDAO weekDAO = new WeekDAO();
        IGradeDAO gradeDAO = new GradeDAO();
        boolean enable = true;
            String weekId = request.getParameter("weekId");
            String schoolyearId = request.getParameter("schoolyearId");
            String gradeId = request.getParameter("gradeId");
        if (session.getAttribute("toastType") != null) {
            toastType = session.getAttribute("toastType").toString();
            toastMessage = session.getAttribute("toastMessage").toString();
            weekId = session.getAttribute("weekId").toString();
            schoolyearId = session.getAttribute("schoolyearId").toString();
            gradeId = session.getAttribute("gradeId").toString();
            enable = false;
        }
        session.removeAttribute("toastType");
        session.removeAttribute("toastMessage");
        session.removeAttribute("gradeId");
        session.removeAttribute("weekId");
        session.removeAttribute("schoolyearId");
        request.setAttribute("toastType", toastType);
        request.setAttribute("toastMessage", toastMessage);

        System.out.println(weekId);
        System.out.println(schoolyearId);
        System.out.println(gradeId);
        IFoodMenuDAO foodMenuDAO = new FoodMenuDAO();
        List<MenuDetail>  menuDetails = new ArrayList<>();
        if(enable == true){
             menuDetails = foodMenuDAO.getMenuDetails(gradeId,weekId ,schoolyearId ,"đang chờ xử lý");
        }else if(enable == false && toastType.equalsIgnoreCase("success") ){
            menuDetails = foodMenuDAO.getMenuDetails(gradeId,weekId ,schoolyearId ,"đã được duyệt");
        }else if(enable == false && toastType.equalsIgnoreCase("fail") ){
            menuDetails = foodMenuDAO.getMenuDetails(gradeId,weekId ,schoolyearId ,"không được duyệt");
        }
        request.setAttribute("enable", enable);
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
        String weekId = request.getParameter("sltedw");
        String schoolyearId = request.getParameter("sltedsy");
        String gradeId = request.getParameter("sltedg");
    if(action.equalsIgnoreCase("accept")){
        for(int i =0; i<menuid.length;i++){
            foodMenuDAO.AcceptorDenyMenu(menuid[i],"đã được duyệt");
        }
        session.setAttribute("toastType", "success");
        session.setAttribute("toastMessage", "Đã cập nhật dữ liệu ! đã duyệt thực đơn");

    }else{
        for(int i =0; i<menuid.length;i++){
            foodMenuDAO.AcceptorDenyMenu(menuid[i],"không được duyệt");
        }
        session.setAttribute("toastType", "fail");
        session.setAttribute("toastMessage", "Đã cập nhật dữ liệu ! không duyệt thực đơn");

    }
        session.setAttribute("weekId", weekId);
        session.setAttribute("schoolyearId", schoolyearId);
        session.setAttribute("gradeId", gradeId);
        response.sendRedirect("processmealtimetable");

    }
}