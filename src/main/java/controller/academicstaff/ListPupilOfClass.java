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
@WebServlet(name = "academicstaff/ListPupilOfClass", value = "/academicstaff/pupilclass")
public class ListPupilOfClass extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PupilDAO pupilDAO = new PupilDAO();
        HttpSession session =  request.getSession();
        String classId = request.getParameter("classId");
        List<Pupil> listPupil = pupilDAO.getListPupilsByClass(classId);
        String search = request.getParameter("information");

        String id = request.getParameter("id");
        if(search!=null){
            listPupil = pupilDAO.searchPupilInClass(search,id);
        }else {
            session.setAttribute("classId",classId); //// lưu classId vào session trước khi search
                                                        //// vì sau khi search sẽ mất classId
        }
        request.setAttribute("listPupil",listPupil);
        request.setAttribute("numberOfPupilsPending",pupilDAO.getPupilByStatus("đang chờ xử lý").size());
        request.getRequestDispatcher("listPupilOfClass.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}