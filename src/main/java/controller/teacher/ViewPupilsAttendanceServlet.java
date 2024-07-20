package controller.teacher;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.classes.Class;
import models.classes.ClassDAO;
import models.classes.IClassDAO;
import models.day.Day;
import models.day.DayDAO;
import models.day.IDayDAO;
import models.personnel.IPersonnelDAO;
import models.personnel.PersonnelDAO;
import models.pupil.IPupilDAO;
import models.pupil.PupilDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYearDAO;
import models.user.User;
import models.week.IWeekDAO;
import models.week.WeekDAO;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "teacher/ViewPupilsAttendanceServlet", value = "/teacher/pupilsattendance")
public class ViewPupilsAttendanceServlet extends HttpServlet {
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
        if (schoolYearId != null){
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


            //send list of pupils
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            IPersonnelDAO personnelDAO = new PersonnelDAO();
            String teacherId = personnelDAO.getPersonnelByUserId(user.getId()).getId();
            IClassDAO classDAO = new ClassDAO();
            Class classes = classDAO.getTeacherClassByYear(schoolYearId, teacherId);
            if (classes != null){
                //get day list
                IDayDAO dayDAO = new DayDAO();
                request.setAttribute("days", dayDAO.getDaysWithTimetableForClass(weekId, classes.getId()));
                request.setAttribute("classes", classes);

                //get pupil list
                IPupilDAO pupilDAO = new PupilDAO();
                request.setAttribute("pupils", pupilDAO.getListPupilsByClass(null, classes.getId()));
            }
        }
        request.getRequestDispatcher("viewPupilsAttendance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}