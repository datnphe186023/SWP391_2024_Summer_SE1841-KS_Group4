package controller.academicstaff;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.classes.Class;
import models.classes.ClassDAO;
import models.grade.GradeDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "academicstaff/AddPupilToClass", value = "/academicstaff/addpupiltoclass")
public class AddPupilToClass extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PupilDAO pupilDAO = new PupilDAO();
        ClassDAO classDAO = new ClassDAO();
        HttpSession session = request.getSession();
        String classId = request.getParameter("classId");
        ////  Get session for the add pupil to class function
        session.setAttribute("classId",classId);
        Class classes = classDAO.getClassById(classId);
        /// This variable to display the schoolyear of this class
        String schoolYear =classes.getSchoolYear().getStartDate().toString();
        List<Pupil> listPupil = pupilDAO.getPupilsWithoutClass(classes.getGrade().getId(),schoolYear);
        String search = request.getParameter("information");
        if(search!=null){
//            listPupil = pupilDAO.searchPupilByStatus(search,"đang chờ xử lý");
        }
        request.setAttribute("listPupil",listPupil);
        request.getRequestDispatcher("addPupilToClass.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PupilDAO pupilDAO = new PupilDAO();
        HttpSession session = request.getSession();
        String toastMessage ="";
        String toastType="";
        boolean addResult = false;
        boolean updateResult = false;
        String [] pupilSelected = request.getParameterValues("pupilSelected");
        String classId = (String) session.getAttribute("classId");
        if(pupilSelected!=null){
            for(int i=0;i<pupilSelected.length;i++){
                 addResult= pupilDAO.addPupilToClass(pupilSelected[i],classId);
                 updateResult =  pupilDAO.updatePupilStatus(pupilSelected[i],"" );
                 session.removeAttribute("classId");
            }
        }
        if(addResult && updateResult){
            toastMessage="Thêm học sinh vào lớp thành công";
            toastType="success";
        }else{
            toastMessage="Thêm học sinh vào lớp thất bại";
            toastType="error";
        }
        session.setAttribute("toastMessage",toastMessage);
        session.setAttribute("toastType",toastType);
        response.sendRedirect("pupilclass?classId="+classId);
    }
}