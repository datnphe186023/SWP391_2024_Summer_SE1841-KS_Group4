package controller.academicstaff;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.day.Day;
import models.day.IDayDAO;
import models.day.DayDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.user.User;
import models.week.IWeekDAO;
import models.week.WeekDAO;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import models.personnel.IPersonnelDAO;
import models.personnel.Personnel;
import models.personnel.PersonnelDAO;

@WebServlet(name = "academicstaff/ViewAttendanceServlet", value = "/academicstaff/attendance")
public class ViewAttendanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //send list of school years
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        List<SchoolYear> schoolYears = schoolYearDAO.getAll();
        request.setAttribute("schoolYears", schoolYears);

        //send list of weeks
        String schoolYearId = request.getParameter("schoolYearId");
        IWeekDAO weekDAO = new WeekDAO();
        if (schoolYearId == null) {
            schoolYearId = schoolYearDAO.getLatest().getId();
        }
        request.setAttribute("weeks", weekDAO.getWeeks(schoolYearId));
        request.setAttribute("schoolYearId", schoolYearId);

        //get day list within that week
        String weekId = request.getParameter("weekId");
        if (weekId == null) {
            weekId = weekDAO.getCurrentWeek(new Date());
        }
        request.setAttribute("weekId", weekId);
        IDayDAO dayDAO = new DayDAO();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        request.setAttribute("personnelId", user.getUsername());
        IPersonnelDAO personnelDAO = new PersonnelDAO();
        Personnel personnel = personnelDAO.getPersonnel(user.getUsername());
        request.setAttribute("personnelFullName", personnel.getLastName()+" "+personnel.getFirstName());
        List<Day> days = dayDAO.getDayByWeek(weekId);
        request.setAttribute("days", days);

        //direct to jsp
        request.getRequestDispatcher("viewAttendance.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}