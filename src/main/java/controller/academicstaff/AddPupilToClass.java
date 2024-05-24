package controller.academicstaff;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.pupil.Pupil;
import models.pupil.PupilDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "academicstaff/AddPupilToClass", value = "/academicstaff/addpupiltoclass")
public class AddPupilToClass extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PupilDAO pupilDAO = new PupilDAO();

        List<Pupil> listPupil = pupilDAO.getPupilByStatus("đang chờ xử lý");
        String search = request.getParameter("information");
        if(search!=null){
            listPupil = pupilDAO.searchPupilByStatus(search,"đang chờ xử lý");
        }
        request.setAttribute("listPupil",listPupil);
        request.getRequestDispatcher("addPupilToClass.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PupilDAO pupilDAO = new PupilDAO();
        HttpSession session = request.getSession();
//        String classId = request.getParameter("classId");
//        session.setAttribute("classId",classId);
//        if(classId == null){
//            classId = request.getParameter("classIdSession");
//        }
        String [] pupilSelected = request.getParameterValues("pupilSelected");
        String classId = (String) session.getAttribute("classId");
        if(pupilSelected!=null){
            for(int i=0;i<pupilSelected.length;i++){
                boolean result= pupilDAO.addPupilToClass(pupilSelected[i],classId);
                pupilDAO.updatePupilStatus(pupilSelected[i],"đã chọn lớp - chưa có tài khoản" );
            }
        }
        response.sendRedirect("addpupiltoclass?classId="+classId);
    }
}