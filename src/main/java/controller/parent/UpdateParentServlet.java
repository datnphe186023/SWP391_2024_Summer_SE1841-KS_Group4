/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.parent;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.pupil.Pupil;
import models.pupil.PupilDAO;

/**
 *
 * @author Admin
 */
public class UpdateParentServlet extends HttpServlet {
   
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
            out.println("<title>Servlet UpdateParentServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateParentServlet at " + request.getContextPath () + "</h1>");
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
    
    // Lấy thông tin pupil từ session
    HttpSession session = request.getSession();
    Pupil pupil = (Pupil) session.getAttribute("pupil");
    
    
        // Lấy thông tin cần update từ request
        String motherName = request.getParameter("mother_name");
        String motherPhone = request.getParameter("mother_phone");
        String fatherName = request.getParameter("father_name");
        String fatherPhone = request.getParameter("father_phone");
        String email =request.getParameter("email");
        String address = request.getParameter("address");
        
        // Cập nhật thông tin của pupil
        pupil.setMotherName(motherName);
        pupil.setMotherPhoneNumber(motherPhone);
        pupil.setFatherName(fatherName);
        pupil.setFatherPhoneNumber(fatherPhone);
        pupil.setEmail(email);
        pupil.setAddress(address);
        
        // Cập nhật thông tin của pupil trong cơ sở dữ liệu
        PupilDAO dao = new PupilDAO();
        dao.updateParent(pupil);
        
        // Lưu lại pupil đã được cập nhật vào session 
        session.setAttribute("pupil", pupil);
        
        // Redirect hoặc forward đến trang cần thiết
        response.sendRedirect("information");
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
