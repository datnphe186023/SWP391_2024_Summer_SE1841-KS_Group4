package controller.teacher;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.day.DayDAO;
import models.day.IDayDAO;
import models.evaluation.EvaluationDAO;
import models.evaluation.IEvaluationDAO;
import models.pupil.IPupilDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.user.User;
import models.week.IWeekDAO;
import models.week.WeekDAO;


@WebServlet(name = "teacher/EvaluationDetailServlet", value = "/teacher/evaluationdetail")
public class EvaluationDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IWeekDAO weekDAO = new WeekDAO();
        IEvaluationDAO evaluationDAO = new EvaluationDAO();
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        IPupilDAO pupilDAO = new PupilDAO();
        IDayDAO dayDAO = new DayDAO();
        String weekId = request.getParameter("weekId");
        List<Pupil> listPupil = null;
        HttpSession session = request.getSession();
        // Get the current date
        Date currentDate = new Date();
        User user = (User) session.getAttribute("user");
        SchoolYear schoolYear = schoolYearDAO.getSchoolYearByDate(currentDate);
        if(schoolYear!=null){
            listPupil = pupilDAO.getListPupilOfTeacherBySchoolYear(schoolYear.getId(),user.getUsername());
        }
        request.setAttribute("evaluationList",evaluationDAO.getEvaluationByWeek(weekId));
        request.setAttribute("dayList",dayDAO.getDayByWeek(weekId));
        request.setAttribute("listPupil",listPupil);
        request.setAttribute("week",weekDAO.getWeek(weekId));
        request.getRequestDispatcher("evaluationDetail.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}