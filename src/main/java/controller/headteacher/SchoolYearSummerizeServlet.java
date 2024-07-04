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

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SchoolYearSummerizeServlet", value = "/headteacher/schoolyearsummerize")
public class SchoolYearSummerizeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ISchoolYearDAO  schoolYearDAO = new SchoolYearDAO();
        IPupilDAO pupilDAO = new PupilDAO();
        IEvaluationDAO evaluationDAO = new EvaluationDAO();
        SchoolYear sltedsy = schoolYearDAO.getClosestSchoolYears();
        List<SchoolYear> schoolYearList = schoolYearDAO.getAll();
        List<Pupil> pupils = pupilDAO.getPupilBySchoolYear(sltedsy.getId());
        int numberOfPupilInSchoolYear = pupils.size();
        int numberOfGoodPupil =  evaluationDAO.AccomplishmentAchieveStudents(sltedsy.getId());
        request.setAttribute("numberOfPupilInSchoolYear", numberOfPupilInSchoolYear);
        request.setAttribute("numberOfGoodPupil", numberOfGoodPupil);
        request.setAttribute("schoolYearList", schoolYearList);
        request.setAttribute("sltedsy", sltedsy);
        request.setAttribute("pupils", pupils);
        request.getRequestDispatcher("SchoolYearSummerize.jsp").forward(request, response);
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
        request.setAttribute("numberOfPupilInSchoolYear", numberOfPupilInSchoolYear);
        request.setAttribute("numberOfGoodPupil", numberOfGoodPupil);
        request.setAttribute("schoolYearList", schoolYearList);
        request.setAttribute("sltedsy", sltedsy);
        request.setAttribute("pupils", pupils);
        request.getRequestDispatcher("SchoolYearSummerize.jsp").forward(request, response);
    }


}