/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.timetable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import models.classes.ClassDAO;
import models.classes.Class;
import models.classes.IClassDAO;
import models.day.Day;
import models.day.DayDAO;
import models.day.IDayDAO;
import models.personnel.IPersonnelDAO;
import models.personnel.Personnel;
import models.personnel.PersonnelDAO;
import models.subject.ISubjectDAO;
import models.subject.Subject;
import models.subject.SubjectDAO;
import models.timeslot.ITimeslotDAO;
import models.timeslot.Timeslot;
import models.timeslot.TimeslotDAO;
import utils.DBContext;

/**
 *
 * @author Admin
 */
public class TimetableDAO extends DBContext implements ITimetableDAO {

    private Timetable createTimetable(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        String classId = resultSet.getString("class_id");
        String timeslotId = resultSet.getString("timeslot_id");
        String dateId = resultSet.getString("date_id");
        String subjectId = resultSet.getString("subject_id");
        String createdById = resultSet.getString("created_by");
        String status = resultSet.getString("status");
        String note = resultSet.getString("note");
        String teacherId = resultSet.getString("teacher_id");

        ClassDAO classDAO = new ClassDAO();
        TimeslotDAO slotDAO = new TimeslotDAO();
        DayDAO dayDAO = new DayDAO();
        SubjectDAO subDAO = new SubjectDAO();
        PersonnelDAO personnelDAO = new PersonnelDAO();

        Class classs = classDAO.getClassById(classId);
        Timeslot timeslot = slotDAO.getTimeslotById(timeslotId);
        Day day = dayDAO.getDayByID(dateId);
        Subject subject = subDAO.getSubjectBySubjectId(subjectId);
        Personnel createdBy = personnelDAO.getPersonnel(createdById);
        Personnel teacher = personnelDAO.getPersonnel(teacherId);

        return new Timetable(id, classs, timeslot, day, subject, createdBy, status, note, teacher);
    }

