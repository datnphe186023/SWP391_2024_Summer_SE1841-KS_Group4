package models.foodmenu;

import models.classes.ClassDAO;
import models.day.Day;
import models.day.TimeInDay;
import models.grade.GradeDAO;
import models.personnel.PersonnelDAO;
import models.schoolYear.SchoolYear;
import models.timeslot.ITimeslotDAO;
import models.timeslot.TimeslotDAO;

import models.timetable.Timetable;

import models.week.Week;
import utils.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodMenuDAO extends DBContext implements IFoodMenuDAO {


    @Override
    public List<FoodMenu> getAllFoodMenu() {
        ClassDAO cdao = new ClassDAO();
        List<FoodMenu> foodMenus = new ArrayList<>();
        String sql = "select * from FoodMenus";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                FoodMenu foodMenu = new FoodMenu();
                foodMenu.setId(resultSet.getString("id"));
                foodMenu.setFoodDetails(resultSet.getString("food_detail"));
                foodMenu.setStatus(resultSet.getString("status"));
                foodMenus.add(foodMenu);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return foodMenus;
    }



    private Day getDay(String date_id) {
        Day day = new Day();
        String sql = "select * from Days where id=?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,date_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                day = new Day
                        (resultSet.getString("id"),
                         getWeek(resultSet.getString("week_id")),
                          resultSet.getDate("date")
                        );
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return day;
    }

    private Week getWeek(String id) {
        Week week = new Week();
        String sql = "select * from Weeks where id=?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                  week = new Week
                          (
                                  resultSet.getDate("start_date"),
                                  resultSet.getString("id"),
                                  resultSet.getDate("end_date"),
                                  getSchoolYear(resultSet.getString("school_year_id"))
                          );

            }
        }catch (Exception e){
            System.out.println(e);
        }
        return week;
    }

    private SchoolYear getSchoolYear (String school_year_id) {
        SchoolYear schoolYear =new SchoolYear();
        PersonnelDAO pdao = new PersonnelDAO();
        String sql = "select * from SchoolYears where id=?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,school_year_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                schoolYear = new SchoolYear(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getString("description"),
                        pdao.getPersonnel(resultSet.getString("created_by"))
                );
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return schoolYear;
    }


    public List<MenuDetail> getAllMenuDetails() {
        GradeDAO gradeDAO = new GradeDAO();
        ITimeslotDAO timeslotDAO = new TimeslotDAO();
        List<MenuDetail> menuDetails = new ArrayList<>();
        String sql = "select md.* from Weeks w join Days d on w.id= d.week_id\n" +
                "                                join MenuDetails md on d.id = md.date_id";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MenuDetail menuDetail = new MenuDetail();
                menuDetail.setId(resultSet.getString("id"));
                menuDetail.setFoodMenu(getFoodMenu(resultSet.getString("food_menu_id")));
                menuDetail.setGrade(gradeDAO.getGrade(resultSet.getString("grade_id")));
                menuDetail.setDay(getDay(resultSet.getString("date_id")));
                menuDetail.setStatus(resultSet.getString("status"));
                menuDetail.setTimeslot(timeslotDAO.getTimeslotById(resultSet.getString("timeslot_id")));
                menuDetails.add(menuDetail);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return menuDetails;
    }

    public FoodMenu getFoodMenu(String id) {
        FoodMenu foodMenu = new FoodMenu();
        String sql = "select * from FoodMenus where id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                foodMenu.setId(resultSet.getString("id"));
                foodMenu.setFoodDetails(resultSet.getString("food_detail"));
                foodMenu.setStatus(resultSet.getString("status"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return foodMenu;
    }

    public List<MenuDetail> getMenuDetails(String grade, String week ,String school_year_id ) {
        GradeDAO gradeDAO = new GradeDAO();
        ITimeslotDAO timeslotDAO = new TimeslotDAO();
        List<MenuDetail> menuDetails = new ArrayList<>();
        String sql = "select md.* from Weeks w join Days d on w.id= d.week_id\n" +
                "                                join MenuDetails md on d.id = md.date_id\n" +
                "                 where md.grade_id = ? and w.id= ? and w.school_year_id =? and md.status = N'Đã Duyệt' ";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,grade);
            statement.setString(2,week);
            statement.setString(3,school_year_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MenuDetail menuDetail = new MenuDetail();
                menuDetail.setId(resultSet.getString("id"));
                menuDetail.setFoodMenu(getFoodMenu(resultSet.getString("food_menu_id")));
                menuDetail.setGrade(gradeDAO.getGrade(resultSet.getString("grade_id")));
                menuDetail.setDay(getDay(resultSet.getString("date_id")));
                menuDetail.setStatus(resultSet.getString("status"));
                menuDetail.setTimeslot(timeslotDAO.getTimeslotById(resultSet.getString("timeslot_id")));
                menuDetails.add(menuDetail);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return menuDetails;
    }

    public void createMenuDetail(MenuDetail menuDetail) {
        String sql = "INSERT INTO MenuDetails "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, menuDetail.getId());
            statement.setString(2, menuDetail.getFoodMenu().getId());
            statement.setString(3, menuDetail.getGrade().getId());
            statement.setString(4, menuDetail.getTimeslot().getId());
            statement.setString(5, menuDetail.getStatus());
            statement.setString(6, menuDetail.getDay().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting timetable", e);
        }
    }
    public int getTotalID() {
        int totalID = 0;
        String sql = " select count(id) as totalid from MenuDetails";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
           if (resultSet.next()) {
                totalID = resultSet.getInt("totalid");
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return totalID;
    }

}
