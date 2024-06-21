/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.academicstaff;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import models.personnel.IPersonnelDAO;
import models.personnel.PersonnelDAO;
import models.schoolYear.ISchoolYearDAO;
import models.timetable.Timetable;
import models.timetable.TimetableDAO;
import models.user.User;

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

        ISchoolYearDAO yearDAO = new SchoolYearDAO();
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
        
        // get start date and end date
        Week dateWeek = weekDAO.getWeek(weekId);

        // get timeslot
        List<Timeslot> listTimeslot = timeslotDAO.getTimeslotsForTimetable();
        // get school year latest
        SchoolYear schoolYear = yearDAO.getLatest();
        // get list week from now
        List<Week> listWeek = weekDAO.getWeeksFromNowUntilEndOfSchoolYear(schoolYear.getId());
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
        request.setAttribute("newYear", schoolYear);
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

        try {
            String action = request.getParameter("action");
            if (action == null) {
                response.sendRedirect("timetable");
            } else if (action.equals("create-timetable")) {
                HttpSession session = request.getSession();
                ITimetableDAO timetableDAO = new TimetableDAO();
                IPersonnelDAO personnelDAO = new PersonnelDAO();
                ITimeslotDAO timeslotDAO = new TimeslotDAO();
                ISubjectDAO subjectDAO = new SubjectDAO();
                IDayDAO dayDAO = new DayDAO();
                IClassDAO classDAO = new ClassDAO();
                Timetable timetable = new Timetable();

                User user = (User) session.getAttribute("user");
                // Lấy các tham số chính từ form
                String classId = request.getParameter("classId");

                timetable.setaClass(classDAO.getClassById(classId));
                // Định nghĩa các tham số khác
                timetable.setCreatedBy(personnelDAO.getPersonnelByUserId(user.getId()));
                String status = "đang chờ xử lý";
                timetable.setStatus(status);
                String note = "";
                timetable.setNote(note);
                timetable.setTeacher(personnelDAO.getTeacherByClass(classId));

                // Thu thập tất cả các `dayId` từ tham số đầu vào
                Enumeration<String> parameterNames = request.getParameterNames();
                Set<String> dayIds = new HashSet<>();
                while (parameterNames.hasMoreElements()) {
                    String paramName = parameterNames.nextElement();
                    if (paramName.startsWith("timeslotId_")) {
                        String[] parts = paramName.split("_");
                        String dayId = parts[1];
                        dayIds.add(dayId);
                    }
                }

                // Kiểm tra thời khóa biểu tồn tại cho tất cả `dayId`
                for (String dayId : dayIds) {
                    if (timetableDAO.existsTimetableForClassInCurrentWeek(classId, dayId)) {
                        session.setAttribute("toastType", "error");
                        session.setAttribute("toastMessage", "Thời khóa biểu của lớp này đã được tạo!");
                        response.sendRedirect("timetable");
                        return; // Dừng lại nếu thời khóa biểu đã tồn tại
                    } 
                }

                // Tiến hành tạo thời khóa biểu nếu không có mục nào tồn tại
                parameterNames = request.getParameterNames();
                int entryCounter = 1;
                boolean entryCreated = false; // Cờ để kiểm tra nếu bất kỳ mục nào được tạo
                while (parameterNames.hasMoreElements()) {
                    String paramName = parameterNames.nextElement();
                    if (paramName.startsWith("timeslotId_")) {
                        String timeslotIdValue = request.getParameter(paramName);
                        if (!timeslotIdValue.isEmpty()) {
                            String[] parts = paramName.split("_");
                            String dayId = parts[1];
                            String timeslotId = parts[2];
                            String subjectId = timeslotIdValue; // ID môn học được chọn
                            String timetableId = timetableDAO.generateTimetableId();
                            timetable.setId(timetableId);
                            timetable.setDay(dayDAO.getDayByID(dayId));
                            timetable.setTimeslot(timeslotDAO.getTimeslotById(timeslotId));
                            timetable.setSubject(subjectDAO.getSubjectBySubjectId(subjectId));
                            // Chèn mục thời khóa biểu vào cơ sở dữ liệu
                            timetableDAO.createTimetable(timetable);
                            entryCreated = true; // Một mục đã được tạo
                        }
                    }
                }

                if (entryCreated) {
                    session.setAttribute("toastType", "success");
                    session.setAttribute("toastMessage", "Thời khóa biểu đã được tạo thành công.");
                } else {
                    session.setAttribute("toastType", "error");
                    session.setAttribute("toastMessage", "Không có mục thời khóa biểu nào được tạo.");
                }
                response.sendRedirect("timetable");

            } 

        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("error.jsp").forward(request, response);
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
