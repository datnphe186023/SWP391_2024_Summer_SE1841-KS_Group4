package controller.headteacher;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.foodmenu.FoodMenu;
import models.foodmenu.FoodMenuDAO;
import models.foodmenu.IFoodMenuDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProcessFoodMenuServlet", urlPatterns={"/headteacher/processfoodmenu"})
public class ProcessFoodMenuServlet extends HttpServlet {
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
        List<FoodMenu> foodMenuList = foodMenuDAO.getAllFoodMenu();
        request.setAttribute("foodMenuList", foodMenuList);
        request.getRequestDispatcher("processFoodMenu.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        IFoodMenuDAO foodMenuDAO = new FoodMenuDAO();
            String foodid = request.getParameter("foodid");
        System.out.println(foodid);
            String action = request.getParameter("action");
            if(action.equalsIgnoreCase("accept")){
                foodMenuDAO.AcceptorDenyFoodMenu(foodid,"đã được duyệt");
                session.setAttribute("toastType", "success");
                session.setAttribute("toastMessage", "Đã cập nhật dữ liệu ! đã duyệt suất ăn");

                response.sendRedirect("processfoodmenu");
            }else{
                foodMenuDAO.AcceptorDenyFoodMenu(foodid,"không được duyệt");
                session.setAttribute("toastType", "fail");
                session.setAttribute("toastMessage", "Đã cập nhật dữ liệu ! không duyệt suất ăn");
                response.sendRedirect("processfoodmenu");
            }
    }
}