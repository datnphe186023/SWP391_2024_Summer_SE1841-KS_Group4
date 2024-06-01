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
import java.util.ArrayList;
import java.util.List;

    @WebServlet(name = "academicstaff/ClassServlet", value = "/academicstaff/class")
public class ClassServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClassDAO classDAO = new ClassDAO();
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
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
            if (schoolYearId == null ) {
                schoolYearId = request.getParameter("schoolYearId");
            }
            if (schoolYearId == null) {
                SchoolYear latestSchoolYear = schoolYearDAO.getLatest();
                schoolYearId = latestSchoolYear.getId();
            }
            List<Class> classes = classDAO.getBySchoolYear(schoolYearId);
            request.setAttribute("classes", classes);
            List<SchoolYear> schoolYears = schoolYearDAO.getAll();
            request.setAttribute("schoolYears", schoolYears);
            request.setAttribute("selectedSchoolYear", schoolYearDAO.getSchoolYear(schoolYearId));
            GradeDAO gradeDAO = new GradeDAO();
            request.setAttribute("grades", gradeDAO.getAll());
            PersonnelDAO personnelDAO = new PersonnelDAO();
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
                String result = classDAO.createNewClass(c);
                if (result.equals("success")) {
                    session.setAttribute("toastType", "success");
                    session.setAttribute("toastMessage", "Tạo mới thành công");
                } else {
                    session.setAttribute("toastType", "error");
                    session.setAttribute("toastMessage", result);
                }
                session.setAttribute("schoolYearId", schoolYearId);
                response.sendRedirect("class");
            } else if (action.equals("search")) {
                String name = request.getParameter("name");
                String schoolYearId = request.getParameter("schoolYearId");
                ClassDAO classDAO = new ClassDAO();
                List<Class> classes = classDAO.getByName(formatString(name), schoolYearId);
                SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
                request.setAttribute("schoolYears", schoolYearDAO.getAll());
                request.setAttribute("selectedSchoolYear", schoolYearDAO.getSchoolYear(schoolYearId));
                if (classes.size() > 0) {
                    request.setAttribute("classes", classes);
                } else {
                    request.setAttribute("error", "Không có kết quả tương ứng");
                }
                request.getRequestDispatcher("class.jsp").forward(request, response);
            }
        }
    }
        private String formatString(String search){
            StringBuilder result = new StringBuilder();
            String[] searchArray = search.split("\\s+");
            for(int i=0;i<searchArray.length;i++){
                result.append(searchArray[i]).append(" ");
            }
            return result.toString().trim();
        }
}