package controller.academicstaff;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.classes.Class;
import models.classes.ClassDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ClassServlet", value = "/academicstaff/class")
public class ClassServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClassDAO classDAO = new ClassDAO();
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        try{
            String schoolYearId = request.getParameter("schoolYear");
            if (schoolYearId == null) {
                SchoolYear latestSchoolYear = schoolYearDAO.getLatest();
                schoolYearId = latestSchoolYear.getId();
            }
            List<Class> classes = classDAO.getBySchoolYear(schoolYearId);
            request.setAttribute("classes", classes);
            List<SchoolYear> schoolYears = schoolYearDAO.getAll();
            request.setAttribute("schoolYears", schoolYears);
            request.setAttribute("schoolYearId", schoolYearId);
            request.getRequestDispatcher("class.jsp").forward(request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}