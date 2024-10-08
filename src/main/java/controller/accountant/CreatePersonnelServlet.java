/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.accountant;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.personnel.IPersonnelDAO;
import models.personnel.PersonnelDAO;
import utils.Helper;

/**
 *
 * @author asus
 */
@WebServlet(name="CreatePersonnelServlet", urlPatterns={"/accountant/createpersonnel"})
public class CreatePersonnelServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet CreatePersonnelServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreatePersonnelServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession(true);
       String action = request.getParameter("action");
       String message="";
       String type= "fail";
       
       IPersonnelDAO personnelDAO = new PersonnelDAO();
       if(action.equalsIgnoreCase("create")){
       String xfirstname = request.getParameter("firstname");
       String xlastname = request.getParameter("lastname");
       String xbirthday = request.getParameter("birthday");
       String xaddress = request.getParameter("address");
       String xgender = request.getParameter("gender");
       String xemail = request.getParameter("email");
       String xphone = request.getParameter("phone");
       String xrole = request.getParameter("role");
       String xavatar = request.getParameter("avatar");
       int role = Integer.parseInt(xrole);
       int gender = Integer.parseInt(xgender);
       String id =generateId(role);
//        if(!checkAge(xbirthday)){
//           message="Thao tác Thất bại ! Chưa đủ 18 tuổi";
//           session.setAttribute("firstname", xfirstname);
//
//       }
//       else
       if(!personnelDAO.checkPersonnelPhone(xphone)&&!personnelDAO.checkPersonnelEmail(xemail)){
            personnelDAO.insertPersonnel(id.trim(), Helper.formatName(xfirstname.trim()) ,Helper.formatName(xlastname.trim()) , gender, xbirthday.trim(), xaddress.trim(), xemail.trim(), xphone.trim(), role, xavatar.trim());
       message="Thêm nhân viên thành công";
       type = "success" ;
       }else if(personnelDAO.checkPersonnelPhone(xphone)&&personnelDAO.checkPersonnelEmail(xemail)){
         message="Thêm nhân viên thất bại!Trùng dữ liệu email và số điện thoại";

       }else if(personnelDAO.checkPersonnelPhone(xphone)){
        message="Thêm nhân viên thất bại!Trùng dữ liệu số điện thoại ";

       }else if(personnelDAO.checkPersonnelEmail(xemail)){
        message="Thêm nhân viên thất bại!Trùng dữ liệu email ";

       }
           session.setAttribute("firstname", xfirstname);
           session.setAttribute("lastname", xlastname);
           session.setAttribute("birthday", xbirthday);
           session.setAttribute("address", xaddress);
           session.setAttribute("gender", xgender);
           session.setAttribute("email", xemail);
           session.setAttribute("phone", xphone);
           session.setAttribute("role", xrole);
           session.setAttribute("avatar", xavatar);
           session.removeAttribute("message");
           session.removeAttribute("type");
           session.setAttribute("message", message);
           session.setAttribute("type", type);
           response.sendRedirect("listpersonnel");


        
    }
  }


    private String generateId(int role){
        String id ;
        int newid ;
        IPersonnelDAO personnelDAO = new PersonnelDAO();
        newid= personnelDAO.getNumberOfPersonByRole(role)+1;
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        id= decimalFormat.format(newid);
        if (role == 0) {
            id = "AD" + id;
        } else if (role == 1) {
            id = "HT" + id;
        } else if (role == 2) {
            id = "AS" + id;
        } else if (role==3){
            id = "ACC"+ id;
        }else if (role == 4){
            id = "TEA"+ id;
        }
        
        return id;
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
            if (age <=18) {
                return false;
            }
        } catch (ParseException e) {
            System.out.println("Định dạng ngày sinh không hợp lệ. Vui lòng sử dụng định dạng yyyy-MM-dd.");
        }
        return true;
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
