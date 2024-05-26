package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ValidatePassword
 */
@WebServlet(name = "ValidatePassword", value = "/ValidatePassword")
public class ValidatePassword extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String value = request.getParameter("passValue");
        
        // Get the current session
        HttpSession session = request.getSession();
        String newPass = (String) session.getAttribute("passGen");

        RequestDispatcher dispatcher = null;
        
        // Check if user enter new password correct
        if (value.equals(newPass) ) {

            request.setAttribute("email", request.getParameter("email"));
            request.setAttribute("status", "success");
            dispatcher = request.getRequestDispatcher("newPassword.jsp");
            dispatcher.forward(request, response);

        } else {
            request.setAttribute("message", "wrong password");

            dispatcher = request.getRequestDispatcher("EnterNewPassword.jsp");
            dispatcher.forward(request, response);

        }

    }

}
