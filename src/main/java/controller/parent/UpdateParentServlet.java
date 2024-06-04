/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.parent;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.personnel.PersonnelDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;
import models.user.User;
import models.user.UserDAO;

/**
 *
 * @author Admin
 */
public class UpdateParentServlet extends HttpServlet {

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
        PersonnelDAO dao = new PersonnelDAO();
        UserDAO userDAO = new UserDAO();
        PupilDAO pupilDAO = new PupilDAO();
        // Lấy thông tin pupil từ session
        HttpSession session = request.getSession();
        Pupil pupil = (Pupil) session.getAttribute("pupil");
        User user = (User) session.getAttribute("user");

        // Lấy thông tin cần update từ request
        String motherName = request.getParameter("mother_name").trim();
        String motherPhone = request.getParameter("mother_phone").trim();
        String fatherName = request.getParameter("father_name").trim();
        String fatherPhone = request.getParameter("father_phone").trim();
        String email = request.getParameter("email").trim();
        String address = request.getParameter("address").trim();
        String note = request.getParameter("note").trim();

        // Kiểm tra tính duy nhất của email và số điện thoại
        boolean emailExists = userDAO.checkEmailExists(email) && !email.equals(user.getEmail());
        boolean phoneNumberMotherExists = dao.checkPhoneNumberExists(motherPhone) || pupilDAO.checkParentPhoneNumberExists(motherPhone);
        boolean phoneNumberFatherExists = dao.checkPhoneNumberExists(fatherPhone) || pupilDAO.checkParentPhoneNumberExists(fatherPhone);
        if (emailExists && phoneNumberMotherExists && phoneNumberFatherExists) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Email và số điện thoại của cả mẹ và bố đã tồn tại.");
        } else if (emailExists && phoneNumberMotherExists) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Email và số điện thoại của mẹ đã tồn tại.");
        } else if (emailExists && phoneNumberFatherExists) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Email và số điện thoại của bố đã tồn tại.");
        } else if (emailExists) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Email đã tồn tại.");
        } else if (phoneNumberMotherExists && phoneNumberFatherExists) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Số điện thoại của cả mẹ và bố đã tồn tại.");
        } else if (phoneNumberMotherExists) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Số điện thoại của mẹ đã tồn tại.");
        } else if (phoneNumberFatherExists) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Số điện thoại của bố đã tồn tại.");
        } else if(motherPhone.equals(fatherPhone)){
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Số điện thoại của bố và mẹ không được trùng nhau");
        }
        else {

            // Cập nhật thông tin của pupil
            pupil.setMotherName(motherName);
            pupil.setMotherPhoneNumber(motherPhone);
            pupil.setFatherName(fatherName);
            pupil.setFatherPhoneNumber(fatherPhone);
            pupil.setEmail(email);
            pupil.setAddress(address);
            pupil.setParentSpecialNote(note);
            user.setEmail(email);
            // Cập nhật thông tin của pupil trong cơ sở dữ liệu
            boolean successUser = userDAO.updateUserById(user);
            boolean success = pupilDAO.updateParent(pupil);

            if (success && successUser) {
                request.setAttribute("toastType", "success");
                request.setAttribute("toastMessage", "Đã cập nhật thành công !");
            } else {
                request.setAttribute("toastType", "error");
                request.setAttribute("toastMessage", "Đã cập nhật thất bại !");
            }
        }
        request.getRequestDispatcher("informationParent.jsp").forward(request, response);
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
