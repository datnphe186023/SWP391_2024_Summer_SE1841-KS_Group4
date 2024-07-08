package controller.headteacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.grade.GradeDAO;
import models.grade.IGradeDAO;
import models.subject.ISubjectDAO;
import models.subject.Subject;
import models.subject.SubjectDAO;


@WebServlet(name = "headteacher/ListSubjectServlet", value = "/headteacher/listsubject")
public class ListSubjectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ISubjectDAO subjectDAO = new SubjectDAO();
        request.setAttribute("listSubject",subjectDAO.getAll());
        request.getRequestDispatcher("listSubject.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ISubjectDAO subjectDAO = new SubjectDAO();
        String status = request.getParameter("status");
        List<Subject> subjectList = new ArrayList<>();
        switch (status){
            case "all" : subjectList = subjectDAO.getAll();
                        break;
            case "pending": subjectList = subjectDAO.getSubjectsByStatus("Đang chờ xử lý");
                        break;
            case "approve": subjectList = subjectDAO.getSubjectsByStatus("Đã được duyệt");
                break;
            case "decline": subjectList = subjectDAO.getSubjectsByStatus("Không được duyệt");
                break;
            default:
                break;
        }
        request.setAttribute("listSubject",subjectList);
        request.getRequestDispatcher("listSubject.jsp").forward(request,response);

    }
}