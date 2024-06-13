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
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Enumeration;
import java.util.List;

import models.classes.ClassDAO;
import models.classes.IClassDAO;
import models.day.IDayDAO;
import models.grade.Grade;
import models.grade.GradeDAO;
import models.grade.IGradeDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.subject.ISubjectDAO;
import models.subject.Subject;
import models.subject.SubjectDAO;
import models.timeslot.ITimeslotDAO;
import models.timeslot.Timeslot;
import models.timeslot.TimeslotDAO;
import models.timetable.ITimetableDAO;
import models.week.IWeekDAO;
import models.week.Week;
import models.week.WeekDAO;
import models.classes.Class;
import models.day.Day;
import models.day.DayDAO;
import models.schoolYear.ISchoolYearDAO;
import utils.Helper;

/**
 *
 * @author Admin
 */
public class CreateTimetableServlet extends HttpServlet {

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
        
        
        SchoolYearDAO yearDAO = new SchoolYearDAO();
        ITimeslotDAO timeslotDAO = new TimeslotDAO();
        IWeekDAO weekDAO = new WeekDAO();
        IGradeDAO gradeDAO = new GradeDAO();
        IClassDAO classDAO = new ClassDAO();
        ISubjectDAO subjectDAO = new SubjectDAO();
        IDayDAO dayDAO = new DayDAO();

        String selectedGradeId = request.getParameter("gradeId");
        String weekId = request.getParameter("weekId");
        // get list grade
        List<Grade> listGrade = gradeDAO.getAll();
        // get list week from now
        List<Week> listWeek = weekDAO.getWeeksFromNow();
        // get start date and end date
        Week dateWeek = weekDAO.getWeek(weekId);
        
        // get timeslot
        List<Timeslot> listTimeslot = timeslotDAO.getAllTimeslots();
        // get school year latest
        SchoolYear newYear = yearDAO.getLatest();
        // get list subject by grade id
        List<Subject> subList = subjectDAO.getSubjectsByGradeId(selectedGradeId);
        // get list class by grade id
        List<Class> classList = classDAO.getClassByGradeId(selectedGradeId);
        // get list day by week 
        List<Day> dayList = dayDAO.getDayByWeek(weekId);
        
        request.setAttribute("dateWeek", dateWeek);
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
        IWeekDAO weekDAO = new WeekDAO();
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        try {
            String action = request.getParameter("action");
            if (action == null) {
                response.sendRedirect("timetable");
            } else if (action.equals("create-week")) {
                String startDateRaw = request.getParameter("startDate").trim();
                String endDateRaw = request.getParameter("endDate").trim();
                String weekId = request.getParameter("weekID").trim();
                Week week = new Week();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = dateFormat.parse(startDateRaw);
                Date endDate = dateFormat.parse(endDateRaw);
                week.setId(weekId);
                week.setStartDate(startDate);
                week.setEndDate(endDate);
                week.setSchoolYear(schoolYearDAO.getLatest());
                HttpSession session = request.getSession();
                String result = weekDAO.createTimetableWeek(week);
                if (result.equals("success")) {
                    session.setAttribute("toastType", "success");
                    session.setAttribute("toastMessage", "Tạo mới thành công");
                } else {
                    session.setAttribute("toastType", "error");
                    session.setAttribute("toastMessage", result);
                }
                response.sendRedirect("timetable");

            } else if(action.equals("create-timetable")){
                String timetableId = request.getParameter("timetableId");
                String classId = request.getParameter("classId");
                
            }
        } catch (Exception e) {
            e.printStackTrace();
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
