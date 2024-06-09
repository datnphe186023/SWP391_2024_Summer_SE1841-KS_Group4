/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.academicstaff;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import models.grade.Grade;
import models.grade.GradeDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.subject.Subject;
import models.subject.SubjectDAO;
import models.timeslot.Timeslot;
import models.timeslot.TimeslotDAO;
import models.week.Week;
import models.week.WeekDAO;

/**
 *
 * @author Admin
 */
public class CreateTimetableServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            out.println("<title>Servlet CreateTimetableServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateTimetableServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        SchoolYearDAO yearDAO = new SchoolYearDAO();
        TimeslotDAO timeslotDAO = new TimeslotDAO();
        WeekDAO weekDAO = new WeekDAO();
        GradeDAO gradeDAO = new GradeDAO();
        
        List<Grade> listGrade = gradeDAO.getAll();
        List<Week> listWeek = weekDAO.getWeeksFromNow();
        List<Timeslot> listTimeslot = timeslotDAO.getAllTimeslots();
        String selectedGradeId = request.getParameter("gradeId");
        SubjectDAO subDAO = new SubjectDAO();
        List<Subject> subList = subDAO.getSubjectsByGradeId(selectedGradeId);
        request.setAttribute("subList", subList);
        
        
        SchoolYear newYear = yearDAO.getLatest();
        request.setAttribute("listTimeslot", listTimeslot);
        request.setAttribute("newYear", newYear);
        request.setAttribute("listWeek", listWeek);
        request.setAttribute("listGrade", listGrade);
        request.setAttribute("selectedGradeId", selectedGradeId);
        
        request.getRequestDispatcher("createTimetable.jsp").forward(request, response);

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
        
        
        request.getRequestDispatcher("createTimetable.jsp").forward(request, response);
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
