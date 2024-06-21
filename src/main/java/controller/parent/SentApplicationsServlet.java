package controller.parent;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.application.Application;
import models.application.ApplicationDAO;
import models.application.IApplicationDAO;
import models.user.User;

import java.io.IOException;
import java.util.List;

//this shows sent application from the teacher themselves
@WebServlet(name = "parent/SentApplicationsServlet", value = "/parent/sentapplications")
public class SentApplicationsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IApplicationDAO applicationDAO = new ApplicationDAO();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<Application> sentApplications = applicationDAO.getSentApplications(user.getId());
        request.setAttribute("sentApplications", sentApplications);
        request.getRequestDispatcher("sentApplications.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}