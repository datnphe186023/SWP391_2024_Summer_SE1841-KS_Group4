/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.headteacher;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import models.classes.ClassDAO;
import models.classes.IClassDAO;
import models.classes.Class;
import models.day.Day;
import models.day.DayDAO;
import models.day.IDayDAO;

import models.timeslot.ITimeslotDAO;
import models.timeslot.Timeslot;
import models.timeslot.TimeslotDAO;
import models.timetable.ITimetableDAO;
import models.timetable.Timetable;
import models.timetable.TimetableDAO;
import models.week.IWeekDAO;
import models.week.Week;
import models.week.WeekDAO;

/**
 *
 * @author Admin
 */
public class ReviewDetailTimetableServlet extends HttpServlet {

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
        HttpSession session = request.getSession();
        String toastType = "", toastMessage = "";
        if (session.getAttribute("toastType") != null) {
            toastType = session.getAttribute("toastType").toString();
            toastMessage = session.getAttribute("toastMessage").toString();
        }
        session.removeAttribute("toastType");
        session.removeAttribute("toastMessage");
        request.setAttribute("toastType", toastType);
        request.setAttribute("toastMessage", toastMessage);

        String classId = request.getParameter("classId");
        String weekId = request.getParameter("weekId");
        String status = request.getParameter("status");

        session.setAttribute("classId", classId);
        session.setAttribute("weekId", weekId);
        session.setAttribute("status", status);

        ITimetableDAO timetableDAO = new TimetableDAO();
        IDayDAO dayDAO = new DayDAO();
        ITimeslotDAO timeslotDAO = new TimeslotDAO();
        IClassDAO classDAO = new ClassDAO();
        IWeekDAO weekDAO = new WeekDAO();

        Week week = weekDAO.getWeek(weekId);
        List<Timetable> timetable = timetableDAO.getTimetableByClassAndWeek(classId, weekId, status);
        List<Timeslot> timeslotList = timeslotDAO.getTimeslotsForTimetable();
        List<Day> dayList = dayDAO.getDayByWeek(weekId);
        Class aClass = classDAO.getClassById(classId);

        request.setAttribute("week", week);
        request.setAttribute("aClass", aClass);
        request.setAttribute("timetable", timetable);
        request.setAttribute("timeslotList", timeslotList);
        request.setAttribute("dayList", dayList);

        request.getRequestDispatcher("reviewDetailTimetable.jsp").forward(request, response);
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

        try {
            ITimetableDAO timetableDAO = new TimetableDAO();
            String action = request.getParameter("action");
            String note = request.getParameter("note");
            HttpSession session = request.getSession();
            String classId = (String) session.getAttribute("classId");
            String weekId = (String) session.getAttribute("weekId");
            String oldStatus = (String) session.getAttribute("status");
            boolean success;

            if (action == null) {
                response.sendRedirect("reviewtimetable");
            } else if (action.equals("approve")) {
                String newStatus = "đã được duyệt";
                success = timetableDAO.updateTimetableStatus(classId, weekId, newStatus, note, oldStatus);
                if (success) {
                    session.setAttribute("toastType", "success");
                    session.setAttribute("toastMessage", "Thời khóa biểu đã được duyệt.");
                } else {
                    session.setAttribute("toastType", "error");
                    session.setAttribute("toastMessage", "Duyệt thất bại");
                }

            } else if (action.equals("reject")) {
                String newStatus = "không được duyệt";
                success = timetableDAO.updateTimetableStatus(classId, weekId, newStatus, note, oldStatus);
                if (success) {
                    session.setAttribute("toastType", "success");
                    session.setAttribute("toastMessage", "Thời khóa biểu không được duyệt");
                } else {
                    session.setAttribute("toastType", "error");
                    session.setAttribute("toastMessage", "Duyệt thất bại");
                }

            }
            response.sendRedirect("reviewtimetable");

            // Xóa các thuộc tính phiên sau khi sử dụng
            session.removeAttribute("classId");
            session.removeAttribute("weekId");
            session.removeAttribute("status");

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error processing timetable approval/rejection", e);
        }
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
