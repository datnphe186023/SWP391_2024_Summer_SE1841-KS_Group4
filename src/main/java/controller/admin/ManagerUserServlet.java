/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.user.IUserDAO;
import models.user.User;
import models.user.UserDAO;

/**
 *
 * @author TuyenCute
 */
public class ManagerUserServlet extends HttpServlet {

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
            out.println("<title>Servlet ManagerUserServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerUserServlet at " + request.getContextPath() + "</h1>");
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
        doPost(request, response);
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
        Map<Integer, String> roleMap = new HashMap<>();
        Map<Byte, String> roleDis = new HashMap<>();
        roleMap.put(0, "NHÂN VIÊN IT");
        roleMap.put(1, "HIỆU TRƯỞNG");
        roleMap.put(2, "GIÁO VỤ");
        roleMap.put(3, "KẾ TOÁN");
        roleMap.put(4, "GIÁO VIÊN");
        roleMap.put(5, "PHỤ HUYNH");

        roleDis.put((byte) 0, "HOẠT ĐỘNG");
        roleDis.put((byte) 1, "KHÔNG HOẠT ĐỘNG");
        IUserDAO userDAO = new UserDAO();
        List<User> list;
        list = userDAO.getListUser();
        HttpSession session = request.getSession();
        String error = (String) session.getAttribute("error");
        String success = (String) session.getAttribute("success");
        String successedit = (String) session.getAttribute("successedit");
        String erroredit = (String) session.getAttribute("erroredit");
        if (error != null) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Đặt Lại Mật Khẩu Không Thành Công");
            session.removeAttribute("error");
        } else if (success != null) {
            request.setAttribute("toastType", "success");
            request.setAttribute("toastMessage", "Đặt Lại Mật Khẩu Thành Công");
            session.removeAttribute("success");
        }
        if (successedit != null) {
            request.setAttribute("toastType", "success");
            request.setAttribute("toastMessage", "Cập Nhật Thông Tin Thành Công");
            session.removeAttribute("successedit");
        } else if (erroredit != null) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Cập Nhật Thông Tin Không Thành Công, Email Đã Được Đăng Ký");
            session.removeAttribute("erroredit");
        }
        request.setAttribute("list", list);
        request.setAttribute("roleMap", roleMap);
        request.setAttribute("roleDis", roleDis);
        request.getRequestDispatcher("../admin/managerUser.jsp").forward(request, response);
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
