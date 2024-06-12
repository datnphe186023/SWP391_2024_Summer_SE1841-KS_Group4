package controller.academicstaff;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.grade.GradeDAO;
import models.grade.IGradeDAO;
import models.subject.ISubjectDAO;
import models.subject.Subject;
import models.subject.SubjectDAO;
import utils.Helper;


@WebServlet(name = "academicstaff/SubjectServlet", value = "/academicstaff/subject")
public class SubjectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ISubjectDAO subjectDAO = new SubjectDAO();
        IGradeDAO gradeDAO = new GradeDAO();
        request.setAttribute("listAllSubject",subjectDAO.getAll());
        request.setAttribute("listGrade",gradeDAO.getAll());
        request.getRequestDispatcher("subject.jsp").forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        IGradeDAO gradeDAO = new GradeDAO();
        ISubjectDAO subjectDAO = new SubjectDAO();
        HttpSession session = request.getSession();
        String toastMessage = "";
        String toastType = "error";
        if (action.equals("create")) {
            String name = request.getParameter("name");
            String grade = request.getParameter("grade");
            String description = request.getParameter("description");
            Subject subject = new Subject(null,Helper.formatName(name),gradeDAO.getGrade(grade),Helper.formatString(description),"đang chờ phê duyệt");
            if(Helper.formatString(description).length()>1000){
                 toastMessage = "Tạo thất bại! Đã quá 1000 kí tự";
                 toastType = "error";
                request.setAttribute("listAllSubject",subjectDAO.getAll());
                request.setAttribute("listGrade",gradeDAO.getAll());
                 session.setAttribute("toastMessage",toastMessage);
                session.setAttribute("toastType",toastType);
                request.getRequestDispatcher("subject.jsp").forward(request,response);
            }else if(subjectDAO.checkSubjectExist(name,grade)){
                toastMessage = "Tạo thất bại! Đã tồn tại môn học này";
                toastType = "error";
                request.setAttribute("listAllSubject", subjectDAO.getAll());
                request.setAttribute("listGrade", gradeDAO.getAll());
                session.setAttribute("toastMessage", toastMessage);
                session.setAttribute("toastType", toastType);
                request.getRequestDispatcher("subject.jsp").forward(request, response);
            } else if (subjectDAO.createSubject(subject)) {
                toastMessage = "Tạo thành công";
                toastType = "success";
                session.setAttribute("toastMessage", toastMessage);
                session.setAttribute("toastType", toastType);
                response.sendRedirect("subject");
            } else {
                toastMessage = "Tạo thật bại";
                toastType = "error";
                session.setAttribute("toastMessage", toastMessage);
                session.setAttribute("toastType", toastType);
                request.setAttribute("listAllSubject", subjectDAO.getAll());
                request.setAttribute("listGrade", gradeDAO.getAll());
                session.setAttribute("toastMessage", toastMessage);
                session.setAttribute("toastType", toastType);
                request.getRequestDispatcher("subject.jsp").forward(request, response);
            }

        }
    }
}