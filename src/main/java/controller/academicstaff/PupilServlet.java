package controller.academicstaff;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.personnel.Personnel;
import models.personnel.PersonnelDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;
import models.user.User;
import utils.Helper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@WebServlet(name = "academicstaff/ListPupilServlet", value = "/academicstaff/pupil")
public class PupilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PupilDAO pupilDAO = new PupilDAO();
        List<Pupil> listPupil = pupilDAO.getAllPupils();
        String newPupilId = pupilDAO.generateId(pupilDAO.getLatest().getId());
        request.setAttribute("newPupilId", newPupilId);
        request.setAttribute("listPupil", listPupil);
        request.getRequestDispatcher("pupil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("create")) {
            HttpSession session = request.getSession();
            PersonnelDAO personnelDAO = new PersonnelDAO();
            PupilDAO pupilDAO = new PupilDAO();
            Helper helper = new Helper();
            User user = null;
            boolean gender = true;
            String toastMessage = "";
            String toastType = "error";
            if (session.getAttribute("user") != null) {
                String avatar = request.getParameter("avatar");
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String secondGuardianName = request.getParameter("secondGuardianName");
                String firstGuardianName = request.getParameter("firstGuardianName");
                String birth = request.getParameter("birth");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date birthday = null;
                try {
                    birthday = formatter.parse(birth);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                String genderRaw = request.getParameter("gender");
                if (genderRaw.equals("0")) {
                    gender = false;
                }
                String email = request.getParameter("email");
                String note = request.getParameter("note");
                String address = request.getParameter("address");
                String secondGuardianPhoneNumber = request.getParameter("secondGuardianPhoneNumber");
                String firstGuardianPhoneNumber = request.getParameter("firstGuardianPhoneNumber");
                String status = "đang chờ xử lý";
                user = (User) session.getAttribute("user");
                Personnel createdBy = personnelDAO.getPersonnelByUserId(user.getId());

                Pupil pupil = new Pupil(null, null, helper.formatName(firstName), helper.formatName(lastName), address, email, status, birthday, gender,
                        helper.formatName(firstGuardianName), firstGuardianPhoneNumber, avatar, helper.formatName(secondGuardianName), secondGuardianPhoneNumber, createdBy,
                        note);
                ////   Stage for create pupil
                if (pupilDAO.createPupil(pupil)) {
                    toastMessage = "Xác nhận thành công";
                    toastType = "success";
                    session.setAttribute("toastMessage", toastMessage);
                    session.setAttribute("toastType", toastType);
                    response.sendRedirect("pupil");
                } else {
                    toastMessage = "Tạo thật bại";
                    toastType = "error";
                    session.setAttribute("toastMessage", toastMessage);
                    session.setAttribute("toastType", toastType);
                    List<Pupil> listPupil = pupilDAO.getAllPupils();
                    request.setAttribute("listPupil", listPupil);
                    String newPupilId = pupilDAO.generateId(pupilDAO.getLatest().getId());
                    request.setAttribute("newPupilId", newPupilId);
                    request.getRequestDispatcher("pupil.jsp").forward(request,response);
                }




            }
        }
    }
}