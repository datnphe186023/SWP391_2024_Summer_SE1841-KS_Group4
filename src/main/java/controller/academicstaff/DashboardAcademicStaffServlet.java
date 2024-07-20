/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.academicstaff;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.classes.ClassDAO;
import models.classes.IClassDAO;
import models.day.DayDAO;
import models.day.IDayDAO;
import models.event.EventDAO;
import models.personnel.IPersonnelAttendanceDAO;
import models.personnel.PersonnelAttendanceDAO;
import models.pupil.IPupilDAO;
import models.pupil.PupilDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYearDAO;
import models.user.User;

/**
 *
 * @author Admin
 */
public class DashboardAcademicStaffServlet extends HttpServlet {
   
    

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
        EventDAO eventDAO = new EventDAO();
        IPupilDAO pupilDAO = new PupilDAO();
        IClassDAO classDAO = new ClassDAO();
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        IDayDAO dayDAO = new DayDAO();
        IPersonnelAttendanceDAO personnelAttendanceDAO = new PersonnelAttendanceDAO();
        HttpSession session = request.getSession();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

        String formattedDate = formatter.format(new Date());
        if(schoolYearDAO.getClosestSchoolYears()!=null){
            request.setAttribute("listClass",classDAO.getByStatus("đã được duyệt",schoolYearDAO.getClosestSchoolYears().getId()));
        }else if(schoolYearDAO.getSchoolYearByDate(new Date())!=null) {
            request.setAttribute("listClass",classDAO.getByStatus("đã được duyệt",schoolYearDAO.getSchoolYearByDate(new Date()).getId()));
        }else {
            request.setAttribute("listClass",null);
        }
        if( dayDAO.getDayByDate(formattedDate)!=null){
            User user = (User)session.getAttribute("user");
            request.setAttribute("attendance",personnelAttendanceDAO.getAttendanceByPersonnelAndDay(user.getUsername(), dayDAO.getDayByDate(formattedDate).getId()));
        }
        request.setAttribute("listEvents",eventDAO.getFutureEvent(2));
        request.setAttribute("numberOfStudent",pupilDAO.getPupilByStatus("đang theo học").size());
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
