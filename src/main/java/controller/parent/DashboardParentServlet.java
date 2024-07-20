/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.parent;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.day.DayDAO;
import models.day.IDayDAO;
import models.evaluation.EvaluationDAO;
import models.evaluation.HealthCheckUpDAO;
import models.evaluation.IEvaluationDAO;
import models.evaluation.IHealthCheckUpDAO;
import models.event.Event;
import models.event.EventDAO;
import models.event.IEventDAO;
import models.notification.INotificationDAO;
import models.notification.NotificationDAO;
import models.pupil.IPupilAttendanceDAO;
import models.pupil.IPupilDAO;
import models.pupil.PupilAttendanceDAO;
import models.pupil.PupilDAO;
import models.user.User;
import models.week.IWeekDAO;
import models.week.WeekDAO;

/**
 *
 * @author Admin
 */
public class DashboardParentServlet extends HttpServlet {
   

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
        User user = (User) request.getSession().getAttribute("user");
        INotificationDAO notificationDAO = new NotificationDAO();
        IEvaluationDAO evaluationDAO = new EvaluationDAO();
        IDayDAO dayDAO = new DayDAO();
        IWeekDAO weekDAO = new WeekDAO();
        IHealthCheckUpDAO healthCheckUpDAO = new HealthCheckUpDAO();
        IPupilDAO pupilDAO = new PupilDAO();
        IPupilAttendanceDAO pupilAttendanceDAO = new PupilAttendanceDAO();
        IEventDAO eventDAO = new EventDAO();
        List<Event> events = eventDAO.getFutureEvent(user.getRoleId());
        Date currentDate = Date.from(Instant.now());
        String evaluation = "";
        String takeattendance = "";
        if(weekDAO.getCurrentWeek(currentDate)==null){
            evaluation = "đang không trong năm học";
            takeattendance = "đang không trong năm học";
        }else{
            evaluation = evaluationDAO.getEvaluationByPupilIdAndDay(pupilDAO.getPupilByUserId(user.getId()).getId(),dayDAO.getDateIDbyDay(currentDate)).getEvaluation();
            takeattendance = pupilAttendanceDAO.getAttendanceByPupilAndDay(pupilDAO.getPupilByUserId(user.getId()).getId(),dayDAO.getDateIDbyDay(currentDate)).getStatus();
        }


        int notifications = notificationDAO.getListNotifiByRoleId(user.getRoleId()).size();
        if(notificationDAO.getListNotifiByRoleId(user.getRoleId()).isEmpty()){
            notifications = 0;
        }
        request.setAttribute("listEvents", events);
        request.setAttribute("evaluation", evaluation);
        request.setAttribute("takeattendance", takeattendance);
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
