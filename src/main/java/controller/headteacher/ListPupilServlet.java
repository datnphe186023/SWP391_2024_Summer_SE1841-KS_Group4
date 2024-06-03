package controller.headteacher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.classes.Class;
import models.classes.ClassDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;
import models.schoolYear.SchoolYearDAO;

import java.io.IOException;
import java.util.List;


@WebServlet(name = "headteacher/ListPupilServlet", value = "/headteacher/listpupil")
public class ListPupilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClassDAO classDAO = new ClassDAO();
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        PupilDAO pupilDAO = new PupilDAO();


        String classesSelect = request.getParameter("classes");
        String schoolYearSelect = request.getParameter("schoolYear");
        if (schoolYearSelect==null ){
            schoolYearSelect = schoolYearDAO.getLatest().getId();
        }

        List<Pupil> listPupils = pupilDAO.getPupilByClassAndSchoolYear(classesSelect,schoolYearSelect);
        List<Class> listClass = classDAO.getBySchoolYear(schoolYearSelect);

        request.setAttribute("numOfPendingPupils",pupilDAO.getPupilByStatus("đang chờ xử lý").size());
        request.setAttribute("classSelect",classesSelect);
        request.setAttribute("listPupil",listPupils);
        request.setAttribute("schoolYearSelect",schoolYearSelect);
        request.setAttribute("listClass",listClass);
        request.setAttribute("listSchoolYear",schoolYearDAO.getAll());
        request.getRequestDispatcher("listPupil.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}