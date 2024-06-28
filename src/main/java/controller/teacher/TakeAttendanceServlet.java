package controller.teacher;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.classes.ClassDAO;
import models.classes.IClassDAO;
import models.pupil.IPupilDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;
import models.user.User;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "teacher/TakeAttendanceServlet", value = "/teacher/takeattendance")
public class TakeAttendanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IPupilDAO pupilDAO = new PupilDAO();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Date date = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(date);
        List<Pupil> pupils = pupilDAO.getPupilsByTeacherAndTimetable(user.getUsername().toUpperCase(), "2025-10-28");
        IClassDAO classDAO = new ClassDAO();
        String className = classDAO.getClassNameByTeacherAndTimetable(user.getUsername(), "2025-10-28");
        request.setAttribute("className", className);
        request.setAttribute("pupils", pupils);
        request.getRequestDispatcher("takeAttendance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}