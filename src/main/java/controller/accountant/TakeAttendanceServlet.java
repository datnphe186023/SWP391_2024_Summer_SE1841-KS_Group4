package controller.accountant;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.day.DayDAO;
import models.day.IDayDAO;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import models.personnel.IPersonnelAttendanceDAO;
import models.personnel.IPersonnelDAO;
import models.personnel.Personnel;
import models.personnel.PersonnelAttendance;
import models.personnel.PersonnelAttendanceDAO;
import models.personnel.PersonnelDAO;

@WebServlet(name = "accountant/TakeAttendanceServlet", value = "/accountant/takeattendance")
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

        IPersonnelDAO personnelDAO = new PersonnelDAO();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(date);
        List<Personnel> personnel = personnelDAO.getAllPersonnels();
        request.setAttribute("date", dateString);
        request.setAttribute("personnel", personnel);
        request.getRequestDispatcher("takeAttendance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Enumeration<String> params = request.getParameterNames();
        PersonnelAttendance personnelAttendance = new PersonnelAttendance();
        IDayDAO dayDAO = new DayDAO();
        IPersonnelDAO personnelDAO = new PersonnelDAO();
        IPersonnelAttendanceDAO personnelAttendanceDAO = new PersonnelAttendanceDAO();
        Date currentDate = new Date();
        // Define the date format
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // Convert the Date to a String
        String dateString = formatter.format(currentDate);
        String result = "";
        HttpSession session = request.getSession();

        // Check if the day exists before processing attendance
        if (dayDAO.getDayByDate(dateString) == null) {
            session.setAttribute("toastType", "error");
            session.setAttribute("toastMessage", "Đang trong kì nghỉ");
            response.sendRedirect("takeattendance");
            return; 
        }

        while (params.hasMoreElements()) {
            String name = params.nextElement();
            if (name.startsWith("attendance")) {
                String personnelId = name.substring(10);
                String status = request.getParameter(name);
                String note = request.getParameter("note" + personnelId);
                personnelAttendance.setDay(dayDAO.getDayByDate(dateString));
                personnelAttendance.setPersonnel(personnelDAO.getPersonnel(personnelId));
                personnelAttendance.setStatus(status);
                personnelAttendance.setNote(note);
                result = personnelAttendanceDAO.addAttendance(personnelAttendance);
            }
        }

        //sending result
        if (result.equals("success")) {
            session.setAttribute("toastType", "success");
            session.setAttribute("toastMessage", "Thao tác thành công");
        } else {
            session.setAttribute("toastType", "error");
            session.setAttribute("toastMessage", result);
        }
        response.sendRedirect("takeattendance");
    }

}
