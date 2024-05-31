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
        String classId = request.getParameter("classId");
        Class classes = classDAO.getClassById(classId);
        /// This variable to display the schoolyear of this class
        String schoolYear =classes.getSchoolYear().getStartDate().toString();
        List<Pupil> listPupil = pupilDAO.getPupilsWithoutClass(classes.getGrade().getId(),schoolYear);
        String search = request.getParameter("information");
        if(search!=null){
            listPupil = pupilDAO.searchPupilWithoutClassByGrade(classes.getGrade().getId(),schoolYear,formatString(search));
        }
        request.setAttribute("classId",classId);
        request.setAttribute("listPupil",listPupil);
        request.getRequestDispatcher("addPupilToClass.jsp").forward(request,response);
    }
    private String formatString(String search){
        StringBuilder result = new StringBuilder();
        String[] searchArray = search.split("\\s+");
        for(int i=0;i<searchArray.length;i++){
            result.append(searchArray[i]).append(" ");
        }
        return result.toString().trim();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PupilDAO pupilDAO = new PupilDAO();
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
        response.sendRedirect("pupilclass?classId="+classId);
    }
}