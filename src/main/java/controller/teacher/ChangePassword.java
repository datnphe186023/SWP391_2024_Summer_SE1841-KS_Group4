/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.teacher;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.user.User;
import models.user.UserDAO;

/**
 *
 * @author Admin
 */
public class ChangePassword extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet ChangePassword</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangePassword at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        // Get the current session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        // Retrieve the new password and confirm password from the request
        String oldPass = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confPassword = request.getParameter("confirmPassword");

        // Check if new password and confirm password are not null and match
        if (newPassword != null && confPassword != null 
                && newPassword.equals(confPassword)
                && oldPass.equals(user.getPassword())) {
            user.setPassword(newPassword);
            // Attempt to update the user's password
            boolean success = userDAO.updateNewPassword(user);
            if (success) {
                request.setAttribute("noti", "Đã đổi mật khẩu thành công !");
                session.setAttribute("user", user);
                request.getRequestDispatcher("information_teacher.jsp").forward(request, response);
            } else {
                request.setAttribute("noti", "Đổi mật khẩu thất bại!");
            }
        } else {
            // Passwords do not match
            request.setAttribute("fail", "Mật khẩu không khớp!");
            request.getRequestDispatcher("information_teacher.jsp").forward(request, response);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
