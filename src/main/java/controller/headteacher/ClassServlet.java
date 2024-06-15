package controller.headteacher;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.classes.Class;
import models.classes.ClassDAO;
import models.classes.IClassDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "headteacher/ClassServlet", value = "/headteacher/class")
public class ClassServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
            IClassDAO classDAO = new ClassDAO();
            //send list of all schoolYear to jsp
            request.setAttribute("schoolYears", schoolYearDAO.getAll());
            String schoolYearId = request.getParameter("schoolYearId");
            if (schoolYearId == null) {
                SchoolYear latestSchoolYear = schoolYearDAO.getLatest();
                schoolYearId = latestSchoolYear.getId();
            }
            List<Class> classes = classDAO.getByStatus("đã được duyệt", schoolYearId);
            request.setAttribute("selectedSchoolYearId", schoolYearId);
            request.setAttribute("classes", classes);
            request.getRequestDispatcher("class.jsp").forward(request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        IClassDAO classDAO = new ClassDAO();
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
                    request.setAttribute("classes", classDAO.getByStatus("đang chờ xử lý", schoolYearId));
                    request.setAttribute("schoolYearId", schoolYearId);
                    request.setAttribute("numberOfPendingClasses", classDAO.getByStatus("đang chờ xử lý", schoolYearId).size());
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
}