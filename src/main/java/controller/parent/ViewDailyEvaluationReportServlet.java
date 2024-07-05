package controller.parent;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.evaluation.Evaluation;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
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
        IWeekDAO weekDAO = new WeekDAO();
        Date currentDate = Date.from(Instant.now());
        String sltedw = "";
        String sltedy = "";
        String display= "week";
        if(weekDAO.getCurrentWeek(currentDate)!=null){
            sltedw = weekDAO.getCurrentWeek(currentDate);
            sltedy = weekDAO.getYearByWeek(sltedw);
        }else if(schoolYearDAO.getClosestSchoolYears()!=null && schoolYearDAO.CheckPupilInClassOfSchoolYear(pupilDAO.getPupilByUserId(user.getId()).getId(),schoolYearDAO.getClosestSchoolYears().getId())) {
            sltedy = schoolYearDAO.getClosestSchoolYears().getId();
            sltedw = weekDAO.getfirstWeekOfClosestSchoolYear(sltedy).getId();
        }
        else{
            sltedw = weekDAO.getLastWeekOfClosestSchoolYearOfPupil(pupilDAO.getPupilByUserId(user.getId()).getId()).getId();
            sltedy = weekDAO.getYearByWeek(sltedw);
        }
        List<SchoolYear> schoolYears = schoolYearDAO.getListSchoolYearsByPupilID(pupilDAO.getPupilByUserId(user.getId()).getId());
        List<Week> weekList = weekDAO.getWeeks(sltedy);
        List<Evaluation> evaluationList = evaluationDAO.getEvaluationByWeekandPupilId(sltedw,pupilDAO.getPupilByUserId(user.getId()).getId());
        int good_day = evaluationDAO.countEvaluationOfWeek(sltedw,pupilDAO.getPupilByUserId(user.getId()).getId());
        Week choosenweek = weekDAO.getWeek(sltedw);
        request.setAttribute("good_day",good_day);
        request.setAttribute("cweek",choosenweek);
        request.setAttribute("schoolYearList", schoolYears);
        request.setAttribute("weekList",weekList);
        request.setAttribute("evaluationList",evaluationList);
        request.setAttribute("display",display);
        request.setAttribute("sltedy",sltedy);
        request.setAttribute("sltedw",sltedw);
        request.getRequestDispatcher("viewDailyEvaluationReport.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        IPupilDAO pupilDAO = new PupilDAO();
        IEvaluationDAO evaluationDAO = new EvaluationDAO();
        IWeekDAO weekDAO = new WeekDAO();
        String sltedy = request.getParameter("schoolyear");
        String sltedw = request.getParameter("week");
        String display = request.getParameter("display");
        System.out.println(sltedw);
        System.out.println(sltedy);
        System.out.println(display);
        if(display.equalsIgnoreCase("week")&&sltedw==null&&sltedy==null){
            response.sendRedirect("viewdailyevaluationreport");
        }
        else if(display.equalsIgnoreCase("week")){
            if(weekDAO.checkWeekInSchoolYear(sltedw,sltedy)){
                Week choosenweek = weekDAO.getWeek(sltedw);
                request.setAttribute("cweek",choosenweek);
                List<Evaluation> evaluationList = evaluationDAO.getEvaluationByWeekandPupilId(sltedw,pupilDAO.getPupilByUserId(user.getId()).getId());
                int good_day = evaluationDAO.countEvaluationOfWeek(sltedw,pupilDAO.getPupilByUserId(user.getId()).getId());
                request.setAttribute("evaluationList",evaluationList);
                request.setAttribute("good_day",good_day);
            }
            List<SchoolYear> schoolYears = schoolYearDAO.getListSchoolYearsByPupilID(pupilDAO.getPupilByUserId(user.getId()).getId());
            List<Week> weekList = weekDAO.getWeeks(sltedy);


            request.setAttribute("schoolYearList", schoolYears);
            request.setAttribute("weekList",weekList);

            request.setAttribute("sltedy",sltedy);
            request.setAttribute("sltedw",sltedw);
            request.setAttribute("display",display);
            request.getRequestDispatcher("viewDailyEvaluationReport.jsp").forward(request, response);
        }else{
            List<String> dataList = evaluationDAO.NumberOfGoodEvaluationsPerYear(pupilDAO.getPupilByUserId(user.getId()).getId());
            List<SchoolYear> schoolYears = new ArrayList<>();
            List<Integer> good_day = new ArrayList<>();
            List<Integer> week = new ArrayList<>();
            for(int i=0; i<dataList.size(); i++){
                String[] parts = dataList.get(i).split("-");
                schoolYears.add(schoolYearDAO.getSchoolYear(parts[0]));
                good_day.add(Integer.parseInt(parts[1]));
                week.add(Integer.parseInt(parts[2]));
            }

            request.setAttribute("schoolYears",schoolYears);
            request.setAttribute("good_day",good_day);
            request.setAttribute("week",week);
            request.setAttribute("display",display);
            request.getRequestDispatcher("viewDailyEvaluationReport.jsp").forward(request, response);

        }

    }
}