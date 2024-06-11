package controller.headteacher;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.classes.ClassDAO;
import models.schoolYear.SchoolYearDAO;

import java.io.IOException;

@WebServlet(name = "headteacher/ReviewClassServlet", value = "/headteacher/reviewclass")
public class ReviewClassServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        ClassDAO classDAO = new ClassDAO();
        String schoolYearId = request.getParameter("schoolYearId");
        try{
            if (action==null) {
                if (schoolYearId == null) {
                    response.sendRedirect("class");
                } else{
                    //handling toast message after process class
                    HttpSession session = request.getSession();
                    String result = (String) session.getAttribute("result");
                    session.removeAttribute("result");
                    if (result != null) {
                        if (result.equals("success")) {
                            request.setAttribute("toastType", result);
                            request.setAttribute("toastMessage", "Duyệt thành công");
                        } else {
                            request.setAttribute("toastType", "error");
                            request.setAttribute("toastMessage", result);
                        }
                    }
                    //get all processing classes
                    request.setAttribute("classes", classDAO.getByStatus("đang chờ duyệt", schoolYearId));
                    request.setAttribute("schoolYearId", schoolYearId);
                    request.setAttribute("numberOfPendingClasses", classDAO.getByStatus("đang chờ duyệt", schoolYearId).size());
                    request.getRequestDispatcher("reviewClass.jsp").forward(request, response);
                }
            } else if (action.equals("accept") || action.equals("decline")) {
                String classId = request.getParameter("id");
                String result = classDAO.reviewClass(action, classId);
                HttpSession session = request.getSession();
                session.setAttribute("result", result);
                response.sendRedirect("reviewclass?schoolYearId=" + schoolYearId);
            } else {
                response.sendRedirect("class");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}