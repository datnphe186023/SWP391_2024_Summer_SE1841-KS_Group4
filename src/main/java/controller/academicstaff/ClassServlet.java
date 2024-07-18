package controller.academicstaff;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.classes.Class;
import models.classes.ClassDAO;
import models.classes.IClassDAO;
import models.grade.GradeDAO;
import models.grade.IGradeDAO;
import models.personnel.IPersonnelDAO;
import models.personnel.Personnel;
import models.personnel.PersonnelDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.user.User;
import utils.Helper;

import java.io.IOException;
import java.util.List;

    @WebServlet(name = "academicstaff/ClassServlet", value = "/academicstaff/class")
public class ClassServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IClassDAO classDAO = new ClassDAO();
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        try{
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
            String schoolYearId = (String) session.getAttribute("schoolYearId");
            session.removeAttribute("schoolYearId");
            if (schoolYearId == null ) {
                schoolYearId = request.getParameter("schoolYearId");
            }
            if (schoolYearId == null) {
                if (schoolYearDAO.getLatest()!=null){
                    SchoolYear latestSchoolYear = schoolYearDAO.getLatest();
                    schoolYearId = latestSchoolYear.getId();
                }
            }
            List<Class> classes = classDAO.getBySchoolYear(schoolYearId);
            request.setAttribute("classes", classes);
            List<SchoolYear> schoolYears = schoolYearDAO.getAll();
            request.setAttribute("schoolYears", schoolYears);
            request.setAttribute("selectedSchoolYear", schoolYearDAO.getSchoolYear(schoolYearId));
            IGradeDAO gradeDAO = new GradeDAO();
            request.setAttribute("grades", gradeDAO.getAll());
            IPersonnelDAO personnelDAO = new PersonnelDAO();
            request.setAttribute("teachers", personnelDAO.getAvailableTeachers(schoolYearId));
            request.getRequestDispatcher("class.jsp").forward(request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null){
            //create new class
            if (action.equals("create")) {
                String name = request.getParameter("name");
                name = Helper.formatName(name);
                String gradeId = request.getParameter("grade");
                String schoolYearId = request.getParameter("schoolYear");
                String teacherId = request.getParameter("teacher");
                Class c = new Class();
                c.setName(name);
                IGradeDAO gradeDAO = new GradeDAO();
                c.setGrade(gradeDAO.getGrade(gradeId));
                ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
                c.setSchoolYear(schoolYearDAO.getSchoolYear(schoolYearId));
                IPersonnelDAO personnelDAO = new PersonnelDAO();
                if (!teacherId.isEmpty()) {
                    Personnel teacher = personnelDAO.getPersonnel(teacherId);
                    c.setTeacher(teacher);
                }
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                c.setCreatedBy(personnelDAO.getPersonnelByUserId(user.getId()));
                IClassDAO classDAO = new ClassDAO();
                String result = classDAO.createNewClass(c);
                //return result of creation to user
                if (result.equals("success")) {
                    session.setAttribute("toastType", "success");
                    session.setAttribute("toastMessage", "Thao tác thành công");
                } else {
                    session.setAttribute("toastType", "error");
                    session.setAttribute("toastMessage", result);
                }
                session.setAttribute("schoolYearId", schoolYearId);
                response.sendRedirect("class");
            }
        }
    }
}