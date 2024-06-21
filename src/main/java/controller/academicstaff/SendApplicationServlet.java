package controller.academicstaff;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.application.Application;
import models.application.ApplicationDAO;
import models.application.ApplicationType;
import models.application.IApplicationDAO;
import models.user.User;
import utils.Helper;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "academicstaff/SendApplicationServlet", value = "/academicstaff/sendapplication")
public class SendApplicationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //getting toast message sent from do post after send application (if exist)
        HttpSession session = request.getSession();
        String toastType = "", toastMessage = "";
        if (session.getAttribute("toastType") != null) {
            toastType = session.getAttribute("toastType").toString();
            toastMessage = session.getAttribute("toastMessage").toString();
        }
        session.removeAttribute("toastType");
        session.removeAttribute("toastMessage");
        request.setAttribute("toastType", toastType);
        request.setAttribute("toastMessage", toastMessage);
        IApplicationDAO applicationDAO = new ApplicationDAO();
        List<ApplicationType> applicationTypes = applicationDAO.getAllApplicationTypes("personnel");
        request.setAttribute("applicationTypes", applicationTypes);
        request.getRequestDispatcher("sendApplication.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        String details = Helper.formatString(request.getParameter("details"));
        IApplicationDAO applicationDAO = new ApplicationDAO();
        Application application = new Application();
        application.setType(applicationDAO.getById(type));
        application.setDetails(details);
        application.setStatus("đang chờ xử lý");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        application.setCreatedBy(user.getId());
        application.setCreatedAt(new Date());

        //getting result for toast message
        String result = applicationDAO.addApplication(application);
        if (result.equals("success")) {
            session.setAttribute("toastType", "success");
            session.setAttribute("toastMessage", "Gửi đơn thành công");
        } else {
            session.setAttribute("toastType", "error");
            session.setAttribute("toastMessage", result);
        }

        response.sendRedirect("sendapplication");
    }
}