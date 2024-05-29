/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.personnel.Personnel;
import models.personnel.PersonnelDAO;

/**
 *
 * @author Admin
 */
public class UpdatePersonnelServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdatePersonnelServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdatePersonnelServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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

        // Lấy thông tin pupil từ session
        HttpSession session = request.getSession();
        Personnel person = (Personnel) session.getAttribute("personnel");

        // Lấy thông tin cần update từ request
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String genderStr = request.getParameter("gender");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phone_number");

        // Cập nhật thông tin của pupil
        person.setFirstName(firstName);
        person.setLastName(lastName);
        // Validate and update gender
        boolean gender = Boolean.parseBoolean(genderStr);
        person.setGender(gender);
        person.setAddress(address);
        person.setEmail(email);
        person.setPhoneNumber(phoneNumber);

        // Cập nhật thông tin của pupil trong cơ sở dữ liệu
        PersonnelDAO dao = new PersonnelDAO();
        boolean success = dao.updatePerson(person);
        if (success) {
            request.setAttribute("toastType", "success");
            request.setAttribute("toastMessage", "Đã cập nhật thành công !");
            session.setAttribute("personnel", person);
        } else {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Đã cập nhật thất bại !");
        }
        switch (person.getRoleId()) {
            // role id = 0 : admin.
            case 0:
                request.getRequestDispatcher("admin/information_admin.jsp").forward(request, response);
                break;
            case 1:
                // role id = 1 : Head Teacher.
                request.getRequestDispatcher("headteacher/information_headteacher.jsp").forward(request, response);
                break;
            case 2:
                // role id = 2 : academic staff.
                request.getRequestDispatcher("academicstaff/information_staff.jsp").forward(request, response);
                break;
            case 3:
                // role id = 3 : Accountant.
                request.getRequestDispatcher("accountant/information_accountant.jsp").forward(request, response);
                break;
            case 4:
                // role id = 4: Teacher .
                request.getRequestDispatcher("teacher/information_teacher.jsp").forward(request, response);
                break;
            default:
                throw new AssertionError();
        }
        request.removeAttribute("toastMessage");

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
