package models.schoolYear;

import models.personnel.IPersonnelDAO;
import models.personnel.Personnel;
import models.personnel.PersonnelDAO;
import utils.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import models.week.IWeekDAO;
import models.week.WeekDAO;
import utils.Helper;

public class SchoolYearDAO extends DBContext implements ISchoolYearDAO {

    private SchoolYear createNewSchoolYear(ResultSet rs) throws SQLException {
        SchoolYear schoolYear = new SchoolYear();
        schoolYear.setId(rs.getString("id"));
        schoolYear.setName(rs.getString("name"));
        schoolYear.setStartDate(rs.getDate("start_date"));
        schoolYear.setEndDate(rs.getDate("end_date"));
        schoolYear.setDescription(rs.getString("description"));
        IPersonnelDAO personnelDAO = new PersonnelDAO();
        Personnel personnel = personnelDAO.getPersonnel(rs.getString("created_by"));
        schoolYear.setCreatedBy(personnel);
        return schoolYear;
    }

    @Override
    public List<SchoolYear> getAll() {
        List<SchoolYear> schoolYears = new ArrayList<SchoolYear>();
        String sql = "select * from schoolYears order by id desc";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                SchoolYear schoolYear = createNewSchoolYear(rs);
                schoolYears.add(schoolYear);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schoolYears;
    }

