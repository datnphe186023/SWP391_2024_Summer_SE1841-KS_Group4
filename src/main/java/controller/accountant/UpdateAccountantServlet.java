/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.accountant;

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
public class UpdateAccountantServlet extends HttpServlet {
  

    /** 
     * Handles the HTTP <code>POST</code> method.
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
            request.setAttribute("noti", "Đã cập nhật thành công !");
            session.setAttribute("personnel", person);
            session.removeAttribute("noti");
            request.getRequestDispatcher("information_accountant.jsp").forward(request, response);
        } else {
            request.setAttribute("fail", "Cập nhật thất bại!");
            session.removeAttribute("fail");
            request.getRequestDispatcher("information_accountant.jsp").forward(request, response);
        }
        
        
    }


    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
