package controller.academicstaff;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.pupil.Pupil;
import models.pupil.PupilDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "academicstaff/ListPupilServlet", value = "/academicstaff/listpupil")
public class ListPupilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PupilDAO pupildao = new PupilDAO();

        List<Pupil> listPupil = new ArrayList<>();
        /////  Create list of all pupil as a default list
        listPupil = pupildao.getAllPupils();
        /////  Get request from search function
        String searchInformation = request.getParameter("information");
        if(searchInformation !=null){
            listPupil = pupildao.getListOfSearchPupilByNameOrId(formatString(searchInformation));
        }
        request.setAttribute("listPupil",listPupil);
        request.getRequestDispatcher("staff_listpupil.jsp").forward(request,response);
    }

    private String formatString(String search){
        StringBuilder result = new StringBuilder();
        String[] searchArray = search.split("\\s+");
        for(int i=0;i<searchArray.length;i++){
            result.append(searchArray[i]).append(" ");
        }
        return result.toString().trim();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}