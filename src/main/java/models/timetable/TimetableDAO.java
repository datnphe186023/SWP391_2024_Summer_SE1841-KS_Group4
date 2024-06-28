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
    public List<TimetableDTO> getListTimetableByStatusAndCreatedBy(String status, String createdBy) {
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
                + "    WHERE t.status = ? AND t.created_by = ? " // Filter by status and createdBy
                + ") AS temp "
                + "JOIN [BoNo_Kindergarten].[dbo].[Weeks] w ON temp.week_id = w.id "
                + "WHERE temp.row_num = 1;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);  // Set status parameter
            statement.setString(2, createdBy);  // Set createdBy parameter
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String classId = resultSet.getString("class_id");
                String weekId = resultSet.getString("week_id");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");
                String createdByResult = resultSet.getString("created_by");
                String statusValue = resultSet.getString("status");
                String note = resultSet.getString("note"); // Lấy giá trị note từ ResultSet
                String teacherId = resultSet.getString("teacher_id");

                IClassDAO classDAO = new ClassDAO();
                IPersonnelDAO personnelDAO = new PersonnelDAO();

                Class classObj = classDAO.getClassById(classId);
                Personnel createdByObj = personnelDAO.getPersonnel(createdByResult);
                Personnel teacherObj = personnelDAO.getPersonnel(teacherId);

                TimetableDTO timetable = new TimetableDTO(classObj, weekId, startDate, endDate, createdByObj, statusValue, note, teacherObj);
                timetables.add(timetable);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving timetables by status and createdBy", e);
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
        String sql = "SELECT status FROM Timetables WHERE class_id = ? AND date_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, classId);
            stmt.setString(2, dayId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String status = rs.getString("status");
                if ("đã được duyệt".equalsIgnoreCase(status) || "đang chờ xử lý".equalsIgnoreCase(status)) {
                    return true; // Timetable exists
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking timetable existence", e);
        }
        return false;
    }

    @Override
    public List<TimetableDTO> getUniqueClassTimetablesWithWeeks() {
        List<TimetableDTO> timetables = new ArrayList<>();
        String sql = "SELECT DISTINCT\n"
                + "    temp.class_id,\n"
                + "    w.id AS week_id,\n"
                + "    w.start_date,\n"
                + "    w.end_date,\n"
                + "    temp.created_by,\n"
                + "    temp.status,\n"
                + "    temp.note,\n" // Thêm trường note vào câu truy vấn SQL
                + "    temp.teacher_id\n"
                + "FROM (\n"
                + "    SELECT\n"
                + "        t.id,\n"
                + "        t.class_id,\n"
                + "        d.week_id,\n"
                + "        t.created_by,\n"
                + "        t.status,\n"
                + "        t.note,\n" // Lấy trường note từ bảng Timetables
                + "        t.teacher_id,\n"
                + "        ROW_NUMBER() OVER (PARTITION BY t.class_id, d.week_id, t.status ORDER BY t.id ) AS row_num\n"
                + "    FROM\n"
                + "        [BoNo_Kindergarten].[dbo].[Timetables] t\n"
                + "    JOIN [BoNo_Kindergarten].[dbo].[Days] d ON t.date_id = d.id\n"
                + ") AS temp\n"
                + "JOIN [BoNo_Kindergarten].[dbo].[Weeks] w ON temp.week_id = w.id\n"
                + "WHERE temp.row_num = 1\n"
                + "AND (NOT EXISTS (\n"
                + "        SELECT 1\n"
                + "        FROM [BoNo_Kindergarten].[dbo].[Timetables] t2\n"
                + "        JOIN [BoNo_Kindergarten].[dbo].[Days] d2 ON t2.date_id = d2.id\n"
                + "        WHERE temp.class_id = t2.class_id\n"
                + "        AND temp.week_id = d2.week_id\n"
                + "        AND temp.status <> t2.status\n"
                + "        AND t2.id < temp.id\n"
                + "    )\n"
                + "    OR temp.row_num = 1);";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String classId = resultSet.getString("class_id");
                String weekId = resultSet.getString("week_id");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");
                String createdBy = resultSet.getString("created_by");
                String status = resultSet.getString("status");
                String note = resultSet.getString("note");  // Lấy giá trị note từ ResultSet
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
    public List<Timetable> getTimetableByClassAndWeek(String classId, String weekId, String status) {
        List<Timetable> timetables = new ArrayList<>();
        String sql = "SELECT t.id AS timetable_id,\n"
                + "       c.id AS class_id,\n"
                + "       ts.id AS timeslot_id,\n"
                + "       d.id AS date_id,\n"
                + "       s.id AS subject_id,\n"
                + "       t.created_by,\n"
                + "       t.status,\n"
                + "       t.note,\n"
                + "       p.id AS teacher_id\n"
                + "FROM Timetables t\n"
                + "JOIN Class c ON t.class_id = c.id\n"
                + "JOIN Timeslots ts ON t.timeslot_id = ts.id\n"
                + "JOIN Days d ON t.date_id = d.id\n"
                + "JOIN Subjects s ON t.subject_id = s.id\n"
                + "JOIN Personnels p ON t.teacher_id = p.id\n"
                + "JOIN Weeks w ON d.week_id = w.id\n"
                + "WHERE c.id = ? \n"
                + "  AND w.id = ?\n"
                + "  AND t.status = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, classId);
            statement.setString(2, weekId);
            statement.setString(3, status);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Fetching data from the result set and creating Timetable object for each row
                String timetableId = resultSet.getString("timetable_id");
                String classIdResult = resultSet.getString("class_id");
                String timeslotId = resultSet.getString("timeslot_id");
                String dateId = resultSet.getString("date_id");
                String subjectId = resultSet.getString("subject_id");
                String createdBy = resultSet.getString("created_by");
                String statusResult = resultSet.getString("status");
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
                Timetable timetable = new Timetable(timetableId, classs, timeslot, day, subject, createdByObj, statusResult, note, teacher);
                timetables.add(timetable);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving timetables by classId and weekId", e);
        }
        return timetables;
    }

    @Override
    public boolean updateTimetableStatus(String classId, String weekId, String newstatus, String note, String oldStatus) {
        String sql = "UPDATE t "
                + "SET t.status = ?, t.note = ? "
                + "FROM [BoNo_Kindergarten].[dbo].[Timetables] t "
                + "JOIN [BoNo_Kindergarten].[dbo].[Days] d ON t.date_id = d.id "
                + "JOIN [BoNo_Kindergarten].[dbo].[Weeks] w ON d.week_id = w.id "
                + "WHERE t.class_id = ? AND w.id = ? AND t.status = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newstatus);
            statement.setString(2, note);
            statement.setString(3, classId);
            statement.setString(4, weekId);
            statement.setString(5, oldStatus); // Optional, if you want to check the current status
            int rowsAffected = statement.executeUpdate();

            // Check if any rows were affected
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating timetable status and note", e);
        }
    }

    @Override
    public List<TimetableDTO> getTimetableByClassAndWeekAndCreateBy(String create_by) {
        List<TimetableDTO> timetables = new ArrayList<>();
        String sql = "SELECT DISTINCT\n"
                + "    temp.class_id,\n"
                + "    w.id AS week_id,\n"
                + "    w.start_date,\n"
                + "    w.end_date,\n"
                + "    temp.created_by,\n"
                + "    temp.status,\n"
                + "    temp.note,\n" // Thêm trường note vào câu truy vấn SQL
                + "    temp.teacher_id\n"
                + "FROM (\n"
                + "    SELECT\n"
                + "        t.id,\n"
                + "        t.class_id,\n"
                + "        d.week_id,\n"
                + "        t.created_by,\n"
                + "        t.status,\n"
                + "        t.note,\n" // Lấy trường note từ bảng Timetables
                + "        t.teacher_id,\n"
                + "        ROW_NUMBER() OVER (PARTITION BY t.class_id, d.week_id, t.status ORDER BY t.id) AS row_num\n"
                + "    FROM\n"
                + "        [BoNo_Kindergarten].[dbo].[Timetables] t\n"
                + "    JOIN [BoNo_Kindergarten].[dbo].[Days] d ON t.date_id = d.id\n"
                + "where t.created_by = ?"
                + ") AS temp\n"
                + "JOIN [BoNo_Kindergarten].[dbo].[Weeks] w ON temp.week_id = w.id\n"
                + "WHERE temp.row_num = 1\n"
                + "AND (NOT EXISTS (\n"
                + "        SELECT 1\n"
                + "        FROM [BoNo_Kindergarten].[dbo].[Timetables] t2\n"
                + "        JOIN [BoNo_Kindergarten].[dbo].[Days] d2 ON t2.date_id = d2.id\n"
                + "        WHERE temp.class_id = t2.class_id\n"
                + "        AND temp.week_id = d2.week_id\n"
                + "        AND temp.status <> t2.status\n"
                + "        AND t2.id < temp.id\n"
                + " )\n"
                + "    OR temp.row_num = 1);";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, create_by);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String classId = resultSet.getString("class_id");
                String weekId = resultSet.getString("week_id");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");
                String createdBy = resultSet.getString("created_by");
                String status = resultSet.getString("status");
                String note = resultSet.getString("note");  // Lấy giá trị note từ ResultSet
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
    public List<TimetableDTO> getTimetableByClassAndWeekAndTeacher(String teacher) {
        List<TimetableDTO> timetables = new ArrayList<>();
        String sql = "SELECT DISTINCT\n"
                + "    temp.class_id,\n"
                + "    w.id AS week_id,\n"
                + "    w.start_date,\n"
                + "    w.end_date,\n"
                + "    temp.created_by,\n"
                + "    temp.status,\n"
                + "    temp.note,\n" // Thêm trường note vào câu truy vấn SQL
                + "    temp.teacher_id\n"
                + "FROM (\n"
                + "    SELECT\n"
                + "        t.id,\n"
                + "        t.class_id,\n"
                + "        d.week_id,\n"
                + "        t.created_by,\n"
                + "        t.status,\n"
                + "        t.note,\n" // Lấy trường note từ bảng Timetables
                + "        t.teacher_id,\n"
                + "        ROW_NUMBER() OVER (PARTITION BY t.class_id, d.week_id, t.status ORDER BY t.id) AS row_num\n"
                + "    FROM\n"
                + "        [BoNo_Kindergarten].[dbo].[Timetables] t\n"
                + "    JOIN [BoNo_Kindergarten].[dbo].[Days] d ON t.date_id = d.id\n"
                + "where t.teacher_id = ?"
                + ") AS temp\n"
                + "JOIN [BoNo_Kindergarten].[dbo].[Weeks] w ON temp.week_id = w.id\n"
                + "WHERE temp.row_num = 1\n"
                + "AND (NOT EXISTS (\n"
                + "        SELECT 1\n"
                + "        FROM [BoNo_Kindergarten].[dbo].[Timetables] t2\n"
                + "        JOIN [BoNo_Kindergarten].[dbo].[Days] d2 ON t2.date_id = d2.id\n"
                + "        WHERE temp.class_id = t2.class_id\n"
                + "        AND temp.week_id = d2.week_id\n"
                + "        AND temp.status <> t2.status\n"
                + "        AND t2.id < temp.id\n"
                + " )\n"
                + "    OR temp.row_num = 1);";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, teacher);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String classId = resultSet.getString("class_id");
                String weekId = resultSet.getString("week_id");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");
                String createdBy = resultSet.getString("created_by");
                String status = resultSet.getString("status");
                String note = resultSet.getString("note");  // Lấy giá trị note từ ResultSet
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
    public void updateTeacherOfTimetable(String classId, String teacherId) {
        String sql = "update Timetables set teacher_id = ? where class_id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, teacherId);
            statement.setString(2, classId);
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
