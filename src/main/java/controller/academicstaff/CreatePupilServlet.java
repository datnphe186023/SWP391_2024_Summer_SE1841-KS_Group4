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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "academicstaff/CreatePupilServlet", value = "/academicstaff/createpupil")
public class CreatePupilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        PersonnelDAO personnelDAO = new PersonnelDAO();
        PupilDAO pupilDAO = new PupilDAO();
        Helper helper = new Helper();
        User user = null;
        boolean gender = true;
        String toastMessage = "";
        String toastType="error";
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
            String status = "đang chờ xử lý";
            user = (User) session.getAttribute("user");
            Personnel createdBy = personnelDAO.getPersonnelByUserId(user.getId());

            Pupil pupil = new Pupil(null, null, helper.formatName(firstName), helper.formatName(lastName), address, email, status, birthday, gender,
                    helper.formatName(motherName), motherPhone, avatar, helper.formatName(fatherName), fatherPhone, createdBy,
                    note);
///          Check format of phone and birthday information
//            Must have age greater than 3 year olds
                ////   Stage for create pupil
                if (pupilDAO.createPupil(pupil)) {
                    toastMessage = "Xác nhận thành công";
                    toastType="success";
                } else {
                    toastMessage = "Tạo thật bại";
                    toastType="error";
                }
                session.setAttribute("toastMessage", toastMessage);
                session.setAttribute("toastType",toastType);
                request.getRequestDispatcher("pupil.jsp").forward(request,response);
        }
    }


    public static int calculateAge(Date birthDate, Date currentDate) {
        // Formating date, year, month
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");

        // Parse day, year, month to integer
        int birthYear = Integer.parseInt(yearFormat.format(birthDate));
        int birthMonth = Integer.parseInt(monthFormat.format(birthDate));
        int birthDay = Integer.parseInt(dayFormat.format(birthDate));


        int currentYear = Integer.parseInt(yearFormat.format(currentDate));
        int currentMonth = Integer.parseInt(monthFormat.format(currentDate));
        int currentDay = Integer.parseInt(dayFormat.format(currentDate));


        int age = currentYear - birthYear;


        if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
            age--;
        }

        return age;
    }

    public static boolean checkAge(String birthDateString) {
        try {
            // Formating date
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setLenient(false); // Đảm bảo ngày nhập vào là hợp lệ
            Date birthDate = formatter.parse(birthDateString);


            Date currentDate = new Date();

            // Check if birdate is after current date
            if (birthDate.after(currentDate)) {
                return false;
            }

            // Calculate Age
            int age = calculateAge(birthDate, currentDate);
            if (age < 3) {
                return false;
            }
        } catch (ParseException e) {

        }
        return true;
    }

}