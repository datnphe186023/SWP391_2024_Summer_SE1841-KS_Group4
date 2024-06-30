package controller.teacher;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.classes.ClassDAO;
import models.classes.IClassDAO;
import models.day.DayDAO;
import models.day.IDayDAO;
import models.pupil.*;
import models.user.User;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@WebServlet(name = "teacher/TakeAttendanceServlet", value = "/teacher/takeattendance")
public class TakeAttendanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //getting toast message sent from doPost after take attendance
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

        IPupilDAO pupilDAO = new PupilDAO();
        User user = (User) session.getAttribute("user");
        Date date = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(date);
        List<Pupil> pupils = pupilDAO.getPupilsByTeacherAndTimetable(user.getUsername().toUpperCase(), dateString);
        IClassDAO classDAO = new ClassDAO();
        String className = classDAO.getClassNameByTeacherAndTimetable(user.getUsername(), dateString);
        request.setAttribute("date", dateString);
        request.setAttribute("className", className);
        request.setAttribute("pupils", pupils);
        request.getRequestDispatcher("takeAttendance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Enumeration<String> params = request.getParameterNames();
        PupilAttendance pupilAttendance = new PupilAttendance();
        IDayDAO dayDAO = new DayDAO();
        IPupilDAO pupilDAO = new PupilDAO();
        IPupilAttendanceDAO pupilAttendanceDAO = new PupilAttendanceDAO();
        Date currentDate = new Date();
        // Define the date format
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // Convert the Date to a String
        String dateString = formatter.format(currentDate);
        String result = "";
        while (params.hasMoreElements()) {
            String name = params.nextElement();
            if (name.startsWith("attendance")) {
                String pupilId = name.substring(10);
                String status = request.getParameter(name);
                String note = request.getParameter("note" + pupilId);
                pupilAttendance.setDay(dayDAO.getDayByDate(dateString));
                pupilAttendance.setPupil(pupilDAO.getPupilsById(pupilId));
                pupilAttendance.setStatus(status);
                pupilAttendance.setNote(note);
                result = pupilAttendanceDAO.addAttendance(pupilAttendance);
            }
        }
        //sending result
        HttpSession session = request.getSession();
        if (result.equals("success")) {
            session.setAttribute("toastType", "success");
            session.setAttribute("toastMessage", "Gửi đơn thành công");
        } else {
            session.setAttribute("toastType", "error");
            session.setAttribute("toastMessage", result);
        }
        response.sendRedirect("takeattendance");
    }
}