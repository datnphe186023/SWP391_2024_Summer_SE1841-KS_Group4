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
        String receiver = Helper.formatString(request.getParameter("receiver").trim());
        String details = request.getParameter("details").trim();
        if (session.getAttribute("user") != null) {
            boolean result =false;
            User user = (User) session.getAttribute("user");
            if (!(heading.isBlank() || heading.length() > 50) && !receiver.isBlank() && !details.isBlank()) {
                if(eventDAO.checkExistEvent(heading)){
                    toastMessage = "Tạo thất bại! Sự kiện đã được tạo!!";
                    toastType = "error";
                    request.setAttribute("toastMessage", toastMessage);
                    request.setAttribute("toastType", toastType);
                    request.getRequestDispatcher("sendEvent.jsp").forward(request, response);
                }else{
                    String newId = eventDAO.generateId(eventDAO.getLastest().getId());
                    Event event = new Event(newId, personnelDAO.getPersonnel(user.getUsername()), heading, details);
                    List<Integer> listReceiver = new ArrayList<>();
                    if (eventDAO.createEvent(event)) {
                        switch (receiver) {
                            case "all":
                                listReceiver.add(2);
                                listReceiver.add(3);
                                listReceiver.add(4);
                                listReceiver.add(5);
                                break;
                            case "teacher":
                                listReceiver.add(4);
                                break;
                            case "accountant":
                                listReceiver.add(3);
                                break;
                            case "staff":
                                listReceiver.add(2);
                                break;
                            case "parent":
                                listReceiver.add(4);
                                listReceiver.add(5);
                                break;
                            case "school":
                                listReceiver.add(2);
                                listReceiver.add(3);
                                listReceiver.add(4);
                                break;
                        }
                        for(int i=0;i<listReceiver.size();i++){
                            if(eventDAO.sendEvent(newId,listReceiver.get(i))){
                                result = true;
                            }else{
                                toastMessage = "Tạo thất bại!";
                                toastType = "error";
                                request.setAttribute("toastMessage", toastMessage);
                                request.setAttribute("toastType", toastType);
                                request.getRequestDispatcher("sendEvent.jsp").forward(request, response);
                            }
                        }
                        if(result){
                            toastMessage = "Gửi thành công";
                            toastType = "success";
                            session.setAttribute("toastMessage", toastMessage);
                            session.setAttribute("toastType", toastType);
                            response.sendRedirect("listevent");
                        }
                    }
                }

            }
            else {
                toastMessage = "Tạo thất bại! Số kí tự không hợp lệ!!";
                toastType = "error";
                request.setAttribute("toastMessage", toastMessage);
                request.setAttribute("toastType", toastType);
                request.getRequestDispatcher("sendEvent.jsp").forward(request, response);
            }
        }
    }
}