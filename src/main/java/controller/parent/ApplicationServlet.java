package controller.parent;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.application.Application;
import models.application.ApplicationDAO;
import models.application.ApplicationType;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "parent/ApplicationServlet", value = "/parent/application")
public class ApplicationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ApplicationDAO applicationDAO = new ApplicationDAO();
        List<ApplicationType> applicationTypes = applicationDAO.getAllApplicationTypes("pupil");
        request.setAttribute("applicationTypes", applicationTypes);
        request.getRequestDispatcher("sendApplication.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        String details = request.getParameter("details");
        ApplicationDAO applicationDAO = new ApplicationDAO();
        try{
            Application application = new Application();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}