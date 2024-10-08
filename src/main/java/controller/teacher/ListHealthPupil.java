package controller.teacher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.classes.Class;
import models.classes.ClassDAO;
import models.classes.IClassDAO;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListHealthPupil extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IPupilDAO pupilDAO = new PupilDAO();
        IPersonnelDAO personnelDAO = new PersonnelDAO();
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        IClassDAO classDAO = new ClassDAO();

        HttpSession session = request.getSession();

        User user = null;
        //// variable to display the year that being checked
        String yearSelected = "";
        ///// Field to define the variable

        Class classes = new Class();
        List<Pupil> listPupil = new ArrayList<>();
        String gradeTeacher = null;
        String classTeacher = null;
        if (session.getAttribute("user") != null) {
            user = (User) session.getAttribute("user");

            if (schoolYearDAO.getLatest()!=null){
                yearSelected = schoolYearDAO.getLatest().getId();
            }
            String schoolYear = request.getParameter("schoolYear");
            Personnel teacher = personnelDAO.getPersonnelByUserId(user.getId());
            if (schoolYear != null) {
                yearSelected = schoolYear;
            }
            if (!yearSelected.isEmpty()) {
                listPupil = pupilDAO.getListPupilOfTeacherBySchoolYear(yearSelected, teacher.getId());

                ///  Get Class and grade of class of this teacher in one school year
                classes = classDAO.getTeacherClassByYear(yearSelected, teacher.getId());
            }

            if (classes != null) {
                gradeTeacher = classes.getGrade().getName();
                classTeacher = classes.getName();
            }

        }
        List<SchoolYear> listSchoolYear = schoolYearDAO.getAll();
        request.setAttribute("teacherGrade", gradeTeacher);
        request.setAttribute("teacherClass", classTeacher);
        request.setAttribute("checkYear", yearSelected);
        request.setAttribute("listPupil", listPupil);
        request.setAttribute("listSchoolYear", listSchoolYear);
        request.getRequestDispatcher("listHealthPupil.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
}
