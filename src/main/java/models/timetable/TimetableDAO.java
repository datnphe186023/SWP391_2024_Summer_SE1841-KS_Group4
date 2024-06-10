/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.timetable;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import utils.DBContext;

/**
 *
 * @author Admin
 */
public class TimetableDAO extends DBContext{
    public void insertTimetable(String classId, String timeslotId, String dateId, String subjectId,
                                String createdBy, String status, String note, String teacherId) throws SQLException {
        String sql = "INSERT INTO Timetables (class_id, timeslot_id, date_id, subject_id, created_by, status, note, teacher_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, classId);
            statement.setString(2, timeslotId);
            statement.setString(3, dateId);
            statement.setString(4, subjectId);
            statement.setString(5, createdBy);
            statement.setString(6, status);
            statement.setString(7, note);
            statement.setString(8, teacherId);
            statement.executeUpdate();
        }
    }
}
