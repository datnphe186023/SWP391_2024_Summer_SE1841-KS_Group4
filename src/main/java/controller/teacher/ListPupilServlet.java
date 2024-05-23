package controller.teacher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.personnel.Personnel;
import models.personnel.PersonnelDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "teacher/ListPupilServlet", value = "/teacher/listpupil")
public class ListPupilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PupilDAO pupilDAO = new PupilDAO();
        PersonnelDAO personnelDAO = new PersonnelDAO();
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();

        HttpSession session = request.getSession();

        User user = null;
        //// variable to display the year that being checked
        String yearSelected = "";

        List<Pupil> listPupil = new ArrayList<>();
        List<SchoolYear> listSchoolYear = schoolYearDAO.getAll();
        if (session.getAttribute("user") != null) {
            user = (User) session.getAttribute("user");
            Personnel teacher = personnelDAO.getPersonnelByUserId(user.getId());

            yearSelected = schoolYearDAO.getAll().get(schoolYearDAO.getAll().size()-1).getId() ;
            String schoolYear = request.getParameter("schoolYear");
            if (schoolYear != null) {
                yearSelected = schoolYear;
            }
            listPupil = pupilDAO.getListPupilOfTeacherBySchoolYear(schoolYear, teacher.getId());
        }
        request.setAttribute("checkYear", yearSelected);
        request.setAttribute("listPupil", listPupil);
        request.setAttribute("listSchoolYear", listSchoolYear);
        request.getRequestDispatcher("teacher_listpupil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}