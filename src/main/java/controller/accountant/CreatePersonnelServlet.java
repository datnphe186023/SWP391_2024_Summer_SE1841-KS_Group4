/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.accountant;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import models.personnel.PersonnelDAO;

/**
 *
 * @author asus
 */
@WebServlet(name="CreatePersonnelServlet", urlPatterns={"/accountant/createpersonnel"})
public class CreatePersonnelServlet extends HttpServlet {
   
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
            out.println("<title>Servlet CreatePersonnelServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreatePersonnelServlet at " + request.getContextPath () + "</h1>");
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
      String action = request.getParameter("action");
       if(action.equalsIgnoreCase("create")){
       String xfirstname = request.getParameter("firstname");
       String xlastname = request.getParameter("lastname");
       String xbirthday = request.getParameter("birthday");
       String xaddress = request.getParameter("address");
       String xgender = request.getParameter("gender");
       String xemail = request.getParameter("email");
       String xphone = request.getParameter("phone");
       String xrole = request.getParameter("role");
       String xavatar = request.getParameter("avatar");
       PersonnelDAO pdao = new PersonnelDAO();
       int role = Integer.parseInt(xrole);
       int gender = Integer.parseInt(xgender);
       String id =generateId(role);
       
       pdao.insertPersonnel(id, xfirstname, xlastname, gender, xbirthday, xaddress, xemail, xphone, role, xavatar);
       }
       response.sendRedirect("listpersonnel");
        
    }
    private String generateId(int role){
        String id ;
        int newid ;
        PersonnelDAO pdao = new PersonnelDAO();
        newid= pdao.getNumberOfPersonByRole(role)+1;
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        id= decimalFormat.format(newid);
        if (role == 0) {
            id = "AD" + id;
        } else if (role == 1) {
            id = "HT" + id;
        } else if (role == 2) {
            id = "AS" + id;
        } else if (role==3){
            id = "ACC"+ id;
        }else if (role == 4){
            id = "TEA"+ id;
        }
        
        return id;
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
