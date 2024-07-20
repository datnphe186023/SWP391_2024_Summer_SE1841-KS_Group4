package models.classes;

import models.grade.GradeDAO;
import models.grade.IGradeDAO;
import models.personnel.IPersonnelDAO;
import models.personnel.PersonnelDAO;
import models.schoolYear.ISchoolYearDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import utils.DBContext;
import utils.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassDAO extends DBContext implements IClassDAO {

    private Class createClass(ResultSet resultSet) throws SQLException {
        Class c = new Class();
        c.setId(resultSet.getString("id"));
        c.setName(resultSet.getString("name"));
        IGradeDAO gradeDAO = new GradeDAO();
        c.setGrade(gradeDAO.getGrade(resultSet.getString("grade_id")));
        IPersonnelDAO personnelDAO = new PersonnelDAO();
        c.setTeacher(personnelDAO.getPersonnel(resultSet.getString("teacher_id")));
        ISchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        c.setSchoolYear(schoolYearDAO.getSchoolYear(resultSet.getString("school_year_id")));
        c.setStatus(resultSet.getString("status"));
        c.setCreatedBy(personnelDAO.getPersonnel(resultSet.getString("created_by")));
        return c;
    }

    @Override
    public List<Class> getBySchoolYear(String schoolYearId) {
        List<Class> classes = new ArrayList<>();
        String sql = "select * from Class where school_year_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, schoolYearId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Class c = createClass(resultSet);
                classes.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    @Override
    public Class getClassById(String id) {
        String sql = "select * from [Class] where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createClass(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Class getTeacherClassByYear(String year, String teacherId) {
        String sql = "select * from Class c where teacher_id= ? and school_year_id= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, teacherId);
            preparedStatement.setString(2, year);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createClass(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String createNewClass(Class c) {
        String sql = "insert into [Class] values (?,?,?,?,?,?,?)";
        try {
            if (!isSchoolYearValid(c.getSchoolYear())) {
                return "Lớp phải được tạo trước khi năm học bắt đầu 7 ngày";
            }
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            String newClassId;
            if (getLatest() != null) {
                newClassId = generateId(getLatest().getId());
            } else {
                newClassId = "C000001";
            }
            preparedStatement.setString(1, newClassId);
            if (c.getName().isBlank()) {
                return "Tên lớp không được để trống";
            }
            preparedStatement.setString(2, c.getName());
            preparedStatement.setString(3, c.getGrade().getId());
            if (c.getTeacher() != null) {
                preparedStatement.setString(4, c.getTeacher().getId());
            } else {
                preparedStatement.setNull(4, java.sql.Types.VARCHAR);
            }
            preparedStatement.setString(5, c.getSchoolYear().getId());
            preparedStatement.setString(6, "đang chờ xử lý");
            preparedStatement.setString(7, c.getCreatedBy().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return "Thao tác thất bại. Lớp đã tồn tại";
        } catch (Exception e) {
            e.printStackTrace();
            return "Vui lòng tạo năm học trước khi tạo lớp";
        }
        return "success";
    }

    private boolean isSchoolYearValid(SchoolYear schoolYear) {
        LocalDate currentDate = LocalDate.now();
        LocalDate schoolYearEndDate = Helper.convertDateToLocalDate(schoolYear.getEndDate());
        LocalDate schoolYearStartDate = Helper.convertDateToLocalDate(schoolYear.getStartDate());
        LocalDate todayPlus7 = LocalDate.now().plusDays(7);
        if (schoolYearEndDate.isBefore(currentDate) || !schoolYearStartDate.isAfter(todayPlus7)) {
            return false;
        }
        return true;
    }

    private Class getLatest() {
        String sql = "select TOP 1 * from Class order by id desc";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createClass(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        return "C" + result;
    }

    @Override
    public List<Class> getByStatus(String status, String schoolYearId) {
        String sql = " Select * from Class where [status] = N'" + status + "'  and school_year_id = ?";
        try {
            List<Class> classes = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, schoolYearId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Class c = createClass(resultSet);
                classes.add(c);
            }
            return classes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String reviewClass(String newStatus, String id) {
        StringBuilder sql = new StringBuilder("update [Class] set [status]= ");
        try {

            if (newStatus.equals("accept")) {
                newStatus = "đã được duyệt";
                sql.append("N'").append(newStatus).append("' where [id] = '").append(id).append("'");
            } else {
                newStatus = "không được duyệt";
                sql.append("N'").append(newStatus).append("' , [teacher_id] = ").append("NULL").append(" where [id] = '").append(id).append("'");
            }
            System.out.println(sql.toString());
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return "Có lỗi xả ra khi duyệt, vui lòng thử lại";
        }
        return "success";
    }

    @Override
    public List<Class> getClassByGradeId(String gradeId) {
        List<Class> classes = new ArrayList<>();
        String sql = "SELECT * FROM [BoNo_Kindergarten].[dbo].[Class] WHERE grade_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, gradeId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Class cls = createClass(rs);
                classes.add(cls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    @Override
    public List<Class> getClassByGradeIdAndSchoolYearAndStatus(String gradeId, String schoolYearId, String status) {
        List<Class> classes = new ArrayList<>();
        String sql = "SELECT TOP (1000) [id], [name], [grade_id], [teacher_id], [school_year_id], [status], [created_by] "
                + "FROM [BoNo_Kindergarten].[dbo].[Class] "
                + "WHERE grade_id = ? AND school_year_id = ? AND status = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, gradeId);
            statement.setString(2, schoolYearId);
            statement.setString(3, status);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Class cls = createClass(rs);
                classes.add(cls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    @Override
    public boolean moveOutClassForPupil(String oldClassId, String newClassId, String pupilId) {
        String sql = "update classDetails set class_id = ? where pupil_id= ? and class_id= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newClassId);
            preparedStatement.setString(2, pupilId);
            preparedStatement.setString(3, oldClassId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Class> getClassesByGradeAndSchoolYear(String classId, String gradeId, String schoolYearId) {
        List<Class> list = new ArrayList<>();
        String sql = " select * from class where school_year_id= ? and grade_id= ? and status= N'đã được duyệt'";
        if (classId != null) {
            sql += " and id != '" + classId + "'";
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, schoolYearId);
            preparedStatement.setString(2, gradeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(createClass(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public String assignTeacherToClass(String teacherId, String classId) {
        String sql = "update [Class] set teacher_id = ? where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, teacherId);
            statement.setString(2, classId);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return "Phân công giáo viên vào lớp thất bại! Vui lòng thử lại sau!";
        }
        return "success";
    }

    @Override
    public Class getClassByPupilIdAndSchoolYear(String id, String schoolYear) {
        String sql = "select * from classDetails cd join Class c on cd.class_id= c.id  where pupil_id= ? and c.school_year_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, schoolYear);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createClass(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public String getClassNameByTeacherAndTimetable(String teacherId, String date) {
        String sql = "SELECT DISTINCT c.name\n"
                + "FROM Class c\n"
                + "JOIN Timetables t ON c.id = t.class_id\n"
                + "JOIN Days d ON t.date_id = d.id\n"
                + "WHERE t.teacher_id = ?\n"
                + "  AND ? = d.date and t.status = N'đã được duyệt';\n";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, teacherId);
            preparedStatement.setString(2, date);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
