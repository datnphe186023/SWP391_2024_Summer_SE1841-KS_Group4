package controller.teacher;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.classes.Class;
import models.classes.ClassDAO;
import models.classes.IClassDAO;
import models.day.DayDAO;
import models.pupil.IPupilDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.user.User;
import models.week.IWeekDAO;
import models.week.Week;
import models.week.WeekDAO;


@WebServlet(name = "teacher/EvaluationReportServlet", value = "/teacher/evaluationreport")
public class EvaluationReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IPupilDAO pupilDAO = new PupilDAO();
        IWeekDAO weekDAO = new WeekDAO();
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        IClassDAO classDAO = new ClassDAO();

        List<Pupil> listPupil =null;
        List<Week> listWeeks = null;
        Class classes = new Class();
        HttpSession session = request.getSession();
        // Get the current date
        Date currentDate = new Date();
        User user = (User) session.getAttribute("user");
        SchoolYear schoolYear = schoolYearDAO.getSchoolYearByDate(currentDate);
        if(schoolYear!=null){
             listPupil = pupilDAO.getListPupilOfTeacherBySchoolYear(schoolYear.getId(),user.getUsername());
             listWeeks= weekDAO.getWeeks(schoolYear.getId());
            classes = classDAO.getTeacherClassByYear(schoolYear.getId(), user.getUsername());
        }
        request.setAttribute("classes",classes);
        request.setAttribute("checkYear",schoolYear!=null?schoolYear.getId():schoolYearDAO.getLatest().getId());
        request.setAttribute("checkWeek",weekDAO.getCurrentWeek(currentDate));
        request.setAttribute("listWeeks",listWeeks);
        request.setAttribute("listPupil",listPupil);
        request.setAttribute("listSchoolYear", schoolYearDAO.getAll());
        request.getRequestDispatcher("evaluationReport.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IPupilDAO pupilDAO = new PupilDAO();
        IWeekDAO weekDAO = new WeekDAO();
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        IClassDAO classDAO = new ClassDAO();

        List<Pupil> listPupil =null;
        Class classes = new Class();
        List<Week> listWeeks = null;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String schoolYear = request.getParameter("schoolYear");
        String week = request.getParameter("week");
        if(schoolYear!=null){
            listPupil = pupilDAO.getListPupilOfTeacherBySchoolYear(schoolYear,user.getUsername());
            listWeeks= weekDAO.getWeeks(schoolYear);
            classes = classDAO.getTeacherClassByYear(schoolYear, user.getUsername());

        }
        request.setAttribute("classes",classes);
        request.setAttribute("checkWeek",week);
        request.setAttribute("checkYear",schoolYear);
        request.setAttribute("listWeeks",listWeeks);
        request.setAttribute("listPupil",listPupil);
        request.setAttribute("listSchoolYear", schoolYearDAO.getAll());
        request.getRequestDispatcher("evaluationReport.jsp").forward(request,response);
    }
}