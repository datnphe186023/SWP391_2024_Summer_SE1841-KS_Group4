package controller.academicstaff;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.user.User;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//servlet for list and create school year of academic staff
@WebServlet(name = "SchoolYearServlet", value = "/academicstaff/schoolyear")
public class SchoolYearServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        List<SchoolYear> schoolYears = schoolYearDAO.getAll();
        request.setAttribute("schoolYears", schoolYears);
        request.getRequestDispatcher("schoolYear.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        try{
            String action = request.getParameter("action");
            if (action == null){
                response.sendRedirect("schoolyear");
            } else if (action.equals("create")) {
                String name = request.getParameter("name");
                String startDateRaw = request.getParameter("startDate");
                String endDateRaw = request.getParameter("endDate");
                String description = request.getParameter("description");
                //username of an account is also that person's id in the organization
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                String username = user.getUsername();
                SchoolYear schoolYear = new SchoolYear();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = dateFormat.parse(startDateRaw);
                Date endDate = dateFormat.parse(endDateRaw);
                schoolYear.setName(name);
                schoolYear.setStartDate(startDate);
                schoolYear.setEndDate(endDate);
                schoolYear.setDescription(description);
                //therefore created id is also their id
//                schoolYear.setCreatedBy(username);
                schoolYearDAO.createSchoolYear(schoolYear);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}