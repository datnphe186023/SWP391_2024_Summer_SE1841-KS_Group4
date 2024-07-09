package controller.headteacher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.classes.ClassDAO;
import models.classes.IClassDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "headteacher/ReviewClassServlet", value = "/headteacher/reviewclass")
public class ReviewClassServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IClassDAO classDAO = new ClassDAO();
        String schoolYearId = request.getParameter("schoolYearId");
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        List<SchoolYear> schoolYears = schoolYearDAO.getFutureSchoolYears();
        request.setAttribute("schoolYears", schoolYears);
        try {
            if (schoolYearId == null) {
                schoolYearId = schoolYearDAO.getLatest().getId();
            }
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
            request.setAttribute("selectedSchoolYearId", schoolYearId);
            request.getRequestDispatcher("reviewClass.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String schoolYearId = request.getParameter("schoolYearId");
        IClassDAO classDAO = new ClassDAO();
        if (action.equals("accept") || action.equals("decline")) {
            String classId = request.getParameter("id");
            String result = classDAO.reviewClass(action, classId);
            HttpSession session = request.getSession();
            session.setAttribute("result", result);
            response.sendRedirect("reviewclass?schoolYearId=" + schoolYearId);
        }
    }
}