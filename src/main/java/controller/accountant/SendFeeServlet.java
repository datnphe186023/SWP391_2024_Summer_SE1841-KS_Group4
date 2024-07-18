package controller.accountant;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import models.notification.Notification;
import models.notification.NotificationDAO;
import models.notification.NotificationDetails;
import models.personnel.PersonnelDAO;
import models.user.User;
import models.user.UserDAO;

@WebServlet(name = "SendFeeServlet", urlPatterns = {"/accountant/sendfee"})
public class SendFeeServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SendFeeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SendFeeServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("sendFee.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long hocphi = Long.parseLong(request.getParameter("hocphi").replace(".", ""));

        String baohiemParam = request.getParameter("baohiem");
        int baohiem = baohiemParam != null ? Integer.parseInt(baohiemParam) : 0;

        String csvatchatParam = request.getParameter("csvatchat");
        int csvatchat = csvatchatParam != null ? Integer.parseInt(csvatchatParam) : 0;

        String dongphucParam = request.getParameter("dongphuc");
        int dongphuc = dongphucParam != null ? Integer.parseInt(dongphucParam) : 0;

        // Tính tổng học phí
        long totalFee = hocphi + baohiem + csvatchat + dongphuc;

        // Định dạng các số với dấu chấm
        String formattedHocphi = formatNumberWithDot(hocphi);
        String formattedBaohiem = formatNumberWithDot(baohiem);
        String formattedCsvatchat = formatNumberWithDot(csvatchat);
        String formattedDongphuc = formatNumberWithDot(dongphuc);
        String formattedTotalFee = formatNumberWithDot(totalFee);

        NotificationDAO notifiDAO = new NotificationDAO();
        String id;
        if (notifiDAO.getLatest()!=null){
            id = notifiDAO.generateId(notifiDAO.getLatest().getId());
        } else {
            id = "N000001";
        }
        String heading = "HỌC PHÍ KÌ TIẾP THEO: " + formattedTotalFee + " VNĐ";
        String details = "Học Phí: " + formattedHocphi + " VNĐ, " + "Bảo Hiểm: " + formattedBaohiem + " VNĐ, "
                + "Cơ Sở Vật Chất: " + formattedCsvatchat + " VNĐ, " + "Đồng Phục: " + formattedDongphuc + " VNĐ";
        String create_by = request.getParameter("userid");
        String submitDateStr = request.getParameter("submitDate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date create_at = null;
        try {
            create_at = dateFormat.parse(submitDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<User> listuser = new UserDAO().getUserByRole(5);
        for (User user : listuser) {
            Notification notifi = new Notification(id, heading.trim(), details.trim(), new PersonnelDAO().getPersonnel(create_by), create_at);
            notifiDAO.createNoti(notifi);
            NotificationDetails notifidetails = new NotificationDetails(id, user.getId());
            notifiDAO.createNotiDetails(notifidetails);
        }
        request.setAttribute("toastMessage", "Tạo Thành Công!");
        request.setAttribute("toastType", "success");
        request.getRequestDispatcher("sendFee.jsp").forward(request, response);
    }

    private String formatNumberWithDot(long number) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(number);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
