/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.teacher;

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
@WebServlet(name = "UpdatePupilServlet", urlPatterns = {"/teacher/updatepupil"})
public class UpdatePupilServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdatePupilServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdatePupilServlet at " + request.getContextPath() + "</h1>");
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
        Pupil pupil = new Pupil();
        String schoolYear = request.getParameter("schoolYear");
        pupil.setId(request.getParameter("id").trim());
        pupil.setfirstGuardianName(request.getParameter("first_guardian_name").trim());
        pupil.setfirstGuardianPhoneNumber(request.getParameter("firstGuardianPhoneNumber").trim());
        pupil.setsecondGuardianName(request.getParameter("second_guardian_name").trim());
        pupil.setsecondGuardianPhoneNumber(request.getParameter("secondGuardianPhoneNumber").trim());
        pupil.setAddress(request.getParameter("address").trim());
        pupil.setParentSpecialNote(request.getParameter("note").trim());
        IPupilDAO pupilDAO = new PupilDAO();
        boolean p = pupilDAO.updatePupilForTeacher(pupil);
        HttpSession session = request.getSession();
        if (p == true) {
            session.setAttribute("success", "success");
        } else {
            session.setAttribute("error", "error");
        }
        request.setAttribute("schoolYear", schoolYear);
        request.getRequestDispatcher("pupilprofile").forward(request, response);
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
        String schoolYear = request.getParameter("schoolYear");
        String id = request.getParameter("id");
        PupilDAO pupildao = new PupilDAO();
        Pupil pupil = pupildao.getPupilsById(id);
        request.setAttribute("pupil", pupil);
        request.setAttribute("schoolYear", schoolYear);
        request.getRequestDispatcher("editInformationPupil.jsp").forward(request, response);
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
