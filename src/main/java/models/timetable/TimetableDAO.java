/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.timetable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.classes.ClassDAO;
import models.classes.Class;
import models.day.Day;
import models.day.DayDAO;
import models.personnel.Personnel;
import models.personnel.PersonnelDAO;
import models.subject.Subject;
import models.subject.SubjectDAO;
import models.timeslot.Timeslot;
import models.timeslot.TimeslotDAO;
import utils.DBContext;

/**
 *
 * @author Admin
 */
public class TimetableDAO extends DBContext {

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

    public List<Timetable> getAllTimetable() throws SQLException {
        List<Timetable> timetables = new ArrayList<>();
        String sql = "SELECT * FROM Timetables";
        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {

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
                ClassDAO classDAO = new ClassDAO();
                TimeslotDAO slotDAO = new TimeslotDAO();
                DayDAO dayDAO = new DayDAO();
                SubjectDAO subDAO = new SubjectDAO();
                PersonnelDAO personnelDAO = new PersonnelDAO();
                // Assuming you have DAO methods to fetch Class, Timeslot, Day, Subject, and Personnel by ID
                Class classs = classDAO.getClassById(classId);
                Timeslot timeslot = slotDAO.getTimeslotById(timeslotId);
                Day day = dayDAO.getDayByID(dateId);
                Subject subject = subDAO.getSubjectBySubjectId(subjectId);
                Personnel createdBy = personnelDAO.getPersonnel(createdById);
                Personnel teacher = personnelDAO.getPersonnel(teacherId);

                Timetable timetable = new Timetable(id, classs, timeslot, day, subject, createdBy, status, note, teacher);
                timetables.add(timetable);
            }
        }
        return timetables;
    }
}
