package controller.parent;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.foodmenu.FoodMenu;
import models.foodmenu.FoodMenuDAO;
import models.foodmenu.MenuDetail;
import models.grade.Grade;
import models.grade.GradeDAO;
import models.pupil.IPupilDAO;
import models.pupil.PupilDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.user.User;
import models.week.IWeekDAO;
import models.week.Week;
import models.week.WeekDAO;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "parent/ViewMealTimetableServlet", urlPatterns={"/parent/viewmealtimetable"})
public class ViewMealTimetableServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FoodMenuDAO foodMenuDAO = new FoodMenuDAO();
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        HttpSession session = request.getSession();
        IWeekDAO weekDAO = new WeekDAO();
        IPupilDAO pupilDAO = new PupilDAO();
        User user = (User) session.getAttribute("user");
        GradeDAO gradeDAO = new GradeDAO();
        Date currentDate = Date.from(Instant.now());
        String sltedw ="";
        String sltedsy = "";
        String sltedg ="";
        if(weekDAO.getCurrentWeek(currentDate)!=null){
            sltedw = weekDAO.getCurrentWeek(currentDate);
            sltedsy = weekDAO.getYearByWeek(sltedw);
            sltedg = gradeDAO.getGradeFromPupilIdAndSchoolYearId(sltedsy,pupilDAO.getPupilByUserId(user.getId()).getId());
        }else if(schoolYearDAO.getClosestSchoolYears()!=null && schoolYearDAO.checkPupilInClassOfSchoolYear(pupilDAO.getPupilByUserId(user.getId()).getId(),schoolYearDAO.getClosestSchoolYears().getId())) {
            sltedsy = schoolYearDAO.getClosestSchoolYears().getId();
            sltedw = weekDAO.getfirstWeekOfClosestSchoolYear(sltedsy).getId();
            sltedg = gradeDAO.getGradeFromPupilIdAndSchoolYearId(sltedsy,pupilDAO.getPupilByUserId(user.getId()).getId());
        }
        else{
            sltedw = weekDAO.getLastWeekOfClosestSchoolYearOfPupil(pupilDAO.getPupilByUserId(user.getId()).getId()).getId();
            sltedsy = weekDAO.getYearByWeek(sltedw);
            sltedg = gradeDAO.getGradeFromPupilIdAndSchoolYearId(sltedsy,pupilDAO.getPupilByUserId(user.getId()).getId());
        }
        List<SchoolYear> schoolYearList = schoolYearDAO.getAll();
        List<Grade> gradeList = gradeDAO.getGradeByUserId(user.getId());
        List<Week> weekList = weekDAO.getWeeks(sltedsy);

        List<MenuDetail>  menuDetailList = foodMenuDAO.getMenuDetails(sltedg,sltedw,sltedsy,"đã được duyệt");

        String menustatus ="";
        if(menuDetailList.size()>0){
            menustatus = "created";
        }else if (menuDetailList.isEmpty()){
            menustatus = "not-created";
        }
        request.setAttribute("menustatus",menustatus);
        request.setAttribute("schoolYearList",schoolYearList );
        request.setAttribute("gradeList",gradeList );
        request.setAttribute("weekList",weekList);
        request.setAttribute("sltedg",sltedg );
        request.setAttribute("sltedsy",sltedsy );
        request.setAttribute("sltedw",sltedw );
        request.getRequestDispatcher("viewMealTimetable.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String week = request.getParameter("week");
        GradeDAO gradeDAO = new GradeDAO();
        String schoolyear = request.getParameter("schoolyear");

        FoodMenuDAO foodMenuDAO = new FoodMenuDAO();
        WeekDAO weekDAO = new WeekDAO();
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        IPupilDAO pupilDAO = new PupilDAO();

        List<SchoolYear> schoolYearList = schoolYearDAO.getAll();
        List<Grade> gradeList = gradeDAO.getGradeByUserId(user.getId());
        List<Week> weekList = weekDAO.getWeeks(schoolyear);
        List<FoodMenu> foodMenuList = foodMenuDAO.getAllFoodMenu();
        List<MenuDetail> menuDetailList = new ArrayList<>();

        String grade = gradeDAO.getGradeFromPupilIdAndSchoolYearId(schoolyear,pupilDAO.getPupilByUserId(user.getId()).getId());

        if(grade!=null && week!=null && schoolyear!=null){
            menuDetailList = foodMenuDAO.getMenuDetails(grade,week,schoolyear,"đã được duyệt");
        }
        String menustatus ="";
        if(menuDetailList.size()>0){
            menustatus = "created";
        }else if (menuDetailList.isEmpty() && grade!=null && week!=null && schoolyear!=null&& weekDAO.checkWeekInSchoolYear(week,schoolyear)){
            menustatus = "not-created";
        }else{
            menustatus = "not-enough-data";
        }
        request.setAttribute("menustatus",menustatus);
        request.setAttribute("sltedg",grade );
        request.setAttribute("sltedsy",schoolyear );
        request.setAttribute("sltedw",week );
        request.setAttribute("schoolYearList",schoolYearList );
        request.setAttribute("gradeList",gradeList );
        request.setAttribute("weekList",weekList);
        request.setAttribute("foodMenuList", foodMenuList);
        request.setAttribute("menuDetailList", menuDetailList);
        request.getRequestDispatcher("viewMealTimetable.jsp").forward(request, response);
    }
}