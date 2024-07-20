/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.accountant;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.event.Event;
import models.event.EventDAO;
import models.event.IEventDAO;
import models.notification.INotificationDAO;
import models.notification.NotificationDAO;
import models.personnel.IPersonnelDAO;
import models.personnel.Personnel;
import models.personnel.PersonnelDAO;
import models.pupil.IPupilDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;
import models.user.User;

/**
 *
 * @author Admin
 */
public class DashboardAccountantServlet extends HttpServlet {
   
    

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
        IPupilDAO pupilDAO = new PupilDAO();
        IPersonnelDAO personnelDAO = new PersonnelDAO();
        INotificationDAO notificationDAO = new NotificationDAO();
        IEventDAO eventDAO = new EventDAO();
        User user = (User) request.getSession().getAttribute("user");
        List<Event> events = eventDAO.getFutureEvent(user.getRoleId());

        int notifications = notificationDAO.getListNotifiByUserId(user.getId()).size();
        if(notificationDAO.getListNotifiByUserId(user.getId()).isEmpty()){
            notifications = 0;
        }
        int pupils = pupilDAO.getPupilByStatus("đang theo học").size();
        int personnels = personnelDAO.getPersonnelByStatus("đang làm việc").size();
        request.setAttribute("listEvents",events);
        request.setAttribute("pupils", pupils);
        request.setAttribute("personnels", personnels);
        request.setAttribute("notifications", notifications);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
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
