package controller.academicstaff;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.classes.Class;
import models.classes.ClassDAO;
import models.classes.IClassDAO;
import models.personnel.IPersonnelDAO;
import models.personnel.Personnel;
import models.personnel.PersonnelDAO;
import models.pupil.IPupilDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;
import models.schoolYear.SchoolYear;
import models.timetable.ITimetableDAO;
import models.timetable.TimetableDAO;
import utils.Helper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "academicstaff/ClassDetailServlet", value = "/academicstaff/classdetail")
public class ClassDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IPupilDAO pupilDAO = new PupilDAO();
        IClassDAO classDAO = new ClassDAO();
        IPersonnelDAO personnelDAO = new PersonnelDAO();
        String classId = request.getParameter("classId");
        HttpSession session = request.getSession();
        session.setAttribute("classId", classId);
        List<Pupil> listPupil = pupilDAO.getListPupilsByClass(null, classId);
        Class classes = classDAO.getClassById(classId);
        List<Pupil> listPupilWithoutClass = pupilDAO.getPupilsWithoutClass(classes.getSchoolYear().getId());

        /// This request for add pupil to class
        request.setAttribute("checkedDate", isSchoolYearInThePast(classes.getSchoolYear()));
        request.setAttribute("listPupilWithoutClass", listPupilWithoutClass);
        request.setAttribute("teacher", classes.getTeacher());
        request.setAttribute("teacherName", classes.getTeacher().getLastName() + " " + classes.getTeacher().getFirstName());
        request.setAttribute("classes", classes);
        /// End request for add pupil to class

        /// This request for move out class for pupil
        request.setAttribute("moveOutClass", classDAO.getClassesByGradeAndSchoolYear(classId, classes.getGrade().getId(), classes.getSchoolYear().getId()));
        /// End request for move out class for pupil
        request.setAttribute("listPupil", listPupil);

        //This request for assign teacher to class, sending a list of available teacher
        request.setAttribute("teachers", personnelDAO.getAvailableTeachers(classDAO.getClassById(classId).getSchoolYear().getId()));
        request.getRequestDispatcher("classDetail.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        if (action.equals("addPupil")) {
            IPupilDAO pupilDAO = new PupilDAO();
            String toastMessage = "";
            String toastType = "";
            boolean addResult = false;
            /// Get all pupils that be selected
            String[] pupilSelected = request.getParameterValues("pupilSelected");
            String classId = request.getParameter("classId");
            if (pupilSelected != null) {
                for (int i = 0; i < pupilSelected.length; i++) {
                    addResult = pupilDAO.addPupilToClass(pupilSelected[i], classId);
                }
            }
            if (addResult) {
                toastMessage = "Thêm học sinh vào lớp thành công";
                toastType = "success";
            } else {
                toastMessage = "Thêm học sinh vào lớp thất bại";
                toastType = "error";
            }
            session.setAttribute("toastMessage", toastMessage);
            session.setAttribute("toastType", toastType);
            response.sendRedirect("classdetail?classId=" + classId);
            
        } else if (action.equals("moveOutClassForPupil")) {
            IClassDAO classDAO = new ClassDAO();
            String oldClassId = request.getParameter("classId");
            String pupilId = request.getParameter("pupil");
            String newClassId = request.getParameter("classes");
            if (!pupilId.isBlank() && !newClassId.isBlank()) {
                if (classDAO.moveOutClassForPupil(oldClassId, newClassId, pupilId)) {
                    session.setAttribute("toastType", "success");
                    session.setAttribute("toastMessage", "Thao tác thành công");
                } else {
                    session.setAttribute("toastType", "error");
                    session.setAttribute("toastMessage", "Thao tác thất bại");
                }
            } else {
                session.setAttribute("toastType", "error");
                session.setAttribute("toastMessage", "Thao tác thất bại");
            }
            response.sendRedirect("classdetail?classId=" + oldClassId);
        } else if (action.equals("assignTeacher")) {
            String teacherId = request.getParameter("teacher");
            String classId = request.getParameter("classId");
            IClassDAO classDAO = new ClassDAO();
            String result = classDAO.assignTeacherToClass(teacherId, classId);
            ITimetableDAO timetableDAO = new TimetableDAO();
            timetableDAO.updateTeacherOfTimetable(classId, teacherId);
            if (result.equals("success")) {
                session.setAttribute("toastType", "success");
                session.setAttribute("toastMessage", "Thao tác thành công");
            } else {
                session.setAttribute("toastType", "error");
                session.setAttribute("toastMessage", result);
            }
            response.sendRedirect("classdetail?classId=" + classId);
        } else if (action.equals("assignSubTeacher")) {
            String substituteTeacher = request.getParameter("substituteTeacher");
            String classId = request.getParameter("classId");
            String dayId= request.getParameter("day");
            ITimetableDAO timetableDAO = new TimetableDAO();
            String result = timetableDAO.updateTimetableOfClass(substituteTeacher, classId, dayId);
            if (result.equals("success")) {
                session.setAttribute("toastType", "success");
                session.setAttribute("toastMessage", "Thao tác thành công");
            } else {
                session.setAttribute("toastType", "error");
                session.setAttribute("toastMessage", result);
            }
            response.sendRedirect("classdetail?classId=" + classId);
        }
    }
    
    private boolean isSchoolYearInThePast(SchoolYear schoolYear) {
        return schoolYear.getEndDate().before(new Date());
    }
}
