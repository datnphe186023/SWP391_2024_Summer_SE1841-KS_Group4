package controller;

import models.user.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.user.User;

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
            switch (user.getRole_id()) {
                // role id = 1 : admin
                case 1:
                    response.sendRedirect("dashboard/dashboard_admin.jsp");
                    break;
                case 2:
                    // role id = 2 : academic staff
                    response.sendRedirect("dashboard/dashboard_staff.jsp");
                    break;
                case 3:
                    // role id = 3 : Head Teacher
                    response.sendRedirect("dashboard/dashboard_headteacher.jsp");
                    break;
                case 4:
                    // role id = 4 : Teacher 
                    response.sendRedirect("dashboard/dashboard_teacher.jsp");
                    break;
                case 5:
                    // role id = 5: Accountant
                    response.sendRedirect("dashboard/dashboard_accountant.jsp");
                    break;
                case 6:
                    // role id = 6 : Parent
                    response.sendRedirect("dashboard/dashboard_parent.jsp");
                    break;
                default:
                    // Nếu thông tin đăng nhập không chính xác, hiển thị thông báo lỗi trên trang đăng nhập
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