    @Override
    public List<Timetable> getAllTimetable() {
        List<Timetable> timetables = new ArrayList<>();
        String sql = "SELECT * FROM Timetables";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            // Create DAO objects outside the loop to avoid unnecessary object creation
            IClassDAO classDAO = new ClassDAO();
            ITimeslotDAO timeslotDAO = new TimeslotDAO();
            IDayDAO dayDAO = new DayDAO();
            ISubjectDAO subjectDAO = new SubjectDAO();
            IPersonnelDAO personnelDAO = new PersonnelDAO();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String classId = resultSet.getString("class_id");
                String timeslotId = resultSet.getString("timeslot_id");
                String dateId = resultSet.getString("date_id");
                String subjectId = resultSet.getString("subject_id");
                String createdById = resultSet.getString("created_by");
                String status = resultSet.getString("status");
                String note = resultSet.getString("note");
                String teacherId = resultSet.getString("teacher_id");

                // Fetch related entities using DAOs
                Class classs = classDAO.getClassById(classId);
                Timeslot timeslot = timeslotDAO.getTimeslotById(timeslotId);
                Day day = dayDAO.getDayByID(dateId);
                Subject subject = subjectDAO.getSubjectBySubjectId(subjectId);
                Personnel createdBy = personnelDAO.getPersonnel(createdById);
                Personnel teacher = personnelDAO.getPersonnel(teacherId);

                // Create Timetable object
                Timetable timetable = new Timetable(id, classs, timeslot, day, subject, createdBy, status, note, teacher);
                timetables.add(timetable);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close the resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Handle or log the exception
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Handle or log the exception
                }
            }
        }
        return timetables;
    }

    @Override
    public List<TimetableDTO> getListTimetableByStatus(String status) {
        List<TimetableDTO> timetables = new ArrayList<>();
        String sql = "SELECT DISTINCT "
                + "    temp.class_id, "
                + "    w.id AS week_id, "
                + "    w.start_date, "
                + "    w.end_date, "
                + "    temp.created_by, "
                + "    temp.status, "
                + "    temp.note, " // Thêm trường note vào truy vấn SQL
                + "    temp.teacher_id "
                + "FROM ( "
                + "    SELECT "
                + "        t.class_id, "
                + "        d.week_id, "
                + "        t.created_by, "
                + "        t.status, "
                + "        t.teacher_id, "
                + "        t.note, " // Thêm trường note
                + "        ROW_NUMBER() OVER (PARTITION BY t.class_id ORDER BY t.id) AS row_num "
                + "    FROM "
                + "        [BoNo_Kindergarten].[dbo].[Timetables] t "
                + "    JOIN [BoNo_Kindergarten].[dbo].[Days] d ON t.date_id = d.id "
                + "    WHERE t.status = ? " // Filter by status
                + ") AS temp "
                + "JOIN [BoNo_Kindergarten].[dbo].[Weeks] w ON temp.week_id = w.id "
                + "WHERE temp.row_num = 1;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);  // Set status parameter
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String classId = resultSet.getString("class_id");
                String weekId = resultSet.getString("week_id");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");
                String createdBy = resultSet.getString("created_by");
                String statusValue = resultSet.getString("status");
                String note = resultSet.getString("note"); // Lấy giá trị note từ ResultSet
                String teacherId = resultSet.getString("teacher_id");

                IClassDAO classDAO = new ClassDAO();
                IPersonnelDAO personnelDAO = new PersonnelDAO();

                Class classObj = classDAO.getClassById(classId);
                Personnel createdByObj = personnelDAO.getPersonnel(createdBy);
                Personnel teacherObj = personnelDAO.getPersonnel(teacherId);

                TimetableDTO timetable = new TimetableDTO(classObj, weekId, startDate, endDate, createdByObj, statusValue, note, teacherObj);
                timetables.add(timetable);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving timetables by status", e);
        }
        return timetables;
    }

    @Override
    public void createTimetable(Timetable timetable) {
        String sql = "INSERT INTO Timetables "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, timetable.getId());
            statement.setString(2, timetable.getaClass().getId());
            statement.setString(3, timetable.getTimeslot().getId());
            statement.setString(4, timetable.getDay().getId());
            statement.setString(5, timetable.getSubject().getId());
            statement.setString(6, timetable.getCreatedBy().getId());
            statement.setString(7, timetable.getStatus());
            statement.setString(8, timetable.getNote());
            statement.setString(9, timetable.getTeacher().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting timetable", e);
        }
    }

    @Override
    public boolean existsTimetableForClassInCurrentWeek(String classId, String dayId) {
        String sql = "SELECT COUNT(*) FROM Timetables WHERE class_id = ? and date_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, classId);
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

    @Override
    public List<TimetableDTO> getUniqueClassTimetablesWithWeeks() {
        List<TimetableDTO> timetables = new ArrayList<>();
        String sql = "SELECT DISTINCT "
                + "    temp.class_id, "
                + "    w.id AS week_id, "
                + "    w.start_date, "
                + "    w.end_date, "
                + "    temp.created_by, "
                + "    temp.status, "
                + "    temp.note, " // Thêm trường note vào truy vấn SQL
                + "    temp.teacher_id "
                + "FROM ( "
                + "    SELECT "
                + "        t.class_id, "
                + "        d.week_id, "
                + "        t.created_by, "
                + "        t.status, "
                + "        t.teacher_id, "
                + "        t.note, " // Thêm trường note
                + "        ROW_NUMBER() OVER (PARTITION BY t.class_id ORDER BY t.id) AS row_num "
                + "    FROM "
                + "        [BoNo_Kindergarten].[dbo].[Timetables] t "
                + "    JOIN [BoNo_Kindergarten].[dbo].[Days] d ON t.date_id = d.id "
                + ") AS temp "
                + "JOIN [BoNo_Kindergarten].[dbo].[Weeks] w ON temp.week_id = w.id "
                + "WHERE temp.row_num = 1;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String classId = resultSet.getString("class_id");
                String weekId = resultSet.getString("week_id");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");
                String createdBy = resultSet.getString("created_by");
                String status = resultSet.getString("status");
                String note = resultSet.getString("note"); // Lấy giá trị note từ ResultSet
                String teacherId = resultSet.getString("teacher_id");

                IClassDAO classDAO = new ClassDAO();
                IPersonnelDAO personnelDAO = new PersonnelDAO();

                Class classObj = classDAO.getClassById(classId);
                Personnel createdByObj = personnelDAO.getPersonnel(createdBy);
                Personnel teacherObj = personnelDAO.getPersonnel(teacherId);

                TimetableDTO timetable = new TimetableDTO(classObj, weekId, startDate, endDate, createdByObj, status, note, teacherObj);
                timetables.add(timetable);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving unique class timetables", e);
        }
        return timetables;
    }

    @Override
    public String generateTimetableId() {
        String latestId = getLatestTimetableId();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(latestId);
        int number = 0;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group()) + 1;
        }
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        String result = decimalFormat.format(number);
        return "TB" + result;
    }

    // Thêm hàm getLatestTimetableId
    private String getLatestTimetableId() {
        String sql = "SELECT TOP 1 id FROM Timetables ORDER BY id DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "TB000000"; // Giá trị mặc định nếu không có bản ghi nào trong bảng
    }

    
    
    @Override
    public List<Timetable> getTimetableByClassAndWeek(String classId, String weekId) {
        List<Timetable> timetables = new ArrayList<>();
        String sql = "SELECT t.id AS timetable_id, "
                + "       c.id AS class_id, "
                + "       ts.id AS timeslot_id, "
                + "       d.id AS date_id, "
                + "       s.id AS subject_id, "
                + "       t.created_by, "
                + "       t.status, "
                + "       t.note, "
                + "       p.id AS teacher_id "
                + "FROM Timetables t "
                + "JOIN Class c ON t.class_id = c.id "
                + "JOIN Timeslots ts ON t.timeslot_id = ts.id "
                + "JOIN Days d ON t.date_id = d.id "
                + "JOIN Subjects s ON t.subject_id = s.id "
                + "JOIN Personnels p ON t.teacher_id = p.id "
                + "JOIN Weeks w ON d.week_id = w.id "
                + "WHERE c.id = ? AND w.id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, classId);
            statement.setString(2, weekId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Fetching data from the result set and creating Timetable object for each row
                String timetableId = resultSet.getString("timetable_id");
                String classIdResult = resultSet.getString("class_id");
                String timeslotId = resultSet.getString("timeslot_id");
                String dateId = resultSet.getString("date_id");
                String subjectId = resultSet.getString("subject_id");
                String createdBy = resultSet.getString("created_by");
                String status = resultSet.getString("status");
                String note = resultSet.getString("note");
                String teacherId = resultSet.getString("teacher_id");

                // Fetch related entities using DAOs
                IClassDAO classDAO = new ClassDAO();
                ITimeslotDAO timeslotDAO = new TimeslotDAO();
                IDayDAO dayDAO = new DayDAO();
                ISubjectDAO subjectDAO = new SubjectDAO();
                IPersonnelDAO personnelDAO = new PersonnelDAO();

                Class classs = classDAO.getClassById(classIdResult);
                Timeslot timeslot = timeslotDAO.getTimeslotById(timeslotId);
                Day day = dayDAO.getDayByID(dateId);
                Subject subject = subjectDAO.getSubjectBySubjectId(subjectId);
                Personnel createdByObj = personnelDAO.getPersonnel(createdBy);
                Personnel teacher = personnelDAO.getPersonnel(teacherId);

                // Create Timetable object
                Timetable timetable = new Timetable(timetableId, classs, timeslot, day, subject, createdByObj, status, note, teacher);
                timetables.add(timetable);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving timetables by classId and weekId", e);
        }
        return timetables;
    }

}