    @Override
    public SchoolYear getLatest() {
        String sql = "SELECT TOP 1 * FROM SchoolYears ORDER BY ID DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createNewSchoolYear(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String createNewSchoolYear(SchoolYear schoolYear) {
        String sql = "insert into SchoolYears values(?,?,?,?,?,?)";
        try {
            if (getLatest()!=null){
                Date lastEndDate = getLatest().getEndDate();
                if (!schoolYear.getStartDate().after(lastEndDate)) {
                    return "Ngày bắt đầu năm học mới phải sau ngày kết thúc năm học cũ";
                }
            }
            if (validateSchoolYear(schoolYear).equals("success")) {
                PreparedStatement statement = connection.prepareStatement(sql);
                String newSchoolYearId;
                if (getLatest()!=null){
                    newSchoolYearId = generateId(getLatest().getId());
                } else {
                    newSchoolYearId = "SY000001";
                }
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
                IWeekDAO weekDAO = new WeekDAO();
                weekDAO.generateWeeks(getLatest());
            } else {
                return "Thao tác thất bại! " + validateSchoolYear(schoolYear);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return "Thao tác thất bại! " + sqlException.getMessage();
        } catch (Exception e) {
            return "Thao tác thất bại! " + e.getMessage();
        }
        return "success";
    }

    private String validateSchoolYear(SchoolYear schoolYear) {
        if (!schoolYear.getEndDate().after(schoolYear.getStartDate())) {
            return "Ngày kết thúc không thể trước ngày bắt đầu";
        }

        LocalDate startLocalDate = Helper.convertDateToLocalDate(schoolYear.getStartDate());
        LocalDate endLocalDate = Helper.convertDateToLocalDate(schoolYear.getEndDate());

        // Validate that the years are at least 10 months long
        if (ChronoUnit.MONTHS.between(startLocalDate, endLocalDate) < 10) {
            return "Năm học phải kéo dài ít nhất 10 tháng";
        }

        LocalDate todayPlus7 = LocalDate.now().plusDays(7);
        if (!startLocalDate.isAfter(todayPlus7)) {
            return "Năm học phải được tạo trước khi bắt đầu ít nhất 7 ngày";
        }
        return "success";
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

    @Override
    public SchoolYear getSchoolYear(String id) {
        String sql = "select * from schoolYears where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                SchoolYear schoolYear = createNewSchoolYear(rs);
                return schoolYear;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public SchoolYear getSchoolYearByDate(Date date) {
        String sql = "SELECT * FROM SchoolYears WHERE ? BETWEEN start_date AND end_date";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                SchoolYear schoolYear = new SchoolYear();
                schoolYear.setId(resultSet.getString("id"));
                schoolYear.setName(resultSet.getString("name"));
                schoolYear.setStartDate(resultSet.getDate("start_date"));
                schoolYear.setEndDate(resultSet.getDate("end_date"));
                schoolYear.setDescription(resultSet.getString("description"));

                return schoolYear;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<SchoolYear> getFutureSchoolYears() {
        String sql = "select * from schoolYears where start_date > CAST(GETDATE() AS DATE)";
        List<SchoolYear> schoolYears = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                SchoolYear schoolYear = createNewSchoolYear(resultSet);
                schoolYears.add(schoolYear);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return schoolYears;
    }


    public SchoolYear getClosestSchoolYears() {
        String sql = "select top 1  * from schoolYears where end_date >= CAST(GETDATE() AS DATE) order by start_date";

        SchoolYear schoolYear = new SchoolYear();
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                schoolYear = createNewSchoolYear(resultSet);

            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return schoolYear;
    }
    public List<SchoolYear> getListSchoolYearsByPupilID(String id) {
        List<SchoolYear> schoolYears = new ArrayList<>();
        String sql = "select sy.* from Pupils p join classDetails cd on p.id = cd.pupil_id\n" +
                "JOIN dbo.Class C on C.id = cd.class_id\n" +
                "join dbo.SchoolYears SY on C.school_year_id = SY.id\n" +
                "where p.id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                SchoolYear schoolYear = new SchoolYear();
                schoolYear.setId(resultSet.getString("id"));
                schoolYear.setName(resultSet.getString("name"));
                schoolYear.setStartDate(resultSet.getDate("start_date"));
                schoolYear.setEndDate(resultSet.getDate("end_date"));
                schoolYear.setDescription(resultSet.getString("description"));
                schoolYears.add(schoolYear);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return schoolYears;
    }
    public boolean checkPupilInClassOfSchoolYear(String pupil_id, String school_year_id) {
        String sql = "select * from Pupils p join classDetails cd on p.id = cd.pupil_id\n" +
                "join dbo.Class C on cd.class_id = C.id\n" +
                "where p.id =? and c.school_year_id =?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, pupil_id);
            statement.setString(2, school_year_id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String editSchoolYear(SchoolYear schoolYear) {
        SchoolYear oldSchoolYear = getSchoolYear(schoolYear.getId());
        if (!new Date().before(oldSchoolYear.getStartDate())) {
            return "Năm học chỉ có thể được chỉnh sửa trước khi bắt đầu";
        }
        String currentId = schoolYear.getId();
        int numericalPart = Integer.parseInt(currentId.substring(2));
        String previousId = "SY" + String.format("%06d", numericalPart - 1);

        if (getSchoolYear(previousId) != null) {
            Date lastEndDate = getSchoolYear(previousId).getEndDate();
            if (!schoolYear.getStartDate().after(lastEndDate)) {
                return "Ngày bắt đầu năm học mới phải sau ngày kết thúc năm học cũ";
            }
        }
        if (validateSchoolYear(schoolYear).equals("success")){
            String sql = "DELETE FROM Days WHERE week_id IN (SELECT id FROM Weeks\n" +
                    "                                    WHERE school_year_id = ?\n" +
                    "                                    ); DELETE FROM Weeks WHERE school_year_id = ?; DELETE FROM SchoolYears where id = ?;";
            try{
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, schoolYear.getId());
                statement.setString(2, schoolYear.getId());
                statement.setString(3, schoolYear.getId());
                statement.executeUpdate();
            }catch (Exception e){
                e.printStackTrace();
                return "Thao tác thất bại";
            }
        }else {
            return "Thao tác thất bại! " + validateSchoolYear(schoolYear);
        }
        return updateSchoolYear(schoolYear);
    }

    @Override
    public List<SchoolYear> getSchoolYears() {
        String sql = "select * from schoolYears where end_date > CAST(GETDATE() AS DATE)";
        List<SchoolYear> schoolYears = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                SchoolYear schoolYear = createNewSchoolYear(resultSet);
                schoolYears.add(schoolYear);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return schoolYears;
    }

    private String updateSchoolYear(SchoolYear schoolYear){
        String sql = "insert into SchoolYears values(?,?,?,?,?,?)";
        try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, schoolYear.getId());
                statement.setString(2, schoolYear.getName());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String sqlStartDate = dateFormat.format(schoolYear.getStartDate());
                statement.setString(3, sqlStartDate);
                String sqlEndDate = dateFormat.format(schoolYear.getEndDate());
                statement.setString(4, sqlEndDate);
                statement.setString(5, schoolYear.getDescription());
                statement.setString(6, schoolYear.getCreatedBy().getId());
                statement.execute();
                IWeekDAO weekDAO = new WeekDAO();
                weekDAO.generateWeeks(getLatest());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return "Thao tác thất bại! " + sqlException.getMessage();
        } catch (Exception e) {
            return "Thao tác thất bại! " + e.getMessage();
        }
        return "success";
    }

}
