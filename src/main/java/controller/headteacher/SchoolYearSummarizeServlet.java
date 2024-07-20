package controller.headteacher;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.evaluation.EvaluationDAO;
import models.evaluation.IEvaluationDAO;
import models.pupil.IPupilDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.week.IWeekDAO;
import models.week.WeekDAO;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@WebServlet(name = "SchoolYearSummarizeServlet", value = "/headteacher/schoolyearsummarize")
public class SchoolYearSummarizeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ISchoolYearDAO  schoolYearDAO = new SchoolYearDAO();
        IPupilDAO pupilDAO = new PupilDAO();
        IEvaluationDAO evaluationDAO = new EvaluationDAO();
        List<SchoolYear> schoolYearList = schoolYearDAO.getAll();
        IWeekDAO weekDAO = new WeekDAO();
        request.setAttribute("schoolYearList", schoolYearList);
        if (schoolYearDAO.getLatest()!=null){
            SchoolYear sltedsy = schoolYearDAO.getLatest();

            List<Pupil> pupils = pupilDAO.getPupilBySchoolYear(sltedsy.getId());
            int numberOfPupilInSchoolYear = pupils.size();
            int numberOfGoodPupil =  evaluationDAO.AccomplishmentAchieveStudents(sltedsy.getId());
            Date currentDate = Date.from(Instant.now());
            boolean display = currentDate.after(weekDAO.getLastWeek(sltedsy.getId()).getStartDate());
            request.setAttribute("display", display);
            request.setAttribute("numberOfPupilInSchoolYear", numberOfPupilInSchoolYear);
            request.setAttribute("numberOfGoodPupil", numberOfGoodPupil);

            request.setAttribute("sltedsy", sltedsy);
            request.setAttribute("pupils", pupils);
        }
        request.getRequestDispatcher("schoolYearSummarize.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ISchoolYearDAO  schoolYearDAO = new SchoolYearDAO();
       SchoolYear sltedsy = schoolYearDAO.getSchoolYear(request.getParameter("year"));
        IPupilDAO pupilDAO = new PupilDAO();
        IEvaluationDAO evaluationDAO = new EvaluationDAO();
        List<SchoolYear> schoolYearList = schoolYearDAO.getAll();
        List<Pupil> pupils = pupilDAO.getPupilBySchoolYear(sltedsy.getId());
        int numberOfPupilInSchoolYear = pupils.size();
        int numberOfGoodPupil =  evaluationDAO.AccomplishmentAchieveStudents(sltedsy.getId());
        Date currentDate = Date.from(Instant.now());
        boolean display = !currentDate.before(sltedsy.getEndDate());
        request.setAttribute("display", display);
        request.setAttribute("numberOfPupilInSchoolYear", numberOfPupilInSchoolYear);
        request.setAttribute("numberOfGoodPupil", numberOfGoodPupil);
        request.setAttribute("schoolYearList", schoolYearList);
        request.setAttribute("sltedsy", sltedsy);
        request.setAttribute("pupils", pupils);
        request.getRequestDispatcher("schoolYearSummarize.jsp").forward(request, response);
    }


}