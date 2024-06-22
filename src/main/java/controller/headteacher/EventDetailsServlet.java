package controller.headteacher;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.event.Event;
import models.event.EventDAO;
import models.event.IEventDAO;

import java.io.IOException;

@WebServlet(name = "headteacher/EventDetailsServlet", value = "/headteacher/eventDetail")
public class EventDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        IEventDAO eventDAO = new EventDAO();
        Event event = eventDAO.getEventById(id);
        request.setAttribute("event", event);
        request.getRequestDispatcher("eventDetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}