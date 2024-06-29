package controller.parent;

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
@WebServlet(name = "/parent/ViewTimetableServlet", urlPatterns = {"/parent/view-timetable"})
public class ViewTimetableServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        List<SchoolYear> schoolYearList = new SchoolYearDAO().getAll();
        request.setAttribute("schoolYearList", schoolYearList);
        request.getRequestDispatcher("viewTimetable.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String week = request.getParameter("week");
        String schoolyear = request.getParameter("schoolyear");
        models.classes.Class aclass = new ClassDAO().getClassByPupilIdandSchoolYear(id, schoolyear);
        if (aclass == null) {
            List<SchoolYear> schoolYearList = new SchoolYearDAO().getAll();
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Bạn chưa được phân công năm này hãy chọn lại");
            request.setAttribute("schoolYearList", schoolYearList);
            request.getRequestDispatcher("viewTimetable.jsp").forward(request, response);
        } else {
            WeekDAO weekDAO = new WeekDAO();
            SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
            List<SchoolYear> schoolYearList = schoolYearDAO.getAll();
            List<Week> weekList = weekDAO.getWeeks(schoolyear);
            List<Timetable> timetable = new ArrayList<>();
            ITimeslotDAO timeslotDAO = new TimeslotDAO();
            List<Timeslot> timeslotList = timeslotDAO.getTimeslotsForTimetable();
            IDayDAO dayDAO = new DayDAO();
            List<Day> dayList = dayDAO.getDayByWeek(week);
            timetable = new TimetableDAO().getTimetableByClassAndWeek(aclass.getId(), week, "đã được duyệt");
            request.setAttribute("aClass", aclass);
            request.setAttribute("timetable", timetable);
            request.setAttribute("timeslotList", timeslotList);
            request.setAttribute("sltedsy", schoolyear);
            request.setAttribute("sltedw", week);
            request.setAttribute("schoolYearList", schoolYearList);
            request.setAttribute("weekList", weekList);
            request.setAttribute("dayList", dayList);
            request.getRequestDispatcher("viewTimetable.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
