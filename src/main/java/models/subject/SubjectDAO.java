/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.subject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.grade.Grade;
import utils.DBContext;

/**
 *
 * @author Admin
 */
public class SubjectDAO extends DBContext {

    public List<Subject> getAll() {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT s.id AS subject_id, s.name AS subject_name, g.id AS grade_id, g.name AS grade_name, s.description "
                + "FROM Subjects s "
                + "JOIN Grades g ON s.grade_id = g.id";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getString("subject_id"));
                subject.setName(rs.getString("subject_name"));

                Grade grade = new Grade();
                grade.setId(rs.getString("grade_id"));
                grade.setName(rs.getString("grade_name"));

                subject.setGrade(grade);
                subject.setDescription(rs.getString("description"));
                subjects.add(subject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subjects;
    }

    public List<Subject> getSubjectsByGradeId(String gradeId) {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT s.id AS subject_id, s.name AS subject_name, g.id AS grade_id, g.name AS grade_name, s.description "
                + "FROM Subjects s "
                + "JOIN Grades g ON s.grade_id = g.id "
                + "WHERE g.id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, gradeId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getString("subject_id"));
                subject.setName(rs.getString("subject_name"));

                Grade grade = new Grade();
                grade.setId(rs.getString("grade_id"));
                grade.setName(rs.getString("grade_name"));

            subject.setGrade(grade);
            subject.setDescription(rs.getString("description"));
            subjects.add(subject);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
        return subjects;
    }

    
}
