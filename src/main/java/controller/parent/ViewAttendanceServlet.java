package controller.parent;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.day.Day;
import models.day.IDayDAO;
import models.day.DayDAO;
import models.pupil.IPupilDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.user.User;
import models.week.IWeekDAO;
import models.week.WeekDAO;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "parent/ViewAttendanceServlet", value = "/parent/attendance")
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
            schoolYearId = schoolYearDAO.getClosestSchoolYears().getId();
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
        request.setAttribute("pupilId", user.getUsername());
        IPupilDAO pupilDAO = new PupilDAO();
        Pupil pupil = pupilDAO.getPupilsById(user.getUsername());
        request.setAttribute("pupilFullName", pupil.getLastName()+" "+pupil.getFirstName());
        List<Day> days = dayDAO.getDaysWithTimetable(weekId);
        request.setAttribute("days", days);

        //direct to jsp
        request.getRequestDispatcher("viewAttendance.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}