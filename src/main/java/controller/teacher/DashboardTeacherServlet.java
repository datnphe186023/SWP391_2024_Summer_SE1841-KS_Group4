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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import models.application.ApplicationDAO;
import models.application.IApplicationDAO;
import models.classes.ClassDAO;
import models.classes.Class;
import models.classes.IClassDAO;
import models.day.DayDAO;
import models.day.IDayDAO;
import models.event.EventDAO;
import models.notification.INotificationDAO;
import models.notification.NotificationDAO;
import models.personnel.IPersonnelAttendanceDAO;
import models.personnel.Personnel;
import models.personnel.PersonnelAttendanceDAO;
import models.pupil.IPupilDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;
import models.role.IRoleDAO;
import models.role.RoleDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYearDAO;
import models.user.User;

/**
 *
 * @author Admin
 */
public class DashboardTeacherServlet extends HttpServlet {

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
        EventDAO eventDAO = new EventDAO();
        IPupilDAO pupilDAO = new PupilDAO();
        IClassDAO classDAO = new ClassDAO();

        HttpSession session = request.getSession();
        Personnel personnel = (Personnel) session.getAttribute("personnel");

        Class individualClass = classDAO.getClassNameByTeacher(personnel.getId());
        if (individualClass != null) {

            int listPupilInClass = pupilDAO.getSumPupilInClass(individualClass.getId());
            IApplicationDAO applicationDAO = new ApplicationDAO();
            IRoleDAO roleDAO = new RoleDAO();
            String roleName = roleDAO.getRoleName(personnel.getRoleId());
            int sumApplication = applicationDAO.getForPersonnel(roleName).size();

            INotificationDAO notificationDAO = new NotificationDAO();
            int sumNotification = notificationDAO.getListNotifiByUserId(personnel.getUserId()).size();
            request.setAttribute("listEvents", eventDAO.getFutureEvent(2));
            request.setAttribute("numberOfStudent", pupilDAO.getPupilByStatus("đang theo học").size());
            request.setAttribute("listPupilInClass", listPupilInClass);
            request.setAttribute("sumApplication", sumApplication);
            request.setAttribute("sumNotification", sumNotification);
        }
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
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
