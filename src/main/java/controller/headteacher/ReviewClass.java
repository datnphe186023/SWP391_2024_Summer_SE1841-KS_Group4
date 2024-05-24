package controller.headteacher;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.classes.ClassDAO;

import java.io.IOException;

@WebServlet(name = "headteacher/ReviewClass", value = "/headteacher/reviewclass")
public class ReviewClass extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        ClassDAO classDAO = new ClassDAO();
        try{
            if (action==null) {
                request.setAttribute("classes", classDAO.getByStatus("đang chờ duyệt"));
                request.getRequestDispatcher("reviewclass.jsp").forward(request, response);
            } else if (action.equals("accept") || action.equals("decline")) {
                String classId = request.getParameter("id");
                classDAO.reviewClass(action, classId);
                response.sendRedirect("reviewclass");
            } else {
                response.sendRedirect("reviewclass");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}