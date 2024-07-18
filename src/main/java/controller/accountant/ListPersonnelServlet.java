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

import models.personnel.IPersonnelDAO;
import models.personnel.Personnel;
import models.personnel.PersonnelDAO;
import models.role.Role;
import utils.Helper;

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
             String xfirstname = (String) session.getAttribute("firstname");
             String xlastname = (String) session.getAttribute("lastname");
             String xbirthday = (String) session.getAttribute("birthday");
             String xaddress = (String) session.getAttribute("address");
             String xgender = (String) session.getAttribute("gender");
             String xemail = (String) session.getAttribute("email");
             String xphone = (String) session.getAttribute("phone");
             String xrole = (String) session.getAttribute("role");
             String xavatar = (String) session.getAttribute("avatar");

            List<Personnel> persons = new ArrayList<Personnel>();
            List<Role> roles = new ArrayList<>();
            List<String> statuss = new ArrayList<>();
            List<Personnel> waitlist = new ArrayList<>();
            IPersonnelDAO personnelDAO = new PersonnelDAO();
            persons = personnelDAO.getAllPersonnels();
            roles = personnelDAO.getAllPersonnelRole();
            statuss = personnelDAO.getAllStatus();
            waitlist = personnelDAO.getPersonnelByStatus("đang chờ xử lý");
            request.setAttribute("message", message);
            request.setAttribute("type", type);
            if(type!=null ) {
                if (type.equalsIgnoreCase("fail")) {
                    request.setAttribute("firstname", xfirstname);
                    request.setAttribute("lastname", xlastname);
                    request.setAttribute("birthday", xbirthday);
                    request.setAttribute("address", xaddress);
                    request.setAttribute("gender", xgender);
                    request.setAttribute("email", xemail);
                    request.setAttribute("phone", xphone);
                    request.setAttribute("role", xrole);
                    request.setAttribute("avatar", xavatar);
                }
                session.removeAttribute("firstname");
                session.removeAttribute("lastname");
                session.removeAttribute("birthday");
                session.removeAttribute("address");
                session.removeAttribute("gender");
                session.removeAttribute("email");
                session.removeAttribute("phone");
                session.removeAttribute("role");
                session.removeAttribute("avatar");
            }
            request.setAttribute("selectedstatus", "all");
            request.setAttribute("selectedrole", "all");
            request.setAttribute("persons", persons);
            request.setAttribute("roles", roles);
            request.setAttribute("waitlist", waitlist);
            request.setAttribute("statuss", statuss);
            request.getRequestDispatcher("listPersonnel.jsp").forward(request, response);
            session.removeAttribute("message");
            session.removeAttribute("type");
//     if(type!=null ) {
//
//             session.removeAttribute("firstname");
//             session.removeAttribute("lastname");
//             session.removeAttribute("birthday");
//             session.removeAttribute("address");
//             session.removeAttribute("gender");
//             session.removeAttribute("email");
//             session.removeAttribute("phone");
//             session.removeAttribute("role");
//             session.removeAttribute("avatar");
//
//     }
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

        List<Personnel> persons = new ArrayList<Personnel>();
        List<Role> roles = new ArrayList<>();
        IPersonnelDAO personnelDAO = new PersonnelDAO();
        roles = personnelDAO.getAllPersonnelRole();

            if(status.equalsIgnoreCase("all")&& role.equalsIgnoreCase("all")){
                 persons = personnelDAO.getAllPersonnels();
             }
            else if(!status.equalsIgnoreCase("all") && role.equalsIgnoreCase("all")){
                  persons =personnelDAO.getPersonnelByStatus(status);
                 
             }else if(!role.equalsIgnoreCase("all") && status.equalsIgnoreCase("all")){
                 try{
                 int xrole = Integer.parseInt(role);
                 persons = personnelDAO.getPersonnelByRole(xrole);
                 }catch (NumberFormatException e){
                     persons = personnelDAO.getPersonnelByRole(-1);
                 }
                
             } else{
            persons = personnelDAO.getPersonnelByIdNameRoleStatus(status, role);
             }
        List<String> statuss = new ArrayList<>();
        statuss = personnelDAO.getAllStatus();
        request.setAttribute("statuss", statuss);
        List<Personnel> waitlist = new ArrayList<>();
        waitlist = personnelDAO.getPersonnelByStatus("đang chờ xử lý");
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
