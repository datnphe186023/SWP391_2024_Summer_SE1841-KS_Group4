/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.academicstaff;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import models.timetable.ITimetableDAO;
import models.timetable.TimetableDAO;
import models.timetable.TimetableDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name = "/academicstaff/ListTimetable", urlPatterns = {"/academicstaff/listtimetable"})
public class ListTimetable extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //classid , date_id(startdate-enddate) , createby , status , teacherid
        ITimetableDAO timetableDAO = new TimetableDAO();
        List<TimetableDTO> listTimetable = timetableDAO.getUniqueClassTimetablesWithWeeks();
        request.setAttribute("listTimetable", listTimetable);
        request.getRequestDispatcher("listTimetable.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
