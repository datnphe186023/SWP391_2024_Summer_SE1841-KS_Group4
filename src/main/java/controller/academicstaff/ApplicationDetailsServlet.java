package controller.academicstaff;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.application.Application;
import models.application.ApplicationDAO;
import models.personnel.PersonnelDAO;
import models.user.User;

import java.io.IOException;
import java.util.Date;

@WebServlet(name = "academicstaff/ApplicationDetailsServlet", value = "/academicstaff/applicationdetails")
public class ApplicationDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String applicationId = request.getParameter("id");
        request.setAttribute("applicationId", applicationId);
        ApplicationDAO appDAO = new ApplicationDAO();
        Application application = appDAO.getApplicationById(applicationId);
        request.setAttribute("application", application);
        request.getRequestDispatcher("applicationDetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //action determine if application is approved or reject
        String action = request.getParameter("action");
        //this is process note
        String note = request.getParameter("note");
        ApplicationDAO appDAO = new ApplicationDAO();
        //creating application object for further update in database
        Application application = new Application();
        application.setId(request.getParameter("id"));
        application.setProcessedAt(new Date());
        application.setProcessNote(note);
        if (action.equals("approve")) {
            application.setStatus("đã duyệt");
        } else if (action.equals("reject")) {
            application.setStatus("đã từ chối");
        }
        //get personnel id of personnel who process this application
        PersonnelDAO personnelDAO = new PersonnelDAO();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        application.setProcessedBy(personnelDAO.getPersonnelByUserId(user.getId()));
        //update application status in database and get result
        String result = appDAO.processApplication(application);
        //sending result to JSP
        if (result.equals("success")) {
            session.setAttribute("toastType", "success");
            session.setAttribute("toastMessage", "Gửi đơn thành công");
        } else {
            session.setAttribute("toastType", "error");
            session.setAttribute("toastMessage", result);
        }
        response.sendRedirect("applicationdetails?id=" + application.getId());
    }
}