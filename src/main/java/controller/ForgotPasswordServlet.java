package controller;

import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.user.IUserDAO;
import models.user.User;
import models.user.UserDAO;

/**
 * Servlet implementation class ForgotPassword
 */
public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("heelo");
        String key = request.getParameter("email");
        RequestDispatcher dispatcher = null;
        HttpSession session = request.getSession();
        IUserDAO userDAO = new UserDAO();
        User user = userDAO.getByUsernameOrEmail(key);
        if(user == null){
            request.setAttribute("error", "Email hoặc tài khoản bạn nhập không tồn tại vui lòng nhập lại!");
            request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
        }
        else if (key != null || !key.equals("")) {
            // update password generate random
            userDAO.resetPassword(key);
            session.setAttribute("username", user.getUsername());
            dispatcher = request.getRequestDispatcher("enterNewPassword.jsp");
            request.setAttribute("message", "Mật khẩu đã được gửi đến bạn, vui lòng kiểm tra email !");
            dispatcher.forward(request, response);
        }

    }

}
