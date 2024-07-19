package controller.teacher;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.classes.Class;
import models.classes.ClassDAO;
import models.classes.IClassDAO;
import models.evaluation.EvaluationDAO;
import models.evaluation.IEvaluationDAO;
import models.evaluation.SchoolYearSummarize;
import models.personnel.IPersonnelDAO;
import models.personnel.PersonnelDAO;
import models.pupil.IPupilDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.user.User;
import models.week.WeekDAO;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@WebServlet(name = "teacher/PupilSchoolYearSummarizeServlet", value = "/teacher/schoolyearsummarize")
public class PupilSchoolYearSummarizeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String schoolYearId = request.getParameter("schoolYearId");
        HttpSession session = request.getSession();
        if (session.getAttribute("schoolYearId") != null) {
            schoolYearId = (String) session.getAttribute("schoolYearId");
        }
        String toastType = "", toastMessage = "";
        if (session.getAttribute("toastType") != null) {
            toastType = session.getAttribute("toastType").toString();
            toastMessage = session.getAttribute("toastMessage").toString();
        }
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        if (schoolYearId == null && schoolYearDAO.getLatest()!=null) {
            schoolYearId = schoolYearDAO.getLatest().getId();
        }

        session.removeAttribute("toastType");
        session.removeAttribute("toastMessage");
        request.setAttribute("toastType", toastType);
        request.setAttribute("toastMessage", toastMessage);
        session.removeAttribute("schoolYearId");
        request.setAttribute("schoolYearId", schoolYearId);

        //get list school year
        List<SchoolYear> schoolYears = schoolYearDAO.getAll();
        request.setAttribute("schoolYears", schoolYears);

        boolean display = false;
        SchoolYear schoolYear = schoolYearDAO.getSchoolYear(schoolYearId);
        WeekDAO weekDAO = new WeekDAO();
        if (schoolYear != null) {
            IPupilDAO pupilDAO = new PupilDAO();
            IClassDAO classDAO = new ClassDAO();
            User user = (User) session.getAttribute("user");
            IPersonnelDAO personnelDAO = new PersonnelDAO();
            Class teacherClass = classDAO.getTeacherClassByYear(schoolYearId, personnelDAO.getPersonnelByUserId(user.getId()).getId());
            Date currentDate = Date.from(Instant.now());
            if (currentDate.after(weekDAO.getLastWeek(schoolYearId).getStartDate())) {
                List<Pupil> pupils = new ArrayList<>();
                display = true;
                if (teacherClass!=null){
                    pupils = pupilDAO.getPupilByClass(teacherClass.getId());
                    request.setAttribute("pupils", pupils);
                }
                if (pupils.isEmpty()){
                    request.setAttribute("error", "Bạn không có lớp trong thời gian này");
                }
            }
        }
        request.setAttribute("display", display);



        request.getRequestDispatcher("schoolYearSummarize.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String schoolYearId = request.getParameter("schoolYearId");

        // Get all parameter names
        Enumeration<String> parameterNames = request.getParameterNames();
        SchoolYearSummarize schoolYearSummarize = new SchoolYearSummarize();
        schoolYearSummarize.setSchoolYearId(schoolYearId);
        IPersonnelDAO personnelDAO = new PersonnelDAO();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        schoolYearSummarize.setTeacher(personnelDAO.getPersonnelByUserId(user.getId()));
        IPupilDAO pupilDAO = new PupilDAO();
        String result = "";

        // Loop through parameter names to process form fields
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();

            // Check if the parameter name matches the pattern for evaluate and note fields
            if (paramName.startsWith("evaluate")) {
                // Extract the pupil ID from the parameter name
                String pupilId = paramName.substring("evaluate".length());

                // Get the evaluation value
                String evaluationValue = request.getParameter(paramName);

                // Get the corresponding note value
                String noteParamName = "note" + pupilId;
                String noteValue = request.getParameter(noteParamName);

                // Optionally get the goodTicket value
                String goodTicketParamName = "goodTicket" + pupilId;
                String goodTicketValue = request.getParameter(goodTicketParamName);
                schoolYearSummarize.setPupil(pupilDAO.getPupilsById(pupilId));
                schoolYearSummarize.setGoodTicket(goodTicketValue);
                schoolYearSummarize.setTitle(evaluationValue);
                schoolYearSummarize.setTeacherNote(noteValue);

                IEvaluationDAO evaluationDAO = new EvaluationDAO();
                result = evaluationDAO.updateSchoolYearSummarize(schoolYearSummarize);
                if (result.equals("success")) {
                    session.setAttribute("toastType", "success");
                    session.setAttribute("toastMessage", "Thao tác thành công");
                } else {
                    session.setAttribute("toastType", "error");
                    session.setAttribute("toastMessage", result);
                }
            }
        }

        session.setAttribute("schoolYearId", schoolYearId);

        response.sendRedirect("schoolyearsummarize");
    }
}