package controller.headteacher;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.classes.Class;
import models.classes.ClassDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "headteacher/ClassServlet", value = "/headteacher/class")
public class ClassServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
            ClassDAO classDAO = new ClassDAO();
            request.setAttribute("schoolYears", schoolYearDAO.getAll());
            String schoolYearId = request.getParameter("schoolYearId");
            if (schoolYearId == null) {
                SchoolYear latestSchoolYear = schoolYearDAO.getLatest();
                schoolYearId = latestSchoolYear.getId();
            }
            List<Class> classes = classDAO.getBySchoolYear(schoolYearId);
            request.setAttribute("selectedSchoolYearId", schoolYearId);
            request.setAttribute("classes", classes);
            request.setAttribute("numberOfPendingClasses", classDAO.getByStatus("đang chờ xử lý").size());
            request.getRequestDispatcher("class.jsp").forward(request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}