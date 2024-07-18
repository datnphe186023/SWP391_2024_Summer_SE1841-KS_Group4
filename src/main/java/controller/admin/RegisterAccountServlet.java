/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.personnel.IPersonnelDAO;
import models.personnel.PersonnelDAO;
import models.pupil.IPupilDAO;
import models.pupil.PupilDAO;
import models.user.IUserDAO;
import models.user.UserDAO;

/**
 *
 * @author TuyenCute
 */
@WebServlet(name = "RegisterAccountServlet", urlPatterns = {"/admin/registeraccount"})
public class RegisterAccountServlet extends HttpServlet {

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
            out.println("<title>Servlet RegisterAccountServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterAccountServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        // Lấy giá trị của các checkbox được chọn từ request
        String[] selectedUserIds = request.getParameterValues("user_checkbox");
        HttpSession session = request.getSession();
        boolean u;
        // Kiểm tra nếu không có checkbox nào được chọn
        if (selectedUserIds == null || selectedUserIds.length == 0) {
            session.setAttribute("error", "error");
            response.sendRedirect("createuser");
        } else {
            IUserDAO userDAO = new UserDAO();
            IPersonnelDAO personnelDAO = new PersonnelDAO();
            IPupilDAO pupilDAO = new PupilDAO();
            for (String username : selectedUserIds) {
                switch (username.substring(0, 2)) {
                    case "HS":
                        u = userDAO.createNewUser(username, pupilDAO.getPupilsById(username).getEmail(), 5, (byte) 0);
                        if (u == true) {
                            session.setAttribute("success", "success");
                        } else {
                            session.setAttribute("errors", "errors");
                        }
                        break;
                    case "AC":
                        u = userDAO.createNewUser(username, personnelDAO.getPersonnel(username).getEmail(), 3, (byte) 0);
                        if (u == true) {
                            session.setAttribute("success", "success");
                        } else {
                            session.setAttribute("errors", "errors");
                        }
                        break;
                    case "HT":
                        u = userDAO.createNewUser(username, personnelDAO.getPersonnel(username).getEmail(), 1, (byte) 0);
                        if (u == true) {
                            session.setAttribute("success", "success");
                        } else {
                            session.setAttribute("errors", "errors");
                        }
                        break;
                    case "TE":
                        u = userDAO.createNewUser(username, personnelDAO.getPersonnel(username).getEmail(), 4, (byte) 0);
                        if (u == true) {
                            session.setAttribute("success", "success");
                        } else {
                            session.setAttribute("errors", "errors");
                        }
                        break;
                    case "AS":
                        u = userDAO.createNewUser(username, personnelDAO.getPersonnel(username).getEmail(), 2, (byte) 0);
                        if (u == true) {
                            session.setAttribute("success", "success");
                        } else {
                            session.setAttribute("errors", "errors");
                        }
                        break;
                    case "AD":
                        u = userDAO.createNewUser(username, personnelDAO.getPersonnel(username).getEmail(), 0, (byte) 0);
                        if (u == true) {
                            session.setAttribute("success", "success");
                        } else {
                            session.setAttribute("errors", "errors");
                        }
                        break;
                    default:
                        break;
                }
            }
            response.sendRedirect("createuser");
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
