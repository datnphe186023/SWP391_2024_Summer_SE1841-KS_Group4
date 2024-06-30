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
        String regexHeading = "^["+Helper.VIETNAMESE_CHARACTERS+"A-Za-z0-9\\s,;:.!?-]{1,200}$";
        String regexDetail = "^["+Helper.VIETNAMESE_CHARACTERS+"A-Za-z0-9\\s,;:.!?-]{1,10000}$";
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            if (!Helper.formatString(heading).matches(regexHeading) || !Helper.formatString(details).matches(regexDetail) || listReceiver == null){
                if (!Helper.formatString(heading).matches(regexHeading)) {
                    toastMessage = "Tạo thất bại ! Tiêu đề không được quá 200 kí tự hoặc bỏ trống";
                } else if (!Helper.formatString(details).matches(regexDetail)) {
                    toastMessage = "Tạo thất bại ! Nội dung không quá 10000 kí tự hoặc bỏ trống !";
                } else if (listReceiver == null) {
                    toastMessage = "Tạo thất bại! Vui lòng chọn đối tượng gửi!";
                }
                toastType = "error";
                request.setAttribute("toastMessage", toastMessage);
                request.setAttribute("toastType", toastType);
                request.getRequestDispatcher("sendEvent.jsp").forward(request, response);
            }else {
                String newId = eventDAO.generateId(eventDAO.getLastest().getId());
                Event event = new Event(newId, personnelDAO.getPersonnel(user.getUsername()), Helper.formatString(heading), Helper.formatString(details));
                String result = eventDAO.createEvent(event);
                if (result.equals("success")) {
                    for (String s : listReceiver) {
                        try {
                            int receiveId = Integer.parseInt(s);
                            if (!eventDAO.sendEvent(newId, receiveId)) {
                                toastMessage = "Tạo thất bại!";
                                toastType = "error";
                                request.setAttribute("toastMessage", toastMessage);
                                request.setAttribute("toastType", toastType);
                                request.getRequestDispatcher("sendEvent.jsp").forward(request, response);
                            }
                        } catch (NullPointerException e) {
                            toastMessage = "Tạo thất bại! Vui lòng chọn đối tượng gửi!";
                            toastType = "error";
                            request.setAttribute("toastMessage", toastMessage);
                            request.setAttribute("toastType", toastType);
                            request.getRequestDispatcher("sendEvent.jsp").forward(request, response);
                        }
                    }
                    toastMessage = "Gửi thành công!";
                    toastType = "success";
                    session.setAttribute("toastMessage", toastMessage);
                    session.setAttribute("toastType", toastType);
                    response.sendRedirect("listevent");
                } else {
                    toastMessage = result;
                    toastType = "error";
                    request.setAttribute("toastMessage", toastMessage);
                    request.setAttribute("toastType", toastType);
                    request.getRequestDispatcher("sendEvent.jsp").forward(request, response);
                }
            }
        }

        }
    }

