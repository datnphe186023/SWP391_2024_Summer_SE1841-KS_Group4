package controller.headteacher;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.classes.Class;
import models.classes.ClassDAO;
import models.classes.IClassDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "headteacher/ClassServlet", value = "/headteacher/class")
public class ClassServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
            IClassDAO classDAO = new ClassDAO();
            //send list of all schoolYear to jsp
            request.setAttribute("schoolYears", schoolYearDAO.getAll());
            String schoolYearId = request.getParameter("schoolYearId");
            if (schoolYearId == null) {
                SchoolYear latestSchoolYear = schoolYearDAO.getLatest();
                schoolYearId = latestSchoolYear.getId();
            }
            String status = request.getParameter("status");
            List<Class> classes;
            if (status!=null && !status.equals("all") && !status.equals("đang chờ xử lý")){
                classes = classDAO.getByStatus(status, schoolYearId);
            } else {
                status = "all";
                classes = classDAO.getBySchoolYear(schoolYearId);
            }
            request.setAttribute("status", status);
            request.setAttribute("selectedSchoolYearId", schoolYearId);
            request.setAttribute("classes", classes);
            request.getRequestDispatcher("class.jsp").forward(request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}