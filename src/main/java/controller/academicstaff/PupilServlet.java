package controller.academicstaff;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.pupil.Pupil;
import models.pupil.PupilDAO;
import utils.Helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "academicstaff/ListPupilServlet", value = "/academicstaff/listpupil")
public class ListPupilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PupilDAO pupilDAO = new PupilDAO();
        List<Pupil> listPupil = pupilDAO.getAllPupils();
        String newPupilId = pupilDAO.generateId(pupilDAO.getLatest().getId());
        request.setAttribute("newPupilId", newPupilId);
        request.setAttribute("listPupil",listPupil);
        request.getRequestDispatcher("listPupil.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}