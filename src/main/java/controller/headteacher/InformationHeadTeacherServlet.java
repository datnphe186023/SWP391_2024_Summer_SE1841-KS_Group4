package controller.headteacher;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class InformationHeadTeacherServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        request.getRequestDispatcher("information_headteacher.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("information_headteacher.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
