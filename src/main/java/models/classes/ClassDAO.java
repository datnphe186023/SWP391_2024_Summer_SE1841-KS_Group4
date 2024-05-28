package models.classes;

import models.grade.GradeDAO;
import models.personnel.PersonnelDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import utils.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassDAO extends DBContext {
    private Class createClass(ResultSet resultSet) throws SQLException {
        Class c = new Class();
        c.setId(resultSet.getString("id"));
        c.setName(resultSet.getString("name"));
        GradeDAO gradeDAO = new GradeDAO();
        c.setGrade(gradeDAO.getGrade(resultSet.getString("grade_id")));
        PersonnelDAO personnelDAO = new PersonnelDAO();
        c.setTeacher(personnelDAO.getPersonnel(resultSet.getString("teacher_id")));
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        c.setSchoolYear(schoolYearDAO.getSchoolYear(resultSet.getString("school_year_id")));
        c.setStatus(resultSet.getString("status"));
        c.setCreatedBy(personnelDAO.getPersonnel(resultSet.getString("created_by")));
        return c;
    }

    public List<Class> getAll() {
        List<Class> classes = new ArrayList<Class>();
        String sql = "select * from Class";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Class c = createClass(resultSet);
                classes.add(c);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    public List<Class> getBySchoolYear(String schoolYearId) {
        List<Class> classes = new ArrayList<>();
        String sql = "select * from Class where school_year_id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, schoolYearId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Class c = createClass(resultSet);
                classes.add(c);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    public Class getClassById(String id){
        String sql="select * from [Class] where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return createClass(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  null;
    }

    public Class getTeacherClassByYear(String year, String teacherId){
        String sql = "select class_id from classDetails cd join Class c on cd.class_id= c.id  where teacher_id= ?  and school_year_id= ?";
        ClassDAO classDAO = new ClassDAO();
        String classId="" ;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,teacherId);
            preparedStatement.setString(2,year);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                 classId = resultSet.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Class classes =  classDAO.getClassById(classId);
        return classes;
    }

    public String createNewClass(Class c){
        String sql = "insert into [Class] values (?,?,?,?,?,?,?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, generateId(getLatest().getId()));
            preparedStatement.setString(2,c.getName());
            preparedStatement.setString(3, c.getGrade().getId());
            preparedStatement.setString(4, c.getTeacher().getId());
            preparedStatement.setString(5, c.getSchoolYear().getId());
            preparedStatement.setString(6, "đang chờ duyệt");
            preparedStatement.setString(7, c.getCreatedBy().getId());
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return "Tạo mới thất bại. Lớp đã tồn tại";
        }
        catch (Exception e) {
            return Arrays.toString(e.getStackTrace());
        }
        return "success";
    }

    private Class getLatest(){
        String sql = "select TOP 1 * from Class order by id desc";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return createClass(resultSet);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateId(String latestId){
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(latestId);
        int number = 0;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group()) + 1;
        }
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        String result = decimalFormat.format(number);
        return "C" + result;
    }

    public List<Class> getByStatus(String status){
        String sql = " Select * from Class where [status] = N'" + status + "'";
        try{
            List<Class> classes = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Class c = createClass(resultSet);
                classes.add(c);
            }
            return classes;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void reviewClass(String newStatus, String id){
        String sql = "update [Class] set [status]= ? where [id] = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if (newStatus.equals("accept")) {
                newStatus = "đã được duyệt";
            } else {
                newStatus = "đã từ chối";
            }
            preparedStatement.setString(1,newStatus);
            preparedStatement.setString(2,id);
            preparedStatement.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
