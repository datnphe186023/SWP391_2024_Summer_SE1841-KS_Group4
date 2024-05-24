/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import models.personnel.PersonnelDAO;
import models.personnel.Personnel;
import models.user.User;
import models.user.UserDAO;

/**
 *
 * @author TuyenCute
 */
public class SearchPersonnelServlet extends HttpServlet {

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
            out.println("<title>Servlet SearchPersonnelServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchPersonnelServlet at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Map<Integer, String> roleMap = new HashMap<>();
        Map<Byte, String> roleDis = new HashMap<>();
        roleMap.put(0, "Admin");
        roleMap.put(1, "Headteacher");
        roleMap.put(2, "Academic Staff");
        roleMap.put(3, "Accountant");
        roleMap.put(4, "Teacher");
        roleMap.put(5, "Parent");

        roleDis.put((byte) 0, "Active");
        roleDis.put((byte) 1, "Disable");
        String search = request.getParameter("search");
        UserDAO dao = new UserDAO();
        User u = dao.searchById(search);
        if (u == null) {
            request.setAttribute("mess", "Không tìm thấy, Hãy Thử Lại ID Khác.");
            request.getRequestDispatcher("/admin/dashboard_admin_errorsManager.jsp").forward(request, response);
        } else {
            request.setAttribute("u", u);
            request.setAttribute("roleMap", roleMap);
            request.setAttribute("roleDis", roleDis);
            request.getRequestDispatcher("../admin/dashboard_admin_searchUser.jsp").forward(request, response);
        }
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Map<Integer, String> roleMap = new HashMap<>();
        roleMap.put(0, "Admin");
        roleMap.put(1, "Headteacher");
        roleMap.put(2, "Academic Staff");
        roleMap.put(3, "Accountant");
        roleMap.put(4, "Teacher");
        roleMap.put(5, "Parent");
        String search = request.getParameter("search");
        PersonnelDAO dao = new PersonnelDAO();
        Personnel p = dao.searchById(search);
        if (p == null) {
            request.setAttribute("mess", "Không tìm thấy, Hãy Thử Lại ID Khác.");
            request.getRequestDispatcher("/admin/dashboard_admin_errors.jsp").forward(request, response);
        } else {
            request.setAttribute("p", p);
            request.setAttribute("roleMap", roleMap);
            request.getRequestDispatcher("/admin/dashboard_admin_search.jsp").forward(request, response);
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
