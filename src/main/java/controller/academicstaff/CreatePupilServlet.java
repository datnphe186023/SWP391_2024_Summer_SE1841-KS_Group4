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

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "academicstaff/CreatePupilServlet", value = "/academicstaff/createpupil")
public class CreatePupilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PupilDAO pupilDAO = new PupilDAO();
        String newPupilId = pupilDAO.generateId(pupilDAO.getLatest().getId());
        request.setAttribute("newPupilId", newPupilId);

        request.getRequestDispatcher("createPupil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        PersonnelDAO personnelDAO = new PersonnelDAO();
        PupilDAO pupilDAO = new PupilDAO();
        User user = null;
        boolean gender = true;
        String message = "";
        if (session.getAttribute("user") != null) {
            String avatar = request.getParameter("avatar");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String fatherName = request.getParameter("fatherName");
            String motherName = request.getParameter("motherName");
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
            String fatherPhone = request.getParameter("fatherPhone");
            String motherPhone = request.getParameter("motherPhone");
            String height = request.getParameter("height");
            String weight = request.getParameter("weight");
            String status = "đang chờ xử lý";
            user = (User) session.getAttribute("user");
            Personnel createdBy = personnelDAO.getPersonnelByUserId(user.getId());

            Pupil pupil = new Pupil("id", null, firstName, lastName, address, email, status, birthday, gender,
                    motherName, motherPhone, avatar, fatherName, fatherPhone, createdBy,
                    note);
//            if (pupilDAO.createPupil(pupil)) {
//                message = "Tạo thành công";
//            } else {
//                message = "Tạo thật bại";
//            }
//            request.setAttribute("message", message);
            pupilDAO.createPupil(pupil);
            response.sendRedirect("createpupil");

        }
    }
}