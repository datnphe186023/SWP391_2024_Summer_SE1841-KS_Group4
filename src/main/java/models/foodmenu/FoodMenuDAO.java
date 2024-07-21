package models.foodmenu;

import models.classes.ClassDAO;
import models.day.Day;
import models.day.DayDAO;
import models.day.IDayDAO;
import models.grade.GradeDAO;
import models.personnel.PersonnelDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import models.timeslot.ITimeslotDAO;
import models.timeslot.TimeslotDAO;
import models.week.Week;
import utils.DBContext;
import utils.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FoodMenuDAO extends DBContext implements IFoodMenuDAO {

    private FoodMenu createFoodMenu(ResultSet resultSet) throws SQLException {
        FoodMenu foodMenu = new FoodMenu();
        foodMenu.setId(resultSet.getString("id"));
        foodMenu.setFoodDetails(resultSet.getString("food_detail"));
        foodMenu.setStatus(resultSet.getString("status"));
        return foodMenu;
    }


    @Override
    public List<FoodMenu> getAllFoodMenu() {
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

    public List<FoodMenu> getAllFoodMenuDESC(String exception) {
        List<FoodMenu> foodMenus = new ArrayList<>();
        String sql = "select * from FoodMenus where not id = ? and status = N'đang chờ xử lý' order by id desc";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, exception);
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

    @Override
    public List<FoodMenu> getFoodMenuWithStatus(String status) {
        List<FoodMenu> foodMenus = new ArrayList<>();
        String sql = "select * from FoodMenus where status = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, status);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                FoodMenu foodMenu = createFoodMenu(resultSet);
                foodMenus.add(foodMenu);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return foodMenus;
    }


    public List<MenuDetail> getAllMenuDetails() {
        GradeDAO gradeDAO = new GradeDAO();
        ITimeslotDAO timeslotDAO = new TimeslotDAO();
        IDayDAO dayDAO = new DayDAO();
        List<MenuDetail> menuDetails = new ArrayList<>();
        String sql = "select md.* from Weeks w join Days d on w.id= d.week_id\n" + "                                join MenuDetails md on d.id = md.date_id";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MenuDetail menuDetail = new MenuDetail();
                menuDetail.setId(resultSet.getString("id"));
                menuDetail.setFoodMenu(getFoodMenu(resultSet.getString("food_menu_id")));
                menuDetail.setGrade(gradeDAO.getGrade(resultSet.getString("grade_id")));
                menuDetail.setDay(dayDAO.getDayByID(resultSet.getString("date_id")));
                menuDetail.setStatus(resultSet.getString("status"));
                menuDetail.setTimeslot(timeslotDAO.getTimeslotById(resultSet.getString("timeslot_id")));
                menuDetails.add(menuDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menuDetails;
    }

    public FoodMenu getFoodMenu(String id) {
        FoodMenu foodMenu = new FoodMenu();
        String sql = "select * from FoodMenus where id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                foodMenu.setId(resultSet.getString("id"));
                foodMenu.setFoodDetails(resultSet.getString("food_detail"));
                foodMenu.setStatus(resultSet.getString("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return foodMenu;
    }

    public List<MenuDetail> getMenuDetails(String grade, String week, String school_year_id,String status) {
        GradeDAO gradeDAO = new GradeDAO();
        ITimeslotDAO timeslotDAO = new TimeslotDAO();
        IDayDAO dayDAO = new DayDAO();
        List<MenuDetail> menuDetails = new ArrayList<>();

        String sql = "select md.* from Weeks w join Days d on w.id= d.week_id\n" +
                "                                join MenuDetails md on d.id = md.date_id\n" +
                "                 where md.grade_id = ? and w.id= ? and w.school_year_id =? and  md.status = ? ";


        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, grade);
            statement.setString(2, week);
            statement.setString(3, school_year_id);
            statement.setNString(4, status);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MenuDetail menuDetail = new MenuDetail();
                menuDetail.setId(resultSet.getString("id"));
                menuDetail.setFoodMenu(getFoodMenu(resultSet.getString("food_menu_id")));
                menuDetail.setGrade(gradeDAO.getGrade(resultSet.getString("grade_id")));
                menuDetail.setDay(dayDAO.getDayByID(resultSet.getString("date_id")));
                menuDetail.setStatus(resultSet.getString("status"));
                menuDetail.setTimeslot(timeslotDAO.getTimeslotById(resultSet.getString("timeslot_id")));
                menuDetails.add(menuDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menuDetails;
    }

    public void createMenuDetail(MenuDetail menuDetail) {
        String sql = "INSERT INTO MenuDetails " + "VALUES (?, ?, ?, ?, ?, ?)";
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalID;
    }


    public boolean existsMealTimetableForGradeInCurrentWeek(String gradeId, String dayId) {
        String sql = "SELECT COUNT(*) FROM MenuDetails WHERE grade_id = ? and date_id = ? and (status = N'đang chờ xử lý'or status = N'đã được duyệt' )";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, gradeId);
            stmt.setString(2, dayId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking timetable existence", e);
        }
        return false;
    }

    public List<MenuDetail> getMenuDetailsforCreate(String grade, String week ,String school_year_id ) {
        GradeDAO gradeDAO = new GradeDAO();
        ITimeslotDAO timeslotDAO = new TimeslotDAO();
        List<MenuDetail> menuDetails = new ArrayList<>();
        IDayDAO dayDAO = new DayDAO();
        String sql = "select md.* from Weeks w join Days d on w.id= d.week_id\n" +
                "                                join MenuDetails md on d.id = md.date_id\n" +
                "                 where md.grade_id = ? and w.id= ? and w.school_year_id =? and (md.status = N'đang chờ xử lý'or md.status = N'đã được duyệt' ) ";

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
                menuDetail.setDay(dayDAO.getDayByID(resultSet.getString("date_id")));
                menuDetail.setStatus(resultSet.getString("status"));
                menuDetail.setTimeslot(timeslotDAO.getTimeslotById(resultSet.getString("timeslot_id")));
                menuDetails.add(menuDetail);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return menuDetails;
    }



    @Override
    public String createFoodMenu(FoodMenu foodMenu) {
        String validateFoodMenu = validateCreateFoodMenu(foodMenu);
        if (!validateFoodMenu.equals("success")){
            return validateFoodMenu;
        }
        String sql = "insert into [FoodMenus] values (?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            String newId;
            if (getLatest()!=null){
                newId = generateId(getLatest().getId());
            } else {
                newId = "FM000001";
            }
            statement.setString(1, newId);
            statement.setString(2, foodMenu.getFoodDetails());
            statement.setString(3, "đang chờ xử lý");
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return "Thao tác thất bại. Thực đơn đã tồn tại";
        } catch (Exception e) {
            e.printStackTrace();
            return "Thao tác thất bại! Vui lòng thử lại sau";
        }
        return "success";
    }

    private String validateCreateFoodMenu(FoodMenu foodMenu){

        if (foodMenu.getFoodDetails().matches("^[a-zA-Z0-9" + "ĐđaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴ"
                + "\\s-]{1,255}$")) {
            return "success";
        } else {
            return "Chi tiết món ăn phải là tiếng việt và ngăn cách nhau bởi dấu gạch ngang";
        }
    }

    private String generateId(String latestId) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(latestId);
        int number = 0;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group()) + 1;
        }
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        String result = decimalFormat.format(number);
        return "SY" + result;
    }

    private FoodMenu getLatest() {
        String sql = "SELECT TOP 1 * FROM [FoodMenus] ORDER BY ID DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createFoodMenu(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getMenuDetailsforProcess(String schoolyearID) {
        GradeDAO gradeDAO = new GradeDAO();
        ITimeslotDAO timeslotDAO = new TimeslotDAO();
        List<String> dataList = new ArrayList<>();
        String sql = "select distinct w.id,w.school_year_id,md.grade_id from Weeks w join Days d on w.id= d.week_id\n" +
                "                                               join MenuDetails md on d.id = md.date_id\n" +
                "                                where   md.status = N'đang chờ xử lý' and w.school_year_id = ?  ";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,schoolyearID);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
               String data = resultSet.getString("id") + "-" + resultSet.getString("school_year_id") + "-" + resultSet.getString("grade_id");
               dataList.add(data);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return dataList;
    }

    @Override
    public boolean AcceptorDenyMenu(String id, String status){
        String sql = "UPDATE [dbo].[MenuDetails]\n"
                + "   SET [status] = ? \n"
                + " WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setNString(1, status);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean AcceptorDenyFoodMenu(String id, String status){
        String sql = "UPDATE [dbo].[FoodMenus]\n"
                + "   SET [status] = ? \n"
                + " WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setNString(1, status);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public String editFoodMenu(FoodMenu foodMenu) {
        String validateFoodMenu = validateCreateFoodMenu(foodMenu);
        if (!validateFoodMenu.equals("success")){
            return validateFoodMenu;
        }
        String sql = "update [FoodMenus] set food_detail = ?, status = ? where id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, foodMenu.getFoodDetails());
            preparedStatement.setString(2, "đang chờ xử lý");
            preparedStatement.setString(3, foodMenu.getId());
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            return "Thao tác thất bại! Vui lòng thử lại sau";
        }
        return "success";
    }
}
