package controller.academicstaff;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.classes.Class;
import models.classes.ClassDAO;
import models.grade.Grade;
import models.grade.GradeDAO;
import models.personnel.Personnel;
import models.personnel.PersonnelDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.user.User;

import java.io.IOException;
import java.util.List;

    @WebServlet(name = "academicstaff/ClassServlet", value = "/academicstaff/class")
public class ClassServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClassDAO classDAO = new ClassDAO();
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        try{
            String schoolYearId = request.getParameter("schoolYearId");
            if (schoolYearId == null) {
                SchoolYear latestSchoolYear = schoolYearDAO.getLatest();
                schoolYearId = latestSchoolYear.getId();
            }
            List<Class> classes = classDAO.getBySchoolYear(schoolYearId);
            request.setAttribute("classes", classes);
            List<SchoolYear> schoolYears = schoolYearDAO.getAll();
            request.setAttribute("schoolYears", schoolYears);
            request.setAttribute("selectedSchoolYearId", schoolYearId);
            GradeDAO gradeDAO = new GradeDAO();
            request.setAttribute("grades", gradeDAO.getAll());
            PersonnelDAO personnelDAO = new PersonnelDAO();
            request.setAttribute("teachers", personnelDAO.getPersonnelByRole(4));
            request.getRequestDispatcher("class.jsp").forward(request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null){
            if (action.equals("create")) {
                String name = request.getParameter("name");
                String gradeId = request.getParameter("grade");
                String schoolYearId = request.getParameter("schoolYear");
                String teacherId = request.getParameter("teacher");
                Class c = new Class();
                c.setName(name);
                GradeDAO gradeDAO = new GradeDAO();
                c.setGrade(gradeDAO.getGrade(gradeId));
                SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
                c.setSchoolYear(schoolYearDAO.getSchoolYear(schoolYearId));
                PersonnelDAO personnelDAO = new PersonnelDAO();
                Personnel teacher = personnelDAO.getPersonnel(teacherId);
                c.setTeacher(teacher);
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                c.setCreatedBy(personnelDAO.getPersonnelByUserId(user.getId()));
                ClassDAO classDAO = new ClassDAO();
                classDAO.createNewClass(c);
                response.sendRedirect("class");
            }
        }
    }
}