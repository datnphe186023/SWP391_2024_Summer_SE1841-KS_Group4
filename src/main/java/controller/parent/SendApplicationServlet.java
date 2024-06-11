package controller.parent;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.application.ApplicationDAO;
import models.application.ApplicationType;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "parent/SendApplicationServlet", value = "/parent/sendapplication")
public class SendApplicationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ApplicationDAO applicationDAO = new ApplicationDAO();
        List<ApplicationType> applicationTypes = applicationDAO.getAllApplicationTypes("pupil");
        request.setAttribute("applicationTypes", applicationTypes);
        request.getRequestDispatcher("application.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}