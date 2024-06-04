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
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import models.personnel.Personnel;
import models.personnel.PersonnelDAO;
import models.role.Role;

/**
 *
 * @author asus
 */
@WebServlet(name="ListPersonnelServlet", urlPatterns={"/accountant/listpersonnel"})
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
 @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         HttpSession session = request.getSession(true);
        String message = (String) session.getAttribute("message");
        String type = (String) session.getAttribute("type");
        List<Personnel> persons = new ArrayList<Personnel>();
        List<Role> roles = new ArrayList<>();
        List<String> statuss = new ArrayList<>();
        List<Personnel> waitlist = new ArrayList<>();
        PersonnelDAO pdao = new PersonnelDAO();
        persons = pdao.getPersonnelByStatus("đang làm việc");
        roles = pdao.getAllPersonnelRole();
        statuss = pdao.getAllStatus();
        waitlist = pdao.getPersonnelByStatus("đang chờ xử lý");
        request.setAttribute("message", message);
        request.setAttribute("type", type);
        request.setAttribute("persons", persons);
        request.setAttribute("roles", roles);
        request.setAttribute("waitlist", waitlist);
        request.setAttribute("statuss", statuss);
        request.getRequestDispatcher("listPersonnel.jsp").forward(request, response);
        session.removeAttribute("message");
        session.removeAttribute("type");
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

        HttpSession session = request.getSession(true);
        String message = (String) session.getAttribute("message");
        String type = (String) session.getAttribute("type");
        String role = request.getParameter("role");
        String status = request.getParameter("status");
        String search = request.getParameter("search");
        System.out.println(role);
        System.out.println(status);
        System.out.println(search);
        List<Personnel> persons = new ArrayList<Personnel>();
        List<Role> roles = new ArrayList<>();
        PersonnelDAO pdao = new PersonnelDAO();
        roles = pdao.getAllPersonnelRole();
        if ((role != null || status != null)&& search == null) {
            if(status.equalsIgnoreCase("all")&& role.equalsIgnoreCase("all")){
                 persons = pdao.getAllPersonnels();
             }
            else if(!status.equalsIgnoreCase("all") && role.equalsIgnoreCase("all")){
                  persons =pdao.getPersonnelByStatus(status);
                 
             }else if(!role.equalsIgnoreCase("all") && status.equalsIgnoreCase("all")){
                 try{
                 int xrole = Integer.parseInt(role);
                 persons = pdao.getPersonnelByRole(xrole);
                 }catch (NumberFormatException e){
                     persons = pdao.getPersonnelByRole(-1);
                 }
                
             } else{
            persons = pdao.getPersonnelByIdNameRoleStatus(status, role);
             }
        }else if(search != null){
            persons = pdao.getPersonnelByNameOrId(formatString(search));
        } 
        List<String> statuss = new ArrayList<>();
        statuss = pdao.getAllStatus();
        request.setAttribute("statuss", statuss);
        List<Personnel> waitlist = new ArrayList<>();
        waitlist = pdao.getPersonnelByStatus("đang chờ xử lý");
        request.setAttribute("searchdata", search);
        request.setAttribute("selectedstatus", status);
        request.setAttribute("selectedrole", role);
        request.setAttribute("message", message);
        request.setAttribute("type", type);
        request.setAttribute("waitlist", waitlist);
        request.setAttribute("roles", roles);
        request.setAttribute("persons", persons);
        request.getRequestDispatcher("listPersonnel.jsp").forward(request, response);
        session.removeAttribute("message");
        session.removeAttribute("type");
        
    }
    
 private String formatString(String search){
        StringBuilder result = new StringBuilder();
        String[] searchArray = search.split("\\s+");
        for(int i=0;i<searchArray.length;i++){
            result.append(searchArray[i]).append(" ");
        }
        return result.toString().trim();
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
