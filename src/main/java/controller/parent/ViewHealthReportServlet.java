package controller.parent;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.evaluation.EvaluationDAO;
import models.evaluation.HealthCheckUp;
import models.evaluation.IEvaluationDAO;
import models.pupil.IPupilDAO;
import models.pupil.PupilDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.user.User;
import models.week.Week;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ViewHealthReportServlet", urlPatterns = {"/parent/viewhealthreport"})
public class ViewHealthReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        IPupilDAO pupilDAO = new PupilDAO();
        List<SchoolYear> schoolYears = schoolYearDAO.getListSchoolYearsByPupilID(pupilDAO.getPupilByUserId(user.getId()).getId());
        request.setAttribute("schoolYearList", schoolYears);
        request.getRequestDispatcher("viewHealthReport.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String report = request.getParameter("report");
        String month = request.getParameter("month");
        String year = request.getParameter("schoolyear");
        String selectedReport = "";
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        IEvaluationDAO evaluationDAO = new EvaluationDAO();
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        IPupilDAO pupilDAO = new PupilDAO();
        List<SchoolYear> schoolYears = schoolYearDAO.getListSchoolYearsByPupilID(pupilDAO.getPupilByUserId(user.getId()).getId());
        request.setAttribute("schoolYearList", schoolYears);
        String pupil_id = pupilDAO.getPupilByUserId(user.getId()).getId();
        List<HealthCheckUp> healthCheckUpList = new ArrayList<>();
        List<Date> checkuptime = new ArrayList<>();
        healthCheckUpList = evaluationDAO.getHealthCheckUpByIdandSchoolYearId(pupil_id, year);
        for(int i=0; i<healthCheckUpList.size(); i++){
            checkuptime.add(healthCheckUpList.get(i).getCheckUpDate());
        }
        HealthCheckUp healthCheckUp = new HealthCheckUp();
        if(report.equalsIgnoreCase("overall") ){
            selectedReport = "overall";
        }else if(report.equalsIgnoreCase("detail") && month==null){
            selectedReport = "detail";
        }else if(report.equalsIgnoreCase("detail") && month!=null) {
            selectedReport = "detail";
            request.setAttribute("sltedtime", month);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = formatter.parse(month);
                healthCheckUp = evaluationDAO.getHealthCheckUpByIdandDate(pupil_id, date);
                request.setAttribute("healthCheckUp", healthCheckUp);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        request.setAttribute("healthCheckUpList", healthCheckUpList);
        request.setAttribute("selectedReport", selectedReport);
        request.setAttribute("sltedsy", year);
        request.getRequestDispatcher("viewHealthReport.jsp").forward(request, response);
    }
}