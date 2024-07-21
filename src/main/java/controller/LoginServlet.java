package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import models.personnel.IPersonnelDAO;
import models.personnel.Personnel;
import models.personnel.PersonnelDAO;
import models.pupil.IPupilDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;
import models.user.IUserDAO;
import models.user.User;
import models.user.UserDAO;


public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("error") != null) {
            String error = (String) session.getAttribute("error");
            request.setAttribute("error", error);
            session.removeAttribute("error");
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get data from login form
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();

        // Check data login
        IUserDAO userDAO = new UserDAO();

        User user = userDAO.getUserByUsernamePassword(username, password);

        if (user != null && user.getIsDisabled() != 1) {

            // Lấy thông tin personnel tương ứng với user id
            IPersonnelDAO personnelDAO = new PersonnelDAO();
            Personnel personnel = personnelDAO.getPersonnelByUserId(user.getId());

            if (personnel != null) {
                // Lưu thông tin personnel vào session
                HttpSession session = request.getSession();
                session.setAttribute("personnel", personnel);
            }
            
            
            // Lấy thông tin parent 
            IPupilDAO pupilDAO = new PupilDAO();
            Pupil pupil = pupilDAO.getPupilByUserId(user.getId());
            
            if(pupil != null) {
                // Lưu thông tin pupil vào session
                HttpSession session = request.getSession();
                session.setAttribute("pupil", pupil);

            }
            
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            // Login success
            switch (user.getRoleId()) {
                // role id = 0 : admin
                case 0:
                    response.sendRedirect("admin/dashboard");
                    break;
                case 1:
                    // role id = 1 : Head Teacher
                    response.sendRedirect("headteacher/dashboard");
                    break;
                case 2:
                    // role id = 2 : academic staff
                    response.sendRedirect("academicstaff/dashboard");
                    break;
                case 3:
                    // role id = 3 : Accountant
                    response.sendRedirect("accountant/dashboard");
                    break;
                case 4:
                    // role id = 4: Teacher 
                    response.sendRedirect("teacher/dashboard");
                    break;
                case 5:
                    // role id = 5 : Parent
                    response.sendRedirect("parent/dashboard");
                    break;
                default:
                    request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu");
                    request.removeAttribute("error");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    break;
            }
        } else {
            // Nếu thông tin đăng nhập không chính xác, hiển thị thông báo lỗi trên trang đăng nhập
            HttpSession session = request.getSession();
            session.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu !");
            response.sendRedirect("login");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
