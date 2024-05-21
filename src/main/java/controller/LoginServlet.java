package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.user.User;
import models.user.UserDAO;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get data from login form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Check data login
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByUsernamePassword(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            // Login success
            switch (user.getRoleId()) {
                // role id = 0 : admin
                case 0:
                    response.sendRedirect("dashboard/admin");
                    break;
                case 1:
                    // role id = 1 : Head Teacher
                    response.sendRedirect("dashboard/headteacher");
                    break;
                case 2:
                    // role id = 2 : academic staff
                    response.sendRedirect("dashboard/academicstaff");
                    break;
                case 3:
                    // role id = 3 : Accountant
                    response.sendRedirect("dashboard/accountant");
                    break;
                case 4:
                    // role id = 4: Teacher 
                    response.sendRedirect("dashboard/teacher");
                    break;
                case 5:
                    // role id = 5 : Parent
                    response.sendRedirect("dashboard/parent");
                    break;
                default:
                    request.setAttribute("error", "Invalid username or password");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    break;
            }
        } else {
            // Nếu thông tin đăng nhập không chính xác, hiển thị thông báo lỗi trên trang đăng nhập
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
