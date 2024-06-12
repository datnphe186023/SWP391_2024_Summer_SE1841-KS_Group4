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
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();

        //get school year list for dropdown select in JSP
        List<SchoolYear> schoolYears= schoolYearDAO.getAll();
        request.setAttribute("schoolYears", schoolYears);

        //get selected school year sent from JSP
        String schoolYearId = request.getParameter("schoolYearId");

        //school year is latest at default
        if (schoolYearId == null) {
            SchoolYear latestSchoolYear = schoolYearDAO.getLatest();
            schoolYearId = latestSchoolYear.getId();
        }
        request.setAttribute("selectedSchoolYear", schoolYearDAO.getSchoolYear(schoolYearId));

        //get application list
        IApplicationDAO applicationDAO = new ApplicationDAO();
        List<Application> applications = applicationDAO.getForStaff(schoolYearDAO.getSchoolYear(schoolYearId));
        request.setAttribute("applications", applications);

        request.getRequestDispatcher("applicationList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}