package controller;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.user.User;
import models.user.UserDAO;

/**
 * Servlet implementation class ForgotPassword
 */
public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("forgotpassword.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String key = request.getParameter("email");
        RequestDispatcher dispatcher = null;
        HttpSession session = request.getSession();
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getByUsernameOrEmail(key);
        if(user == null){
            request.setAttribute("error", "Email bạn nhập không tồn tại vui lòng nhập lại!");
            request.getRequestDispatcher("forgotpassword.jsp").forward(request, response);
        }
        else if (key != null || !key.equals("")) {
            // update password generate random
            userDAO.resetPassword(key);
            session.setAttribute("username", user.getUsername());
            dispatcher = request.getRequestDispatcher("EnterNewPassword.jsp");
            request.setAttribute("message", "Mật khẩu đã được gửi đến bạn, vui lòng kiểm tra email");
            dispatcher.forward(request, response);
        }

    }

}
