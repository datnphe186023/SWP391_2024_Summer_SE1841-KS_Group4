package controller.headteacher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.pupil.PupilDAO;

import java.io.IOException;


@WebServlet(name = "headteacher/ReviewPupilServlet", value = "/headteacher/reviewpupil")
public class ReviewPupilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PupilDAO pupilDAO = new PupilDAO();
        String action = request.getParameter("action");
        String pupilId = request.getParameter("id");
        String message ="";
        if(action!=null){
            if(action.equals("accept")){
                pupilDAO.updatePupilStatus(pupilId,"đã duyệt - chưa có lớp");
                message="Đã duyệt thành công";
            }else if(action.equals("decline")){
                pupilDAO.updatePupilStatus(pupilId,"không được duyệt");
                message="Đã từ chối";
            }
        }
        request.setAttribute("message",message);
        request.setAttribute("listPupil",pupilDAO.getPupilByStatus("đang chờ xử lý"));
        request.getRequestDispatcher("headteacher_reviewnewpupil.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}