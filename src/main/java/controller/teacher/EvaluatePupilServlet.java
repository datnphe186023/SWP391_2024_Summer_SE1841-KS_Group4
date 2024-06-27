package controller.teacher;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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
import models.evaluation.Evaluation;
import models.evaluation.EvaluationDAO;
import models.evaluation.IEvaluationDAO;
import models.personnel.IPersonnelDAO;
import models.personnel.Personnel;
import models.personnel.PersonnelDAO;
import models.pupil.IPupilDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.user.User;
import utils.Helper;


@WebServlet(name = "teacher/EvaluatePupilServlet", value = "/teacher/evaluate")
public class EvaluatePupilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IPupilDAO pupilDAO = new PupilDAO();
        IPersonnelDAO personnelDAO = new PersonnelDAO();
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        IClassDAO classDAO = new ClassDAO();
        DayDAO dayDAO = new DayDAO();

        HttpSession session = request.getSession();
        // Get the current date
        Date currentDate = new Date();
        // Define the date format
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // Convert the Date to a String
        String dateString = formatter.format(currentDate);
        String gradeTeacher =null;
        String classTeacher=null;
        Class classes = null;
        List<Pupil> listPupil = null;
        User user = (User) session.getAttribute("user");
            Personnel teacher = personnelDAO.getPersonnelByUserId(user.getId());
            SchoolYear currentSchoolYear = schoolYearDAO.getSchoolYearByDate(increaseDate(currentDate,15));
            ///  Get Class and grade of class of this teacher in one school year
        if(currentSchoolYear!=null){
             classes = classDAO.getTeacherClassByYear(currentSchoolYear.getId(),teacher.getId());
             listPupil = pupilDAO.getListPupilOfTeacherBySchoolYear(currentSchoolYear.getId(), teacher.getId());
        }
        if(classes!=null){
                gradeTeacher=classes.getGrade().getName();
                classTeacher=classes.getName();
        }
        request.setAttribute("dateId",dayDAO.getDayByDate(dateString).getId());
        request.setAttribute("schoolYear",currentSchoolYear);
        request.setAttribute("teacherGrade",gradeTeacher);
        request.setAttribute("teacherClass",classTeacher);
        request.setAttribute("listPupil", listPupil);
        request.getRequestDispatcher("evaluatePupil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IPupilDAO pupilDAO = new PupilDAO();
        IPersonnelDAO personnelDAO = new PersonnelDAO();
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        IEvaluationDAO evaluationDAO = new EvaluationDAO();
        DayDAO dayDAO = new DayDAO();
        HttpSession session = request.getSession();

        String action = request.getParameter("action");
        String toastType = "", toastMessage = "";
        if(action.equals("evaluatePupil")){
            // Thu thập tất cả các tham số đầu vào
            Enumeration<String> parameterNames = request.getParameterNames();
            HashMap<String,String> evaluateMap = new HashMap<>();
            while(parameterNames.hasMoreElements()){
                String paramName = parameterNames.nextElement();
                if(paramName.startsWith("evaluation-") || paramName.startsWith("notes-")){
                    if(paramName.startsWith("evaluation-") ){
                        String   evaltuationValue = request.getParameter(paramName);
                        String[]   partsEvaluation = paramName.split("-");
                        evaluateMap.put(partsEvaluation[1]+"-evaluation",evaltuationValue);
                        /// add evaluation at the end to pupilId different from pupilId at note
                    }
                    if(paramName.startsWith("notes-")){
                        String  noteValue = request.getParameter(paramName);
                        String[]    partsNotes = paramName.split("-");
                        evaluateMap.put(partsNotes[1]+"-notes",noteValue);
                        /// add note at the end to pupilId different from pupilId at evaluation
                    }
                }
            }

            int counter =0;
            Date currentDate = new Date();
            // Define the date format
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            // Convert the Date to a String
            String dateString = formatter.format(currentDate);
            List<Pupil> listPupil = null;
            User user = (User) session.getAttribute("user");
            Personnel teacher = personnelDAO.getPersonnelByUserId(user.getId());
            SchoolYear currentSchoolYear = schoolYearDAO.getSchoolYearByDate(increaseDate(currentDate,15));
            if(currentSchoolYear!=null){
                listPupil = pupilDAO.getListPupilOfTeacherBySchoolYear(currentSchoolYear.getId(), teacher.getId());
            }

            if(listPupil!=null){
                for(Pupil pupil : listPupil){
                    Evaluation evaluation = new Evaluation();
                    evaluation.setPupil(pupilDAO.getPupilsById(pupil.getId()));
                    evaluation.setDate(dayDAO.getDayByDate(dateString));
                    if(evaluateMap.containsKey(pupil.getId()+"-evaluation")){
                        evaluation.setEvaluation(Helper.formatString(evaluateMap.get(pupil.getId()+"-evaluation")));
                    }
                    if(evaluateMap.containsKey(pupil.getId()+"-notes")){
                        evaluation.setNotes(Helper.formatString(evaluateMap.get(pupil.getId()+"-notes")));
                    }
                    if(evaluationDAO.checkEvaluationExist(pupil.getId(),dayDAO.getDayByDate(dateString).getId())){
                        if(evaluationDAO.updateEvaluationByPupilAndDay(evaluation)
                        && evaluationDAO.updateNoteByPupilAndDay(evaluation)){
                            counter++;
                        }
                    }else {
                        if(evaluationDAO.createEvaluation(evaluation)){
                            counter++;
                        }
                    }

                }
            }
            if(counter==listPupil.size()){
                toastType="success";
                toastMessage="Thao tác thành công";
            }else {
                toastType="error";
                toastMessage="Thao tác thất bại";
            }
            session.setAttribute("toastType", toastType);
            session.setAttribute("toastMessage", toastMessage);
            response.sendRedirect("evaluate");
        }
    }

    private Date increaseDate(Date currrentDate, int day){
        Date currentDate = new Date();

        // Create a Calendar object
        Calendar calendar = Calendar.getInstance();

        // Set the calendar to the current date
        calendar.setTime(currentDate);

        // Subtract 15 days from the current date
        calendar.add(Calendar.DAY_OF_MONTH, day);


        return calendar.getTime();
    }
}