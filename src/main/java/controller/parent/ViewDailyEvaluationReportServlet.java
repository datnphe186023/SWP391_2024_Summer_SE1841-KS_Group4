package controller.parent;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.evaluation.EvaluationDAO;
import models.evaluation.IEvaluationDAO;
import models.pupil.IPupilDAO;
import models.pupil.PupilDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.user.User;
import models.week.IWeekDAO;
import models.week.Week;
import models.week.WeekDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ViewDailyEvaluationReportServlet", urlPatterns = {"/parent/viewdailyevaluationreport"})
public class ViewDailyEvaluationReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        IPupilDAO pupilDAO = new PupilDAO();
        IEvaluationDAO evaluationDAO = new EvaluationDAO();
        List<SchoolYear> schoolYears = schoolYearDAO.getListSchoolYearsByPupilID(pupilDAO.getPupilByUserId(user.getId()).getId());
        List<String> reportdata = evaluationDAO.EvaluationReportYearly(pupilDAO.getPupilByUserId(user.getId()).getId());
        request.setAttribute("schoolYearList", schoolYears);
        request.setAttribute("reportdata", reportdata);
        request.getRequestDispatcher("viewDailyEvaluationReport.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}