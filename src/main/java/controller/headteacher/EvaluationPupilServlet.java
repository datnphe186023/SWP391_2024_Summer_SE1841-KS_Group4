/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.headteacher;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import models.classes.ClassDAO;
import models.classes.IClassDAO;
import models.evaluation.EvaluationDAO;
import models.evaluation.IEvaluationDAO;
import models.pupil.IPupilDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;

/**
 *
 * @author TuyenCute
 */
@WebServlet(name = "EvaluationPupilServlet", urlPatterns = {"/headteacher/evaluapupil"})
public class EvaluationPupilServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EvaluationPupilServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EvaluationPupilServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        ISchoolYearDAO schoolyearDAO = new SchoolYearDAO();
        List<SchoolYear> listSchoolyear = schoolyearDAO.getAll();
        request.setAttribute("listSchoolYear", listSchoolyear);
        request.getRequestDispatcher("evaluaPupil.jsp").forward(request, response);
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
        String schoolYear = request.getParameter("schoolyear");
        ISchoolYearDAO schoolyearDAO = new SchoolYearDAO();
        List<SchoolYear> listSchoolyear = schoolyearDAO.getAll();
        IPupilDAO pupilDAO = new PupilDAO();
        List<Pupil> listPupil = pupilDAO.getPupilsBySchoolYearID(schoolYear);
        int sumPupil = 0;
        List<String> listClass = new ArrayList<>();
        List<Integer> listEvaluation = new ArrayList<>();
        List<Integer> listTotalEvaluation = new ArrayList<>();
        List<Boolean> listAchieved = new ArrayList<>();
        IClassDAO classDAO = new ClassDAO();
        IEvaluationDAO evaluationDAO = new EvaluationDAO();
        int evaluation = 0;
        int totalEvalua = 0;
        int totalAchieved = 0;
        int totalNotAchieved = 0;
        for (Pupil pupil : listPupil) {
            sumPupil += 1;
            models.classes.Class c = classDAO.getClassByPupilIDandSchoolYearId(pupil.getId(), schoolYear);
            listClass.add(c != null ? c.getName() : "");
            evaluation = evaluationDAO.getEvaluationByPupilIdandStatusGood(pupil.getId()) / 3;
            listEvaluation.add(evaluation);
            totalEvalua = evaluationDAO.getEvaluationByPupilIdandAllStatus(pupil.getId()) / 3;
            listTotalEvaluation.add(totalEvalua);
            boolean isAchieved = totalEvalua != 0 && (evaluation * 100 / totalEvalua) >= 50; // Thêm điều kiện kiểm tra chia cho 0
            listAchieved.add(isAchieved);
            if (isAchieved) {
                totalAchieved += 1; // Đếm số học sinh đạt
            } else {
                totalNotAchieved += 1; // Đếm số học sinh không đạt
            }
        }
        request.setAttribute("totalAchieved", totalAchieved);
        request.setAttribute("totalNotAchieved", totalNotAchieved);
        request.setAttribute("listAchieved", listAchieved);
        request.setAttribute("listTotalEvalua", listTotalEvaluation);
        request.setAttribute("listEvaluation", listEvaluation);
        request.setAttribute("listClass", listClass);
        request.setAttribute("listPupil", listPupil);
        request.setAttribute("sumPupil", sumPupil);
        request.setAttribute("listSchoolYear", listSchoolyear);
        request.setAttribute("schoolYearEQ", schoolYear);
        request.getRequestDispatcher("evaluaPupil.jsp").forward(request, response);
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
