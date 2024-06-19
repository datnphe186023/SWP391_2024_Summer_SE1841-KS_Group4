package controller.headteacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.event.Event;
import models.event.EventDAO;
import models.event.IEventDAO;
import models.personnel.IPersonnelDAO;
import models.personnel.PersonnelDAO;
import models.user.User;
import utils.Helper;


@WebServlet(name = "headteacher/SendEventServlet", value = "/headteacher/sendevent")
public class SendEventServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("sendEvent.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IPersonnelDAO personnelDAO = new PersonnelDAO();
        IEventDAO eventDAO = new EventDAO();
        HttpSession session = request.getSession();
        String toastMessage = "";
        String toastType = "error";
        String heading = request.getParameter("heading").trim();
        String details = request.getParameter("details").trim();
        String []listReceiver = request.getParameterValues("receiver");
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            if (!(heading.isBlank() || heading.length() > 50) && !details.isBlank() && listReceiver!=null) {
                String newId = eventDAO.generateId(eventDAO.getLastest().getId());
                Event event = new Event(newId, personnelDAO.getPersonnel(user.getUsername()), heading, details);
                String result = eventDAO.createEvent(event);
                 if(result.equals("success")){
                    for(int i=0;i<listReceiver.length;i++){
                        try {
                            int receiveId = Integer.parseInt(listReceiver[i]);
                            if(!eventDAO.sendEvent(newId,receiveId)){
                                toastMessage = "Tạo thất bại!";
                                toastType = "error";
                                request.setAttribute("toastMessage", toastMessage);
                                request.setAttribute("toastType", toastType);
                                request.getRequestDispatcher("sendEvent.jsp").forward(request, response);
                            }
                        }catch (NullPointerException e){
                            toastMessage = "Tạo thất bại! Vui lòng chọn đối tượng gửi!";
                            toastType = "error";
                            request.setAttribute("toastMessage", toastMessage);
                            request.setAttribute("toastType", toastType);
                            request.getRequestDispatcher("sendEvent.jsp").forward(request, response);
                        }
                    }
                     toastMessage ="Gửi thành công!";
                     toastType = "success";
                     request.setAttribute("toastMessage", toastMessage);
                     request.setAttribute("toastType", toastType);
                     response.sendRedirect("listevent");
                 }
                 else{
                     toastMessage = result;
                     toastType = "error";
                     request.setAttribute("toastMessage", toastMessage);
                     request.setAttribute("toastType", toastType);
                     request.getRequestDispatcher("sendEvent.jsp").forward(request, response);
                 }
            }
            else {
                toastMessage = "Tạo thất bại! Số kí tự không hợp lệ !";
                toastType = "error";
                request.setAttribute("toastMessage", toastMessage);
                request.setAttribute("toastType", toastType);
                request.getRequestDispatcher("sendEvent.jsp").forward(request, response);
            }
        }

        }
    }

