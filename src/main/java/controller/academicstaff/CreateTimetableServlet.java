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
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.classes.ClassDAO;
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
import models.classes.Class;
import models.day.Day;
import models.day.DayDAO;
import models.timetable.TimetableDAO;

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
        ClassDAO classDAO = new ClassDAO();
        SubjectDAO subDAO = new SubjectDAO();
        DayDAO dayDAO = new DayDAO();
        
        String selectedGradeId = request.getParameter("gradeId");
        String weekId = request.getParameter("weekId");
        
        List<Grade> listGrade = gradeDAO.getAll();
        List<Week> listWeek = weekDAO.getWeeksFromNow();
        List<Timeslot> listTimeslot = timeslotDAO.getAllTimeslots();
        SchoolYear newYear = yearDAO.getLatest();
        List<Subject> subList = subDAO.getSubjectsByGradeId(selectedGradeId);
        List<Class> classList = classDAO.getClassByGradeId(selectedGradeId);
        List<Day> dayList = dayDAO.getDayByWeek(weekId);
        
        request.setAttribute("dayList", dayList);
        request.setAttribute("subList", subList);
        request.setAttribute("classList", classList);
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
        String classId = request.getParameter("classId");
        String note = request.getParameter("note");
        TimetableDAO timetableDAO = new TimetableDAO();
        
        // Lấy tất cả các tham số từ request
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            
            // Kiểm tra và xử lý các tham số liên quan đến thời khóa biểu
            if (paramValue != null && !paramValue.isEmpty()) {
                if (paramName.matches("^\\d{2}:\\d{2}_\\w+$")) {
                    String[] parts = paramName.split("_");
                    String timeslotId = parts[0];
                    String dateId = parts[1];
                    String subjectId = paramValue;
                    
                    try {
                        // Chèn dữ liệu vào bảng Timetables
                        timetableDAO.insertTimetable(classId, timeslotId, dateId, subjectId, "created_by_value", "status_value", note, "teacher_id_value");
                    } catch (SQLException ex) {
                        Logger.getLogger(CreateTimetableServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
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
