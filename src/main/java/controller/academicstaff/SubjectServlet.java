package controller.academicstaff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        String status = request.getParameter("status");
        List<Subject> subjectList = subjectDAO.getAll();
        if(status!=null){
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
        }
        request.setAttribute("listAllSubject",subjectList);
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
            String name = request.getParameter("name").trim();
            String grade = request.getParameter("grade");
            String description = request.getParameter("description").trim();
            String regexSubjectName =  "^["+Helper.VIETNAMESE_CHARACTERS+"A-Za-z0-9\\s,;:.!?-]{1,125}$";
            String regexDescription =  "^["+Helper.VIETNAMESE_CHARACTERS+"A-Za-z0-9\\s,;:.!?-]{1,1000}$";
            if(!Helper.formatString(name).matches(regexSubjectName) || !Helper.formatString(description).matches(regexDescription)|| grade.isBlank() ){
                   if(!Helper.formatString(name).matches(regexSubjectName)){
                       toastMessage = "Tạo thất bại! Tên môn học trống hoặc quá 125 kí tự ! ";
                   }else if ( !Helper.formatString(description).matches(regexDescription)){
                       toastMessage = "Tạo thất bại! Chi tiết trống hoặc đã quá 1000 kí tự ! ";
                   }else if(grade.isBlank()){
                       toastMessage = "Tạo thất bại! Vui Lòng chọn khối !";
                   }
                toastType = "error";
                request.setAttribute("listAllSubject", subjectDAO.getAll());
                request.setAttribute("listGrade", gradeDAO.getAll());
                session.setAttribute("toastMessage", toastMessage);
                session.setAttribute("toastType", toastType);
                request.getRequestDispatcher("subject.jsp").forward(request, response);
            }else {
                Subject subject = new Subject(null, Helper.formatName(name), gradeDAO.getGrade(grade), Helper.formatString(description), "đang chờ xử lý");
                if (subjectDAO.createSubject(subject).equals("success")) {
                    toastMessage = "Tạo thành công";
                    toastType = "success";
                    session.setAttribute("toastMessage", toastMessage);
                    session.setAttribute("toastType", toastType);
                    response.sendRedirect("subject");
                } else {
                    toastMessage = subjectDAO.createSubject(subject);
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
        } else if (action.equals("edit")) {
            String subjectId = request.getParameter("subjectId");
            String name = request.getParameter("name");
            String grade = request.getParameter("grade");
            String description = request.getParameter("description");
            Subject subject = new Subject(subjectId,Helper.formatName(name),gradeDAO.getGrade(grade),Helper.formatString(description),"đang chờ xử lý");
            if(Helper.formatString(description).length()>1000 ||Helper.formatString(description).length() <= 0 ){
                toastMessage = "Chỉnh sửa thất bại! Chi tiết trống hoặc đã quá 1000 kí tự";
                toastType = "error";
                session.setAttribute("toastMessage",toastMessage);
                session.setAttribute("toastType",toastType);
                session.setAttribute("subject", subject);
                response.sendRedirect("subject");
            }else if(Helper.formatString(name).length() >=125 || Helper.formatString(name).length()<=0 || !name.matches("^[a-zA-Z0-9" + "ĐđaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴ"
                    + "\\s]{1,50}$")){
                toastMessage = "Chỉnh sửa thất bại!Tên môn học trống hoặc quá 125 kí tự";
                toastType = "error";
                session.setAttribute("toastMessage", toastMessage);
                session.setAttribute("toastType", toastType);
                session.setAttribute("subject", subject);
                response.sendRedirect("subject");
            } else if (subjectDAO.editSubject(subject).equals("success")) {
                toastMessage = "Chỉnh sửa thành công";
                toastType = "success";
                session.setAttribute("toastMessage", toastMessage);
                session.setAttribute("toastType", toastType);
                session.setAttribute("subject", subject);
                response.sendRedirect("subject");
            } else {
                toastMessage = subjectDAO.editSubject(subject);
                toastType = "error";
                session.setAttribute("toastMessage", toastMessage);
                session.setAttribute("toastType", toastType);
                session.setAttribute("subject", subject);
                response.sendRedirect("subject");
            }
        }
    }
}