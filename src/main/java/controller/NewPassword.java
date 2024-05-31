package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.user.UserDAO;

@WebServlet("/newPassword")
public class NewPassword extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Handles the HTTP POST method. This method processes the password reset
     * request.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        // Get the current session
        HttpSession session = request.getSession();

        // Retrieve the new password and confirm password from the request
        String newPassword = request.getParameter("password");
        String confPassword = request.getParameter("confPassword");

        // Check if new password and confirm password are not null and match
        if (newPassword != null && newPassword.equals(confPassword)) {

            // Get the user's email from the session
            String username = (String) session.getAttribute("username");

            // Attempt to update the user's password
            boolean success = userDAO.updateNewPassword(newPassword, userDAO.getByUsernameOrEmail(username).getId());

            if (success) {
                request.setAttribute("status", "Đã đổi mật khẩu thành công !");
            } else {
                request.setAttribute("status", "Đổi mật khẩu thất bại!");
            }
        } else {
            // Passwords do not match
            request.setAttribute("err", "Mật khẩu không khớp!");
            request.getRequestDispatcher("newPassword.jsp").forward(request, response);
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
