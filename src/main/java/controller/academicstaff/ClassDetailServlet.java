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
import models.schoolYear.SchoolYear;
import utils.Helper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        List<Pupil> listPupilWithoutClass = pupilDAO.getPupilsWithoutClass(classes.getSchoolYear().getId());

        /// This request for add pupil to class
        request.setAttribute("checkedDate", isSchoolYearInThePast(classes.getSchoolYear()));
        request.setAttribute("listPupilWithoutClass",listPupilWithoutClass);
        request.setAttribute("teacherName",classes.getTeacher().getLastName()+" "+classes.getTeacher().getFirstName());
        request.setAttribute("grade",classes.getGrade().getName());
        request.setAttribute("classes",classes.getName());
        request.setAttribute("classId",classId);
        /// End request for add pupil to class
        request.setAttribute("listPupil",listPupil);
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
            /// Get all pupils that be selected
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

    private boolean isSchoolYearInThePast(SchoolYear schoolYear){
        Date currentDate = new Date();
        return schoolYear.getEndDate().before(currentDate);
    }
}