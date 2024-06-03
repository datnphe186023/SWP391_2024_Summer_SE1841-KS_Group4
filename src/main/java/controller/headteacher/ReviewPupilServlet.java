package controller.headteacher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.pupil.PupilDAO;

import java.io.IOException;


@WebServlet(name = "headteacher/ReviewPupilServlet", value = "/headteacher/reviewpupil")
public class ReviewPupilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PupilDAO pupilDAO = new PupilDAO();
        request.setAttribute("listPupil",pupilDAO.getPupilByStatus("đang chờ xử lý"));
        request.getRequestDispatcher("ReviewNewPupil.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        PupilDAO pupilDAO = new PupilDAO();
        String action = request.getParameter("action");
        String pupilId = request.getParameter("id");
        String toastMessage ="";
        String toastType="";
        boolean result = false;
        if(action!=null){
            if(action.equals("accept")){
                result =pupilDAO.updatePupilStatus(pupilId,"đang theo học");
            }else if(action.equals("decline")){
                pupilDAO.updatePupilStatus(pupilId,"không được duyệt");
            }
        }
        if (result){
            toastMessage="Duyệt thành công";
            toastType = "success";
        }else {
            toastMessage="Từ chối thành công";
            toastType="error";
        }
        session.setAttribute("toastMessage",toastMessage);
        session.setAttribute("toastType",toastType);
        response.sendRedirect("listpupil");
    }
}