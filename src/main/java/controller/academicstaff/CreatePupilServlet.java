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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String regex = "^(0[23578]|09)\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
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

            Pupil pupil = new Pupil(null, null, firstName, lastName, address, email, status, birthday, gender,
                    motherName, motherPhone, avatar, fatherName, fatherPhone, createdBy,
                    note);
//            Check format of phone number
            Matcher matcherFatherPhone = pattern.matcher(fatherPhone);
            Matcher matcherMotherPhone = pattern.matcher(motherPhone);
///          Check format of phone and birthday information
//            Must have age greater than 3 year olds
            if(!matcherFatherPhone.matches() || !matcherMotherPhone.matches() || !checkAge(birth)){
                String toastMessageMotherPhone = "";
                String toasMessageBirthday="";
                String toastMessageFatherPhone = "";
                if(!matcherFatherPhone.matches() ){
                    toastMessageFatherPhone="Số điện thoại không hợp lệ !";
                }
                if(!matcherMotherPhone.matches()){
                    toastMessageMotherPhone="Số điện thoại không hợp lệ !";
                }
                 if(!checkAge(birth)){
                    toasMessageBirthday="Ngày sinh không hợp lệ !";
                }
                request.setAttribute("toastMessageMotherPhone",toastMessageMotherPhone);
                request.setAttribute("toasMessageBirthday",toasMessageBirthday);
                request.setAttribute("toastMessageFatherPhone",toastMessageFatherPhone);
                String newPupilId = pupilDAO.generateId(pupilDAO.getLatest().getId());
                request.setAttribute("newPupilId", newPupilId);
                request.getRequestDispatcher("createPupil.jsp").forward(request,response);
            }else {
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
                response.sendRedirect("listpupil");
            }

        }
    }


    public static int calculateAge(Date birthDate, Date currentDate) {
        // Lấy ngày tháng năm của ngày sinh
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");

        int birthYear = Integer.parseInt(yearFormat.format(birthDate));
        int birthMonth = Integer.parseInt(monthFormat.format(birthDate));
        int birthDay = Integer.parseInt(dayFormat.format(birthDate));

        // Lấy ngày tháng năm của ngày hiện tại
        int currentYear = Integer.parseInt(yearFormat.format(currentDate));
        int currentMonth = Integer.parseInt(monthFormat.format(currentDate));
        int currentDay = Integer.parseInt(dayFormat.format(currentDate));

        // Tính tuổi
        int age = currentYear - birthYear;

        // Kiểm tra xem học sinh đã qua sinh nhật năm nay chưa
        if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
            age--;
        }

        return age;
    }

    public static boolean checkAge(String birthDateString) {
        try {
            // Định dạng ngày
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setLenient(false); // Đảm bảo ngày nhập vào là hợp lệ
            Date birthDate = formatter.parse(birthDateString);

            // Ngày hiện tại
            Date currentDate = new Date();

            // Kiểm tra nếu ngày sinh vượt quá ngày hiện tại
            if (birthDate.after(currentDate)) {
                return false;
            }

            // Tính tuổi
            int age = calculateAge(birthDate, currentDate);
            if (age < 3) {
                return false;
            }
        } catch (ParseException e) {
            System.out.println("Định dạng ngày sinh không hợp lệ. Vui lòng sử dụng định dạng yyyy-MM-dd.");
        }
        return true;
    }

}