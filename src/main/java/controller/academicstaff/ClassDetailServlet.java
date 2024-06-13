package controller.academicstaff;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
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
import utils.Helper;

import java.io.IOException;
import java.util.List;
@WebServlet(name = "academicstaff/ClassDetailServlet", value = "/academicstaff/classdetail")
public class ClassDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IPupilDAO pupilDAO = new PupilDAO();
        IClassDAO classDAO = new ClassDAO();

        String classId = request.getParameter("classId");
        List<Pupil> listPupil = pupilDAO.getListPupilsByClass(classId);
        Class classes = classDAO.getClassById(classId);
        /// This variable to display the schoolyear of this class
        String schoolYear =classes.getSchoolYear().getStartDate().toString();
        /// Get Pupil that not have class and the age is valid for this class (This code use for add pupil to class modal)
        List<Pupil> listPupilWithoutClass = pupilDAO.getPupilsWithoutClass(classes.getGrade().getId(),schoolYear);
        request.setAttribute("listPupilWithoutClass",listPupilWithoutClass);
        request.setAttribute("teacherName",classes.getTeacher().getLastName()+" "+classes.getTeacher().getFirstName());
        request.setAttribute("grade",classes.getGrade().getName());
        request.setAttribute("classes",classes.getName());
        request.setAttribute("classId",classId);
        request.setAttribute("listPupil",listPupil);
        request.setAttribute("numberOfPupilsPending",pupilDAO.getPupilsWithoutClass(classes.getGrade().getId(),schoolYear).size());
        request.getRequestDispatcher("classDetail.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action.equals("addPupil")){
            IPupilDAO pupilDAO = new PupilDAO();
            HttpSession session = request.getSession();
            String toastMessage ="";
            String toastType="";
            boolean addResult = false;

            String [] pupilSelected = request.getParameterValues("pupilSelected");
            String classId = request.getParameter("classId");
            if(pupilSelected!=null){
                for(int i=0;i<pupilSelected.length;i++){
                    addResult= pupilDAO.addPupilToClass(pupilSelected[i],classId);
                }
            }
            if(addResult){
                toastMessage="Thêm học sinh vào lớp thành công";
                toastType="success";
            }else{
                toastMessage="Thêm học sinh vào lớp thất bại";
                toastType="error";
            }
            session.setAttribute("toastMessage",toastMessage);
            session.setAttribute("toastType",toastType);
            response.sendRedirect("classdetail?classId="+classId);
        }


    }
}