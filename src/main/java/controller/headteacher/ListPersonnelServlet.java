/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.headteacher;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import models.personnel.Personnel;
import models.personnel.PersonnelDAO;
import models.role.Role;

/**
 *
 * @author asus
 */
@WebServlet(name = "headteacher/ListPersonnelServlet", value = "/headteacher/listpersonnel")
public class ListPersonnelServlet extends HttpServlet {
   
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
            out.println("<title>Servlet ListPersonnelServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListPersonnelServlet at " + request.getContextPath () + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        List<Personnel> persons = new ArrayList<Personnel>();
        List<Role> roles = new ArrayList<>();
        List<Personnel> waitlist= new ArrayList<>();
        PersonnelDAO pdao = new PersonnelDAO();
        persons = pdao.getAllPersonnels();
        roles = pdao.getAllPersonnelRole();
        waitlist = pdao.getPersonnelByStatus("đang chờ xử lý");
        request.setAttribute("persons", persons);
        request.setAttribute("roles", roles);
        request.setAttribute("waitlist", waitlist);
        request.getRequestDispatcher("headteacher_listPersonnel.jsp").forward(request, response);
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
        String role = request.getParameter("role");

       String search = request.getParameter("search");
       List<Personnel> persons = new ArrayList<Personnel>();
       List<Role> roles = new ArrayList<>();
        PersonnelDAO pdao = new PersonnelDAO();  
        roles = pdao.getAllPersonnelRole();
       if(role==null){
        persons = pdao.getPersonnelByNameOrId(search);
        request.setAttribute("searchdata", search);
       }else{
          int roleid = Integer.parseInt(role);
          persons = pdao.getPersonnelByRole(roleid);
          request.setAttribute("selectedrole", role);
       }
       List<Personnel> waitlist= new ArrayList<>();
        waitlist = pdao.getPersonnelByStatus("đang chờ xử lý");
        request.setAttribute("waitlist", waitlist);
       request.setAttribute("roles", roles);
       request.setAttribute("persons", persons);
        request.getRequestDispatcher("headteacher_listPersonnel.jsp").forward(request, response);
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
