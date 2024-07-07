package controller.academicstaff;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
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
        IPupilDAO pupilDAO = new PupilDAO();
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
            IPersonnelDAO personnelDAO = new PersonnelDAO();
            IPupilDAO pupilDAO = new PupilDAO();
            User user = null;
            boolean gender = true;
            String toastMessage = "";
            String toastType = "error";
            if (session.getAttribute("user") != null) {
                String avatar = request.getParameter("avatar");
                String firstName = request.getParameter("firstName").trim();
                String lastName = request.getParameter("lastName").trim();
                String secondGuardianName = request.getParameter("secondGuardianName").trim();
                String firstGuardianName = request.getParameter("firstGuardianName").trim();
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
                String email = request.getParameter("email").trim();
                String note = request.getParameter("note").trim();
                String address = request.getParameter("address").trim();
                String secondGuardianPhoneNumber = request.getParameter("secondGuardianPhoneNumber").trim();
                String firstGuardianPhoneNumber = request.getParameter("firstGuardianPhoneNumber").trim();

                String status = "đang chờ xử lý";
                user = (User) session.getAttribute("user");
                Personnel createdBy = personnelDAO.getPersonnelByUserId(user.getId());

                Pupil pupil = new Pupil(null, null, Helper.formatName(firstName), Helper.formatName(lastName), address, email, status, birthday, gender,
                        Helper.formatName(firstGuardianName), firstGuardianPhoneNumber, avatar, secondGuardianName.isBlank()?null:Helper.formatName(secondGuardianName), secondGuardianPhoneNumber.isBlank()?null:secondGuardianPhoneNumber, createdBy,
                        note);
                ////   Stage for create pupil

                  if(secondGuardianName.isBlank() && !secondGuardianPhoneNumber.isBlank()){
                    toastMessage = "Tạo thật bại ! Vui lòng nhập họ và tên người giám hộ thứ 2!";
                    toastType = "error";
                    session.setAttribute("toastMessage", toastMessage);
                    session.setAttribute("toastType", toastType);
                    List<Pupil> listPupil = pupilDAO.getAllPupils();
                    request.setAttribute("listPupil", listPupil);
                    String newPupilId = pupilDAO.generateId(pupilDAO.getLatest().getId());
                    request.setAttribute("newPupilId", newPupilId);
                    request.getRequestDispatcher("pupil.jsp").forward(request,response);
                }else if(!secondGuardianName.isBlank() && secondGuardianPhoneNumber.isBlank()){
                    toastMessage = "Tạo thật bại ! Vui lòng nhập số điện thoại người giám hộ thứ 2!";
                    toastType = "error";
                    session.setAttribute("toastMessage", toastMessage);
                    session.setAttribute("toastType", toastType);
                    List<Pupil> listPupil = pupilDAO.getAllPupils();
                    request.setAttribute("listPupil", listPupil);
                    String newPupilId = pupilDAO.generateId(pupilDAO.getLatest().getId());
                    request.setAttribute("newPupilId", newPupilId);
                    request.getRequestDispatcher("pupil.jsp").forward(request,response);
                } else if(pupilDAO.checkFirstGuardianPhoneNumberExists(firstGuardianPhoneNumber) || pupilDAO.checkSecondGuardianPhoneNumberExists(secondGuardianPhoneNumber)){
                     if(pupilDAO.checkFirstGuardianPhoneNumberExists(firstGuardianPhoneNumber) ){
                         toastMessage = "Số điện thoại người giám hộ đầu tiên đã tồn tại !";
                     }else if(pupilDAO.checkSecondGuardianPhoneNumberExists(secondGuardianPhoneNumber)){
                         toastMessage = "Số điện thoại người giám hộ thứ hai đã tồn tại !";
                     }
                     session.setAttribute("toastMessage", toastMessage);
                     session.setAttribute("toastType", toastType);
                     List<Pupil> listPupil = pupilDAO.getAllPupils();
                     request.setAttribute("listPupil", listPupil);
                     String newPupilId = pupilDAO.generateId(pupilDAO.getLatest().getId());
                     request.setAttribute("newPupilId", newPupilId);
                     request.getRequestDispatcher("pupil.jsp").forward(request,response);
                 }
                else {
                      if(Helper.formatName(firstName).isBlank() || Helper.formatName(lastName).isBlank() || Helper.formatName(firstGuardianName).isBlank() || Helper.formatName(secondGuardianName).isBlank()) {
                          toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống các trường nhập !";
                          toastType = "error";
                          session.setAttribute("toastMessage", toastMessage);
                          session.setAttribute("toastType", toastType);
                          List<Pupil> listPupil = pupilDAO.getAllPupils();
                          request.setAttribute("listPupil", listPupil);
                          String newPupilId = pupilDAO.generateId(pupilDAO.getLatest().getId());
                          request.setAttribute("newPupilId", newPupilId);
                          request.getRequestDispatcher("pupil.jsp").forward(request, response);
                      } else if (pupilDAO.createPupil(pupil)) {
                        toastMessage = "Xác nhận thành công";
                        toastType = "success";
                        session.setAttribute("toastMessage", toastMessage);
                        session.setAttribute("toastType", toastType);
                        response.sendRedirect("pupil");
                        }else {
                        toastMessage = "Tạo thật bại ! Email hoặc số điện thoại đã tồn tại !";
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
}