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
import models.personnel.IPersonnelDAO;
import models.personnel.PersonnelDAO;
import models.pupil.IPupilDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;
import models.user.IUserDAO;
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
        IPersonnelDAO personnelDAO = new PersonnelDAO();
        IUserDAO userDAO = new UserDAO();
        IPupilDAO pupilDAO = new PupilDAO();
        // Lấy thông tin pupil từ session
        HttpSession session = request.getSession();
        Pupil pupil = (Pupil) session.getAttribute("pupil");
        User user = (User) session.getAttribute("user");

        // Lấy thông tin cần update từ request
        String firstGuardianName = request.getParameter("first_guardian_name").trim();
        String firstGuardianPhoneNumber = request.getParameter("firstGuardianPhoneNumber").trim();
        String secondGuardianName = request.getParameter("second_guardian_name").trim();
        String secondGuardianPhoneNumber = request.getParameter("secondGuardianPhoneNumber").trim();
        String email = request.getParameter("email").trim();
        String address = request.getParameter("address").trim();
        String note = request.getParameter("note").trim();

        // Kiểm tra tính duy nhất của email và số điện thoại
        boolean emailExists = userDAO.checkEmailExists(email) && !email.equals(user.getEmail());
        boolean phoneNumberMotherExists = (personnelDAO.checkPhoneNumberExists(firstGuardianPhoneNumber) || pupilDAO.checkFirstGuardianPhoneNumberExists(firstGuardianPhoneNumber) || pupilDAO.checkSecondGuardianPhoneNumberExists(firstGuardianPhoneNumber))
                && (!firstGuardianPhoneNumber.equals(pupil.getfirstGuardianPhoneNumber()));
        boolean phoneNumberSecondGuardianExists = (personnelDAO.checkPhoneNumberExists(secondGuardianPhoneNumber) || pupilDAO.checkFirstGuardianPhoneNumberExists(secondGuardianPhoneNumber) || pupilDAO.checkSecondGuardianPhoneNumberExists(firstGuardianPhoneNumber))
                && (!secondGuardianPhoneNumber.equals(pupil.getsecondGuardianPhoneNumber()));
        if (emailExists && phoneNumberMotherExists && phoneNumberSecondGuardianExists) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Email và số điện thoại của cả người giám hộ thứ nhất và người giám hộ thứ hai đã tồn tại.");
        } else if (emailExists && phoneNumberMotherExists) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Email và số điện thoại của người giám hộ thứ nhất đã tồn tại.");
        } else if (emailExists && phoneNumberSecondGuardianExists) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Email và số điện thoại của người giám hộ thứ hai đã tồn tại.");
        } else if (emailExists) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Email đã tồn tại.");
        } else if (phoneNumberMotherExists && phoneNumberSecondGuardianExists) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Số điện thoại của cả người giám hộ thứ nhất và người giám hộ thứ hai đã tồn tại.");
        } else if (phoneNumberMotherExists) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Số điện thoại của người giám hộ thứ nhất đã tồn tại.");
        } else if (phoneNumberSecondGuardianExists) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Số điện thoại của người giám hộ thứ hai đã tồn tại.");
        } else if(firstGuardianPhoneNumber.equals(secondGuardianPhoneNumber)){
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Số điện thoại của người giám hộ thứ hai và người giám hộ thứ nhất không được trùng nhau");
        }
        else {

            // Cập nhật thông tin của pupil
            pupil.setfirstGuardianName(firstGuardianName);
            pupil.setfirstGuardianPhoneNumber(firstGuardianPhoneNumber);
            pupil.setsecondGuardianName(secondGuardianName);
            pupil.setsecondGuardianPhoneNumber(secondGuardianPhoneNumber);
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
