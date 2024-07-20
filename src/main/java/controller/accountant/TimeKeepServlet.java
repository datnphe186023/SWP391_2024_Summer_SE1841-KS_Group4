package controller.accountant;

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

@WebServlet(name = "accountant/TimeKeepServlet", value = "/accountant/mytimekeeping")
public class TimeKeepServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //list of school years
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        request.setAttribute("schoolYears", schoolYearDAO.getAll());

        //get school year id from select box
        String schoolYearId = request.getParameter("schoolYearId");
        if (schoolYearId == null){
            if (schoolYearDAO.getLatest()!=null){
                schoolYearId = schoolYearDAO.getLatest().getId();
            }
        }
        if (schoolYearId != null) {
            //get list of weeks for select box
            IWeekDAO weekDAO = new WeekDAO();
            request.setAttribute("weeks", weekDAO.getWeeks(schoolYearId));
            request.setAttribute("schoolYearId", schoolYearId);

            String weekId = request.getParameter("weekId");
            if (weekId == null) {
                weekId = weekDAO.getCurrentWeek(new Date());
            }
            if (weekId == null) {
                weekId = weekDAO.getfirstWeekOfClosestSchoolYear(schoolYearId).getId();
            }
            request.setAttribute("weekId", weekId);
            IDayDAO dayDAO = new DayDAO();
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            request.setAttribute("personnelId", user.getUsername());
            IPersonnelDAO personnelDAO = new PersonnelDAO();
            Personnel personnel = personnelDAO.getPersonnel(user.getUsername());
            request.setAttribute("personnelFullName", personnel.getLastName() + " " + personnel.getFirstName());
            System.out.println(weekId);
            List<Day> days = dayDAO.getDayByWeek(weekId);
            request.setAttribute("days", days);
        }

        //direct to jsp
        request.getRequestDispatcher("viewTimeKeep.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}