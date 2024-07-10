/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.teacher;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.evaluation.HealthCheckUp;
import models.evaluation.HealthCheckUpDAO;
import models.pupil.IPupilDAO;
import models.pupil.PupilDAO;

/**
 *
 * @author Admin
 */
public class AddHealthReportServlet extends HttpServlet {

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
        request.getRequestDispatcher("listHealthPupilDetails.jsp").forward(request, response);
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
        HttpSession session = request.getSession();

        String pupil_id = request.getParameter("pupil_id");
        IPupilDAO pupilDAO = new PupilDAO();
        HealthCheckUpDAO healthCheckUpDAO = new HealthCheckUpDAO();

        // Get check up date
        String checkUpDateStr = request.getParameter("check_up_date");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date checkUpDate = null;
        try {
            checkUpDate = sdf.parse(checkUpDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            session.setAttribute("toastType", "error");
            session.setAttribute("toastMessage", "Ngày không hợp lệ, báo cáo sức khỏe của trẻ đã tồn tại!");
            response.sendRedirect("health-details");
            return;
        }

        // Convert java.util.Date to java.sql.Date
        java.sql.Date sqlCheckUpDate = new java.sql.Date(checkUpDate.getTime());

        // Check if health report already exists
        boolean checkHealthReportExisted = healthCheckUpDAO.healthCheckUpExists(pupil_id, sqlCheckUpDate);
        if (checkHealthReportExisted) {
            session.setAttribute("toastType", "error");
            session.setAttribute("toastMessage", "Báo cáo sức khỏe của trẻ đã được tạo!");
        } else {
            // Get height
            float height = Float.parseFloat(request.getParameter("height"));
            // Get weight
            float weight = Float.parseFloat(request.getParameter("weight"));
            // Get average development stage
            String averageDevelopmentStage = request.getParameter("average_development_stage");
            // Get blood pressure
            String bloodPressure = request.getParameter("blood_pressure");
            // Get teeth
            String teeth = request.getParameter("teeth");
            // Get eyes
            String eyes = request.getParameter("eyes");
            // Get Ear - nose - throat
            String earNoseThroat = request.getParameter("ear_nose_throat");
            // Get note
            String note = request.getParameter("note");

            // Create new health check up
            HealthCheckUp healthCheckUp = new HealthCheckUp();
            healthCheckUp.setId(healthCheckUpDAO.generateHealthCheckUpId());
            healthCheckUp.setPupil(pupilDAO.getPupilsById(pupil_id));
            healthCheckUp.setCheckUpDate(sqlCheckUpDate);
            healthCheckUp.setHeight(height);
            healthCheckUp.setWeight(weight);
            healthCheckUp.setAverageDevelopmentStage(averageDevelopmentStage);
            healthCheckUp.setBloodPressure(bloodPressure);
            healthCheckUp.setTeeth(teeth);
            healthCheckUp.setEyes(eyes);
            healthCheckUp.setEarNoseThroat(earNoseThroat);
            healthCheckUp.setNotes(note);

            boolean success = healthCheckUpDAO.addHealthCheckUp(healthCheckUp);
            // Set success message and redirect
            if (success) {
                session.setAttribute("toastType", "success");
                session.setAttribute("toastMessage", "Báo cáo sức khỏe của trẻ đã được tạo!");
            } else {
                session.setAttribute("toastType", "error");
                session.setAttribute("toastMessage", "Báo cáo sức khỏe của trẻ tạo thất bại!");
            }
        }
        String redirectUrl = "health-details?pupilid=" + pupil_id + "&&schoolyear=" + request.getParameter("schoolYear");
        response.sendRedirect(redirectUrl);
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
