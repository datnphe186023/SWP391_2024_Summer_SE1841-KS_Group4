package controller.headteacher;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.subject.Subject;
import models.subject.SubjectDAO;


@WebServlet(name = "headteacher/ReviewSubjectServlet", value = "/headteacher/reviewsubject")
public class ReviewSubjectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SubjectDAO subjectDAO = new SubjectDAO();
        request.setAttribute("listSubjectPending",subjectDAO.getSubjectsByStatus("đang chờ phê duyệt"));
        request.getRequestDispatcher("reviewSubject.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SubjectDAO subjectDAO = new SubjectDAO();
        String action = request.getParameter("action");
        String subjectId= request.getParameter("id");
        String toastMessage ="";
        String toastType="";
        boolean result = false;
        if(action.equals("accept")){
            result = subjectDAO.updateStatusById(subjectId,"đã được phê duyệt");
        }else if (action.equals("decline")){
             subjectDAO.updateStatusById(subjectId,"không được duyệt");
        }
        if (result){
            toastMessage="Duyệt thành công";
            toastType = "success";
        }else {
            toastMessage="Từ chối thành công";
            toastType="error";
        }
        request.setAttribute("listSubjectPending",subjectDAO.getSubjectsByStatus("đang chờ phê duyệt"));
        request.setAttribute("toastMessage",toastMessage);
        request.setAttribute("toastType",toastType);
        request.getRequestDispatcher("reviewSubject.jsp").forward(request,response);

    }
}