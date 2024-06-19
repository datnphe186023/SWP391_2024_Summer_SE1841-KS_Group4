/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.accountant;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import models.notification.Notification;
import models.notification.NotificationDAO;
import models.personnel.PersonnelDAO;

/**
 *
 * @author TuyenCute
 */
@WebServlet(name = "SendFeeServlet", urlPatterns = {"/accountant/sendfee"})
public class SendFeeServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int hocphi = Integer.parseInt(request.getParameter("hocphi"));

        String baohiemParam = request.getParameter("baohiem");
        int baohiem = baohiemParam != null ? Integer.parseInt(baohiemParam) : 0;

        String csvatchatParam = request.getParameter("csvatchat");
        int csvatchat = csvatchatParam != null ? Integer.parseInt(csvatchatParam) : 0;

        String dongphucParam = request.getParameter("dongphuc");
        int dongphuc = dongphucParam != null ? Integer.parseInt(dongphucParam) : 0;

// Tính tổng học phí
        int totalFee = hocphi + baohiem + csvatchat + dongphuc;
        NotificationDAO notifiDAO = new NotificationDAO();
        String id = "";
        String heading = "HỌC PHÍ KÌ TIẾP THEO : " + totalFee + "VNĐ";
        String details = "Học Phí : " + hocphi + "VNĐ" + ", " + "Bảo Hiểm : " + baohiem + "VNĐ" + ", " + "Cơ Sở Vật Chất : "
                + csvatchat + "VNĐ" + ", " + "Đồng Phục : " + dongphuc + "VNĐ";
        String create_by = request.getParameter("userid");
        String submitDateStr = request.getParameter("submitDate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng ngày bạn mong muốn
        Date create_at = null;
        try {
            create_at = dateFormat.parse(submitDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Notification notifi = new Notification(id, heading.trim(), details.trim(), new PersonnelDAO().getPersonnel(create_by), create_at);
        try {
            notifiDAO.createNoti(notifi);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("listnotification").forward(request, response);
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
