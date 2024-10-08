/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.academicstaff;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.pupil.IPupilDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;

/**
 *
 * @author TuyenCute
 */
@WebServlet(name = "PupilProfileClassServlet", urlPatterns = {"/academicstaff/pupilprofileclass"})
public class PupilProfileClassServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PupilProfileClassServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PupilProfileClassServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("edit")) {
            String id = request.getParameter("id");
            String classId = request.getParameter("classId");
            IPupilDAO pupilDAO = new PupilDAO();
            Pupil pupil = pupilDAO.getPupilsById(id);
            request.setAttribute("classId", classId);
            request.setAttribute("pupil", pupil);
            request.getRequestDispatcher("editInformationPupilsClass.jsp").forward(request, response);
        } else {
            String id = request.getParameter("id");
            HttpSession session = request.getSession();
            String classId = "";
            classId = request.getParameter("classId");
            request.setAttribute("classId", classId);
            session.removeAttribute("classId");
            IPupilDAO pupilDAO = new PupilDAO();
            Pupil pupil = pupilDAO.getPupilsById(id);
            String success = (String) session.getAttribute("success");
            String error = (String) session.getAttribute("error");
            if (success != null) {
                request.setAttribute("toastType", "success");
                request.setAttribute("toastMessage", "Cập nhật thông tin thành công");
                session.removeAttribute(success);
            }
            if (error != null) {
                request.setAttribute("toastType", "error");
                request.setAttribute("toastMessage", "Cập nhật thông tin thất bại");
                session.removeAttribute(error);
            }
            request.setAttribute("pupil", pupil);
            request.getRequestDispatcher("informationPupilsClass.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
