package controller.academicstaff;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.personnel.IPersonnelDAO;
import models.personnel.Personnel;
import models.personnel.PersonnelDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.user.User;
import utils.Helper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

//servlet for list and create school year of academic staff
@WebServlet(name = "SchoolYearServlet", value = "/academicstaff/schoolyear")
public class SchoolYearServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        List<SchoolYear> schoolYears = schoolYearDAO.getAll();
        HttpSession session = request.getSession();
        String toastType = "", toastMessage = "";
        if (session.getAttribute("toastType") != null) {
            toastType = session.getAttribute("toastType").toString();
            toastMessage = session.getAttribute("toastMessage").toString();
        }
        session.removeAttribute("toastType");
        session.removeAttribute("toastMessage");
        request.setAttribute("toastType", toastType);
        request.setAttribute("toastMessage", toastMessage);
        request.setAttribute("schoolYears", schoolYears);
        request.getRequestDispatcher("schoolYear.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        try{
            String action = request.getParameter("action");
            if (action == null){
                response.sendRedirect("schoolyear");
            } else if (action.equals("create")) {
                String startDateRaw = request.getParameter("startDate");
                String endDateRaw = request.getParameter("endDate");
                String description = request.getParameter("description");
                description = Helper.formatString(description);
                //username of an account is also that person's id in the organization
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                String username = user.getUsername();
                SchoolYear schoolYear = new SchoolYear();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = dateFormat.parse(startDateRaw);
                Date endDate = dateFormat.parse(endDateRaw);
                schoolYear.setName(getSchoolYearName(startDate, endDate));
                schoolYear.setStartDate(startDate);
                schoolYear.setEndDate(endDate);
                schoolYear.setDescription(description);
                //therefore created id is also their id
                IPersonnelDAO personnelDAO = new PersonnelDAO();
                Personnel personnel = personnelDAO.getPersonnel(username);
                schoolYear.setCreatedBy(personnel);
                String result = schoolYearDAO.createNewSchoolYear(schoolYear);
                if (result.equals("success")) {
                    session.setAttribute("toastType", "success");
                    session.setAttribute("toastMessage", "Thao tác thành công");
                } else {
                    session.setAttribute("toastType", "error");
                    session.setAttribute("toastMessage", result);
                }
                response.sendRedirect("schoolyear");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private String getSchoolYearName(Date startDate, Date endDate) {
        // Convert Date to LocalDate
        LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Extract the year from each LocalDate
        int startYear = startLocalDate.getYear();
        int endYear = endLocalDate.getYear();
        if (endYear == startYear) {
           return Helper.formatString(startYear + " ");
        }

        // Generate the school year string
        return startYear + "-" + endYear;
    }
}