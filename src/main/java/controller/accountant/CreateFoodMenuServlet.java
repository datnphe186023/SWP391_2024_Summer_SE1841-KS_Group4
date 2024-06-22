package controller.accountant;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.foodmenu.FoodMenu;
import models.foodmenu.FoodMenuDAO;
import models.foodmenu.IFoodMenuDAO;
import utils.Helper;

import java.io.IOException;
import java.util.List;
//this servlet is for listing food menu and create new food menu
@WebServlet(name = "accountant/CreateFoodMenuServlet", value = "/accountant/foodmenus")
public class CreateFoodMenuServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //getting toast message sent from do post after create food menu
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
        //get list of food menus
        IFoodMenuDAO foodMenuDAO = new FoodMenuDAO();
        List<FoodMenu> foodMenus = foodMenuDAO.getAllFoodMenu("FM000000");
        request.setAttribute("foodMenus", foodMenus);
        request.getRequestDispatcher("foodMenu.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("create")) {
            String details = Helper.formatString(request.getParameter("details"));
            FoodMenu foodMenu = new FoodMenu();
            foodMenu.setFoodDetails(details);
            IFoodMenuDAO foodMenuDAO = new FoodMenuDAO();
            String result = foodMenuDAO.createFoodMenu(foodMenu);

            //handling toast message after create new food menu
            HttpSession session = request.getSession();
            if (result.equals("success")) {
                session.setAttribute("toastType", "success");
                session.setAttribute("toastMessage", "Thao tác thành công");
            } else {
                session.setAttribute("toastType", "error");
                session.setAttribute("toastMessage", result);
            }
            response.sendRedirect("foodmenus");
        } else if (action.equals("edit")) {
            String details = Helper.formatString(request.getParameter("details"));
            String foodMenuId = request.getParameter("foodMenuId");
            IFoodMenuDAO foodMenuDAO = new FoodMenuDAO();
            FoodMenu foodMenu = new FoodMenu();
            foodMenu.setId(foodMenuId);
            foodMenu.setFoodDetails(details);
            String result = foodMenuDAO.editFoodMenu(foodMenu);
            //handling toast message after create new food menu
            HttpSession session = request.getSession();
            session.setAttribute("foodMenuId", foodMenuId);
            if (result.equals("success")) {
                session.setAttribute("toastType", "success");
                session.setAttribute("toastMessage", "Thao tác thành công");
            } else {
                session.setAttribute("toastType", "error");
                session.setAttribute("toastMessage", result);
            }
            session.setAttribute("foodMenu", foodMenu);
            response.sendRedirect("foodmenus");
        }
    }
}