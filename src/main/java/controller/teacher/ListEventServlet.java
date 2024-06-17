package controller.teacher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.event.EventDAO;
import models.event.IEventDAO;

import java.io.IOException;


@WebServlet(name = "teacher/ListEventServlet", value = "/teacher/listevent")
public class ListEventServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IEventDAO eventDAO = new EventDAO();
        request.setAttribute("listEvents",eventDAO.getEventByRole(4));
        request.getRequestDispatcher("listEvent.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}