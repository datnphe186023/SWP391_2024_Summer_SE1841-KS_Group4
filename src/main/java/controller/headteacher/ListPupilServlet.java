package controller.headteacher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import models.schoolYear.SchoolYearDAO;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "headteacher/ListPupilServlet", value = "/headteacher/listpupil")
public class ListPupilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IClassDAO classDAO = new ClassDAO();
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        IPupilDAO pupilDAO = new PupilDAO();
        IPersonnelDAO personnelDAO = new PersonnelDAO();


        String classesSelect = request.getParameter("classes");
        String schoolYearSelect = request.getParameter("schoolYear");
        /// School Year default will set to the lastest year
        if (schoolYearSelect==null ){
            schoolYearSelect = schoolYearDAO.getLatest().getId();
        }
            Class gradeClass =  classDAO.getClassById(classesSelect);
            Personnel teacher = personnelDAO.getTeacherByClassAndSchoolYear(classesSelect,schoolYearSelect);
            if(teacher!=null && gradeClass!=null){
                request.setAttribute("teacherName",teacher.getLastName()+" "+teacher.getFirstName());
                request.setAttribute("grade",gradeClass.getName());
            }

        List<Pupil> listPupils = pupilDAO.getPupilByClassAndSchoolYear(classesSelect,schoolYearSelect);
        List<Class> listClass = classDAO.getBySchoolYear(schoolYearSelect);

        request.setAttribute("classSelect",classesSelect);
        request.setAttribute("listPupil",listPupils);
        request.setAttribute("schoolYearSelect",schoolYearSelect);
        request.setAttribute("listClass",listClass);
        request.setAttribute("listSchoolYear",schoolYearDAO.getAll());
        request.getRequestDispatcher("listPupil.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
}