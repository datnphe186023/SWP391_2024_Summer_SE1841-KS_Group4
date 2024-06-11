package controller.academicstaff;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.application.Application;
import models.application.ApplicationDAO;

import java.io.IOException;

@WebServlet(name = "academicstaff/ApplicationDetailsServlet", value = "/academicstaff/applicationdetails")
public class ApplicationDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String applicationId = request.getParameter("id");
        ApplicationDAO appDAO = new ApplicationDAO();
        Application application = appDAO.getApplicationById(applicationId);
        request.setAttribute("application", application);
        request.getRequestDispatcher("applicationDetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}