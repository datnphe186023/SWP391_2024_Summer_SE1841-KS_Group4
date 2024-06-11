package controller.headteacher;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.grade.GradeDAO;
import models.subject.SubjectDAO;


@WebServlet(name = "headteacher/ListSubjectServlet", value = "/headteacher/listsubject")
public class ListSubjectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SubjectDAO subjectDAO = new SubjectDAO();
        GradeDAO gradeDAO = new GradeDAO();
        request.setAttribute("listAllSubject",subjectDAO.getAll());
        request.setAttribute("listGrade",gradeDAO.getAll());
        request.getRequestDispatcher("listSubject.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}