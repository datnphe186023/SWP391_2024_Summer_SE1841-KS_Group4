package models.schoolYear;

import models.personnel.Personnel;
import models.personnel.PersonnelDAO;
import models.week.WeekDAO;
import utils.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;
import utils.Helper;

public class SchoolYearDAO extends DBContext {
    private SchoolYear createNewSchoolYear(ResultSet rs) throws SQLException {
        SchoolYear schoolYear = new SchoolYear();
        schoolYear.setId(rs.getString("id"));
        schoolYear.setName(rs.getString("name"));
        schoolYear.setStartDate(rs.getDate("start_date"));
        schoolYear.setEndDate(rs.getDate("end_date"));
        schoolYear.setDescription(rs.getString("description"));
        PersonnelDAO personnelDAO = new PersonnelDAO();
        Personnel personnel = personnelDAO.getPersonnel(rs.getString("created_by"));
        schoolYear.setCreatedBy(personnel);
        return schoolYear;
    }

    public List<SchoolYear> getAll() {
        List<SchoolYear> schoolYears = new ArrayList<SchoolYear>();
        String sql = "select * from schoolYears";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                SchoolYear schoolYear = createNewSchoolYear(rs);
                schoolYears.add(schoolYear);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return schoolYears;
    }

    public SchoolYear getLatest() {
        String sql = "SELECT TOP 1 * FROM SchoolYears ORDER BY ID DESC";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createNewSchoolYear(rs);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String createNewSchoolYear(SchoolYear schoolYear) {
        String sql = "insert into SchoolYears values(?,?,?,?,?,?)";
        try{
            if (validateSchoolYear(schoolYear).equals("success")){
                PreparedStatement statement = connection.prepareStatement(sql);
                String newSchoolYearId = generateId(getLatest().getId());
                statement.setString(1, newSchoolYearId);
                statement.setString(2, schoolYear.getName());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String sqlStartDate = dateFormat.format(schoolYear.getStartDate());
                statement.setString(3, sqlStartDate);
                String sqlEndDate = dateFormat.format(schoolYear.getEndDate());
                statement.setString(4, sqlEndDate);
                statement.setString(5, schoolYear.getDescription());
                statement.setString(6, schoolYear.getCreatedBy().getId());
                statement.execute();
            } else {
                return "Tạo mới thất bại! " + validateSchoolYear(schoolYear);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return "Tạo mới thất bại! " + sqlException.getMessage();
        } catch (Exception e) {
            return "Tạo mới thất bại! " + e.getMessage();
        }
        return "success";
    }

    private String validateSchoolYear(SchoolYear schoolYear) {
        if (schoolYear.getEndDate().before(schoolYear.getStartDate())) {
            return "Ngày kết thúc không thể trước ngày bắt đầu";
        }
        Date lastEndDate = getLatest().getEndDate();
        if (!schoolYear.getStartDate().after(lastEndDate)) {
            return "Ngày bắt đầu năm học mới phải sau ngày kết thúc năm học cũ";
        }
        LocalDate startLocalDate = convertToLocalDate(schoolYear.getStartDate());
        LocalDate endLocalDate = convertToLocalDate(schoolYear.getEndDate());

        // Validate that the years are exactly one year apart
        if (endLocalDate.getYear() - startLocalDate.getYear() != 1) {
            return "Ngày kết thúc phải cách ngày bắt đầu đúng một năm";
        }
        return "success";
    }

    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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
        return "SY" + result;
    }

    public SchoolYear getSchoolYear(String id) {
        String sql = "select * from schoolYears where id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                SchoolYear schoolYear = createNewSchoolYear(rs);
                return schoolYear;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
