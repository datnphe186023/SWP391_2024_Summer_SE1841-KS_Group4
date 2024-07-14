package controller.academicstaff;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.classes.Class;
import models.classes.ClassDAO;
import models.classes.IClassDAO;
import models.day.Day;
import models.day.DayDAO;
import models.day.IDayDAO;
import models.pupil.IPupilDAO;
import models.pupil.PupilDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYearDAO;
import models.week.IWeekDAO;
import models.week.WeekDAO;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "academicstaff/ViewPupilsAttendanceServlet", value = "/academicstaff/pupilsattendance")
public class ViewPupilsAttendanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //list of school years
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        request.setAttribute("schoolYears", schoolYearDAO.getAll());

        //get school year id from select box
        String schoolYearId = request.getParameter("schoolYearId");
        if (schoolYearId == null){
            schoolYearId = schoolYearDAO.getLatest().getId();
        }
        //get list of weeks for select box
        IWeekDAO weekDAO = new WeekDAO();
        request.setAttribute("weeks", weekDAO.getWeeks(schoolYearId));
        request.setAttribute("schoolYearId", schoolYearId);

        String weekId = request.getParameter("weekId");
        if (weekId == null){
            weekId = weekDAO.getCurrentWeek(new Date());
        }
        if (weekId == null){
            weekId = weekDAO.getfirstWeekOfClosestSchoolYear(schoolYearId).getId();
        }
        request.setAttribute("weekId", weekId);



        //send list of class for select box
        IClassDAO classDAO = new ClassDAO();
        request.setAttribute("classList", classDAO.getBySchoolYear(schoolYearId));

        //get class id
        String classId = request.getParameter("classId");
        if (classId != null){
            if (!classId.isBlank()){
                IDayDAO dayDAO = new DayDAO();
                request.setAttribute("days", dayDAO.getDaysWithTimetableForClass(weekId, classId));

                //send list of pupils
                Class classes = classDAO.getClassById(classId);
                request.setAttribute("classes", classes);
                IPupilDAO pupilDAO = new PupilDAO();
                request.setAttribute("pupils", pupilDAO.getListPupilsByClass(null, classes.getId()));
                request.setAttribute("classId", classId);
            }
        }


        request.getRequestDispatcher("viewPupilsAttendance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}