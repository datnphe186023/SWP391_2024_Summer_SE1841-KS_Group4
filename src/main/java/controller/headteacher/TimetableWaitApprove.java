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
        //classid , date_id(startdate-enddate) , createby , status , teacherid
        ITimetableDAO timetableDAO = new TimetableDAO();
        List<TimetableDTO> listTimetable = timetableDAO.getListTimetableByStatus("chưa xét duyệt");
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
