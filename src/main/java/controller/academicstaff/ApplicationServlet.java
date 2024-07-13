package controller.academicstaff;

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

@WebServlet(name = "academicstaff/ApplicationServlet", value = "/academicstaff/application")
public class ApplicationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get application list
        IApplicationDAO applicationDAO = new ApplicationDAO();
        List<Application> applications = applicationDAO.getForPersonnel("academic staff");
        request.setAttribute("applications", applications);

        request.getRequestDispatcher("applications.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}