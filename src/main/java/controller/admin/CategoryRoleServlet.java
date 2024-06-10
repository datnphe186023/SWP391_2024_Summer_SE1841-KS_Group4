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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.personnel.PersonnelDAO;
import models.personnel.Personnel;
import models.pupil.Pupil;
import models.pupil.PupilDAO;

/**
 *
 * @author TuyenCute
 */
public class CategoryRoleServlet extends HttpServlet {

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
            out.println("<title>Servlet CategoryRoleServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CategoryRoleServlet at " + request.getContextPath() + "</h1>");
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
        String role_id = request.getParameter("role");
        List<Personnel> listPersonnel = new ArrayList<>();
        List<Pupil> listPupils = new ArrayList<>();
        Map<Integer, String> roleMap = new HashMap<>();
        roleMap.put(0, "Admin");
        roleMap.put(1, "Headteacher");
        roleMap.put(2, "Academic Staff");
        roleMap.put(3, "Accountant");
        roleMap.put(4, "Teacher");
        roleMap.put(5, "Parent");
        switch (role_id) {
            case "0":
                listPersonnel = new PersonnelDAO().getPersonelByRoleandNonUserId(0);
                request.setAttribute("roleMap", roleMap);
                request.setAttribute("listPersonnel", listPersonnel);
                request.getRequestDispatcher("adminCreateUser.jsp").forward(request, response);
                break;
            case "1":
                listPersonnel = new PersonnelDAO().getPersonelByRoleandNonUserId(1);
                request.setAttribute("roleMap", roleMap);
                request.setAttribute("listPersonnel", listPersonnel);
                request.getRequestDispatcher("adminCreateUser.jsp").forward(request, response);
                break;
            case "2":
                listPersonnel = new PersonnelDAO().getPersonelByRoleandNonUserId(2);
                request.setAttribute("roleMap", roleMap);
                request.setAttribute("listPersonnel", listPersonnel);
                request.getRequestDispatcher("adminCreateUser.jsp").forward(request, response);
                break;
            case "3":
                listPersonnel = new PersonnelDAO().getPersonelByRoleandNonUserId(3);
                request.setAttribute("roleMap", roleMap);
                request.setAttribute("listPersonnel", listPersonnel);
                request.getRequestDispatcher("adminCreateUser.jsp").forward(request, response);
                break;
            case "4":
                listPersonnel = new PersonnelDAO().getPersonelByRoleandNonUserId(4);
                request.setAttribute("roleMap", roleMap);
                request.setAttribute("listPersonnel", listPersonnel);
                request.getRequestDispatcher("adminCreateUser.jsp").forward(request, response);
                break;
            case "5":
                listPupils = new PupilDAO().getPupilNonUserId();
                request.setAttribute("roleMap", roleMap);
                request.setAttribute("listPupils", listPupils);
                request.getRequestDispatcher("adminCreateUser.jsp").forward(request, response);
                break;
            case "6":
                response.sendRedirect("createuser");
                break;
            default:
                throw new AssertionError();
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
        processRequest(request, response);
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
