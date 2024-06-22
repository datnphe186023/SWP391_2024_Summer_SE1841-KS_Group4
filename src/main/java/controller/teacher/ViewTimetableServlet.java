package controller.teacher;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import models.classes.ClassDAO;
import models.day.Day;
import models.day.DayDAO;
import models.day.IDayDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.timeslot.ITimeslotDAO;
import models.timeslot.Timeslot;
import models.timeslot.TimeslotDAO;
import models.timetable.Timetable;
import models.timetable.TimetableDAO;
import models.week.Week;
import models.week.WeekDAO;

/**
 *
 * @author Admin
 */
@WebServlet(name = "/teacher/ViewTimetableServlet", urlPatterns = {"/teacher/view-timetable"})
public class ViewTimetableServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        List<SchoolYear> schoolYearList = new SchoolYearDAO().getAll();
        models.classes.Class aclass = new ClassDAO().getClassByTeacherId(id);
        request.setAttribute("aClass", aclass);
        request.setAttribute("schoolYearList", schoolYearList);
        request.getRequestDispatcher("viewTimetable.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String classId = request.getParameter("classId");
        models.classes.Class aclass = new ClassDAO().getClassById(classId);
        String week = request.getParameter("week");
        String schoolyear = request.getParameter("schoolyear");
        WeekDAO weekDAO = new WeekDAO();
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        List<SchoolYear> schoolYearList = schoolYearDAO.getAll();
        List<Week> weekList = weekDAO.getWeeks(schoolyear);
        List<Timetable> timetable = new ArrayList<>();
        ITimeslotDAO timeslotDAO = new TimeslotDAO();
        List<Timeslot> timeslotList = timeslotDAO.getTimeslotsForTimetable();
        IDayDAO dayDAO = new DayDAO();
        List<Day> dayList = dayDAO.getDayByWeek(week);
        timetable = new TimetableDAO().getTimetableByClassAndWeek(classId, week, "đã được duyệt");
        request.setAttribute("timetable", timetable);
        request.setAttribute("timeslotList", timeslotList);
        request.setAttribute("sltedsy", schoolyear);
        request.setAttribute("sltedw", week);
        request.setAttribute("schoolYearList", schoolYearList);
        request.setAttribute("weekList", weekList);
        request.setAttribute("dayList", dayList);
        request.setAttribute("aClass", aclass);
        request.getRequestDispatcher("viewTimetable.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
