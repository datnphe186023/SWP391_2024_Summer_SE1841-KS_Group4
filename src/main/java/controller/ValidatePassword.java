package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.user.IUserDAO;
import models.user.UserDAO;

/**
 * Servlet implementation class ValidatePassword
 */
@WebServlet(name = "ValidatePassword", value = "/ValidatePassword")
public class ValidatePassword extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPassword = request.getParameter("currentPassword").trim();

        RequestDispatcher dispatcher = null;
        IUserDAO userDAO = new UserDAO();
        HttpSession session = request.getSession();
        // Check if user enter new password correct
        try {
            if (userDAO.checkPassword(currentPassword, (String) session.getAttribute("username"))) {
                request.setAttribute("status", "success");
                dispatcher = request.getRequestDispatcher("newPassword.jsp");
                dispatcher.forward(request, response);

            } else {
                request.setAttribute("error", "Sai mật khẩu, vui lòng thử lại !");
                dispatcher = request.getRequestDispatcher("enterNewPassword.jsp");
                dispatcher.forward(request, response);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
