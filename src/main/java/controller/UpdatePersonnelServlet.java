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
import models.personnel.IPersonnelDAO;
import models.personnel.Personnel;
import models.personnel.PersonnelDAO;
import models.pupil.IPupilDAO;
import models.pupil.PupilDAO;
import models.user.IUserDAO;
import models.user.User;
import models.user.UserDAO;

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
        // Cập nhật thông tin của pupil,user trong cơ sở dữ liệu
        IPersonnelDAO personnelDAO = new PersonnelDAO();
        IUserDAO userDAO = new UserDAO();
        IPupilDAO pupilDAO = new PupilDAO();
        // Lấy thông tin pupil từ session
        HttpSession session = request.getSession();
        Personnel person = (Personnel) session.getAttribute("personnel");
        User user = (User) session.getAttribute("user");
        // Lấy thông tin cần update từ request
        String firstName = request.getParameter("first_name").trim();
        String lastName = request.getParameter("last_name").trim();
        String genderStr = request.getParameter("gender").trim();
        String address = request.getParameter("address").trim();
        String email = request.getParameter("email").trim();
        String phoneNumber = request.getParameter("phone_number").trim();

        // Kiểm tra tính duy nhất của email và số điện thoại
        boolean emailExists = userDAO.checkEmailExists(email) && !email.equals(user.getEmail());
        boolean phoneNumberExists = (personnelDAO.checkPhoneNumberExists(phoneNumber)
                || pupilDAO.checkFirstGuardianPhoneNumberExists(phoneNumber)
                || pupilDAO.checkSecondGuardianPhoneNumberExists(phoneNumber))
                && !phoneNumber.equals(person.getPhoneNumber());

        if (emailExists && phoneNumberExists) {
            session.setAttribute("toastType", "error");
            session.setAttribute("toastMessage", "Email và số điện thoại đã tồn tại.");
        } else if (emailExists) {
            session.setAttribute("toastType", "error");
            session.setAttribute("toastMessage", "Email đã tồn tại.");
        } else if (phoneNumberExists) {
            session.setAttribute("toastType", "error");
            session.setAttribute("toastMessage", "Số điện thoại đã tồn tại.");
        } else {
            // Cập nhật thông tin của pupil
            person.setFirstName(firstName);
            person.setLastName(lastName);
            // Validate and update gender
            boolean gender = Boolean.parseBoolean(genderStr);
            person.setGender(gender);
            person.setAddress(address);
            person.setEmail(email);
            person.setPhoneNumber(phoneNumber);
            user.setEmail(email);
            boolean successUser = userDAO.updateUserById(user);
            boolean successPerson = personnelDAO.updatePerson(person);
            if (successPerson && successUser) {
                session.setAttribute("toastType", "success");
                session.setAttribute("toastMessage", "Đã cập nhật thành công !");
            } else {
                session.setAttribute("toastType", "error");
                session.setAttribute("toastMessage", "Đã cập nhật thất bại !");
            }
        }

        switch (person.getRoleId()) {
            // role id = 0 : admin.
            case 0:
                response.sendRedirect("admin/information");
                break;
            case 1:
                // role id = 1 : Head Teacher.
                response.sendRedirect("headteacher/information");
                break;
            case 2:
                // role id = 2 : academic staff.
                response.sendRedirect("academicstaff/information");
                break;
            case 3:
                // role id = 3 : Accountant.
                response.sendRedirect("accountant/information");
                break;
            case 4:
                // role id = 4: Teacher .
                response.sendRedirect("teacher/information");
                break;
            default:
                throw new AssertionError();
        }
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
