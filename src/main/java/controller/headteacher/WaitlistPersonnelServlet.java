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
import jakarta.servlet.http.HttpSession;
import models.personnel.IPersonnelDAO;
import models.personnel.Personnel;
import models.personnel.PersonnelDAO;

/**
 *
 * @author asus
 */
@WebServlet(name = "headteacher/WaitlistPersonnelServlet", value = "/headteacher/waitlistpersonnel")
public class WaitlistPersonnelServlet extends HttpServlet {
   
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
            out.println("<title>Servlet WaitlistPersonnelServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet WaitlistPersonnelServlet at " + request.getContextPath () + "</h1>");
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
        IPersonnelDAO personnelDAO = new PersonnelDAO();
        String action = request.getParameter("action");
        String personId = request.getParameter("id");
        String message ="";
        String type = "";
        if(action!=null){
            if(action.equals("accept")){
                personnelDAO.updatePersonnelStatus(personId,"đang làm việc");
                message="Đã duyệt thành công";
                type = "success";
            }else if(action.equals("decline")){
                personnelDAO.updatePersonnelStatus(personId,"không được duyệt");
                message="Đã từ chối";
                type = "fail";
            }
        }
        request.setAttribute("message",message);
        request.setAttribute("type",type);
        request.setAttribute("waitlistpersonnel",personnelDAO.getPersonnelByStatus("đang chờ xử lý"));
        request.getRequestDispatcher("waitlistPersonnel.jsp").forward(request,response);
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
        session.removeAttribute("message");
        session.removeAttribute("type");

        IPersonnelDAO personnelDAO = new PersonnelDAO();
        String action = request.getParameter("action");
        String personId = request.getParameter("id");
        String message ="";
        String type = "";
        if(action!=null){
            if(action.equals("accept")){
                personnelDAO.updatePersonnelStatus(personId,"đang làm việc");
                message="Đã duyệt thành công";
                type = "success";
            }else if(action.equals("decline")){
                personnelDAO.updatePersonnelStatus(personId,"không được duyệt");
                message="Đã từ chối";
                type = "fail";
            }
        }
        session.setAttribute("message", message);
        session.setAttribute("type", type);
      //  request.setAttribute("waitlistpersonnel",pdao.getPersonnelByStatus("đang chờ xử lý"));
      //  request.getRequestDispatcher("waitlistPersonnel.jsp").forward(request,response);
        response.sendRedirect("viewpersonnel?id="+personId);
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
