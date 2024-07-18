package controller.headteacher;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import models.timetable.ITimetableDAO;
import models.timetable.TimetableDAO;
import models.timetable.TimetableDTO;

public class TimetableWaitApprove extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        //classid , date_id(startdate-enddate) , createby , status , teacherid
        ITimetableDAO timetableDAO = new TimetableDAO();
        List<TimetableDTO> listTimetable = timetableDAO.getListTimetableByStatus("đang chờ xử lý");
        request.setAttribute("listTimetable", listTimetable);
        request.getRequestDispatcher("timetableWaitApprove.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
