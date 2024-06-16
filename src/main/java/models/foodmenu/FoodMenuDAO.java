package models.foodmenu;

import models.classes.ClassDAO;
import models.day.Day;
import models.day.TimeInDay;
import models.grade.GradeDAO;
import models.personnel.PersonnelDAO;
import models.schoolYear.SchoolYear;
import models.week.Week;
import utils.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    private TimeInDay getTimeInDay(String time_in_day_id) {
        TimeInDay timeInDay = new TimeInDay();
        String sql = "select * from TimeInDays where id=?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,time_in_day_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                timeInDay = new TimeInDay(
                        resultSet.getString("id"),
                        getDay(resultSet.getString("date_id")),
                        resultSet.getString("name")
                );
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return timeInDay;
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
        List<MenuDetail> menuDetails = new ArrayList<>();
        String sql = "select md.* from Weeks w join Days d on w.id= d.week_id\n" +
                "                join TimeInDays tid on d.id=tid.date_id\n" +
                "                join MenuDetails md on tid.id = md.time_in_day_id\n" +
                "                join FoodMenus fm on md.food_menu_id = fm.id where md.grade_id = 'G000001' and w.id='W000001'";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MenuDetail menuDetail = new MenuDetail();
                menuDetail.setId(resultSet.getString("id"));
                menuDetail.setFoodMenu(getFoodMenu(resultSet.getString("food_menu_id")));
                menuDetail.setGrade(gradeDAO.getGrade(resultSet.getString("grade_id")));
                menuDetail.setTimeInDay(getTimeInDay(resultSet.getString("time_in_day_id")));
                menuDetail.setStatus(resultSet.getString("status"));
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
        List<MenuDetail> menuDetails = new ArrayList<>();
        String sql = "select md.* from Weeks w join Days d on w.id= d.week_id\n" +
                "                join TimeInDays tid on d.id=tid.date_id\n" +
                "                join MenuDetails md on tid.id = md.time_in_day_id\n" +
                "                 where md.grade_id = ? and w.id= ? and w.school_year_id =?";
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
                menuDetail.setTimeInDay(getTimeInDay(resultSet.getString("time_in_day_id")));
                menuDetail.setStatus(resultSet.getString("status"));
                menuDetails.add(menuDetail);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return menuDetails;
    }
}
