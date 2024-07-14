package controller.teacher;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.application.Application;
import models.application.ApplicationDAO;
import models.application.IApplicationDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;

import java.io.IOException;
import java.util.List;

//this is servlet for showing waiting for process application, which are sent from pupil
@WebServlet(name = "teacher/ApplicationServlet", value = "/teacher/application")
public class ApplicationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = request.getParameter("status");

        //get application list
        IApplicationDAO applicationDAO = new ApplicationDAO();
        List<Application> applications;
        if (status==null || status.equals("all")) {
            applications = applicationDAO.getForPersonnel("teacher");
        } else {
            applications = applicationDAO.getForPersonnelWithStatus("teacher", status);
        }
        request.setAttribute("applications", applications);

        request.getRequestDispatcher("applications.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}