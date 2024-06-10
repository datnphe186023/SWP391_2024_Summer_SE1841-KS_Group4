/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.subject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.grade.Grade;
import models.grade.GradeDAO;
import utils.DBContext;

/**
 *
 * @author Admin
 */
public class SubjectDAO extends DBContext {
    public Subject createSubject(ResultSet resultSet) throws SQLException{
        GradeDAO gradeDAO = new GradeDAO();
        Subject subject = new Subject();
        subject.setId(resultSet.getString("id"));
        subject.setName(resultSet.getString("name"));
        Grade grade = gradeDAO.getGrade(resultSet.getString("grade_id"));
        subject.setGrade(grade);
        subject.setStatus(resultSet.getString("status"));
        subject.setDescription(resultSet.getString("description"));
        return subject;
    }
    public boolean checkSubjectExist(String name, String gradeId){
        String sql="select * from Subjects where [name] = ? and grade_id= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,gradeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public boolean updateStatusById(String id, String status){
        String sql="update Subjects set status =? where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,status);
            preparedStatement.setString(2,id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
  public boolean createSubject(Subject subject){
      String sql="INSERT INTO [dbo].[Subjects] VALUES (?,?,?,?,?)";
      try {
          PreparedStatement preparedStatement = connection.prepareStatement(sql);
          preparedStatement.setString(1,generateId(getLastest().getId()));
          preparedStatement.setString(2,subject.getName());
          preparedStatement.setString(3,subject.getGrade().getId());
          preparedStatement.setString(4,subject.getDescription());
          preparedStatement.setString(5,subject.getStatus());
          preparedStatement.executeUpdate();
          return true;
      } catch (SQLException e) {
          System.out.println(e);
      }
      return false;
  }


    public List<Subject> getAll() {
        List<Subject> subjects = new ArrayList<>();
        String sql = "Select * from Subjects";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Subject subject = createSubject(rs);
                subjects.add(subject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subjects;
    }
    public List<Subject> getSubjectsByStatus(String status){
        List<Subject> subjectList = new ArrayList<>();
        String sql="select * from Subjects where status = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,status);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Subject subject = createSubject(resultSet);
                subjectList.add(subject);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subjectList;
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

    public Subject getLastest(){
        String sql="Select top 1 * from Subjects order by id desc";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return createSubject(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public String generateId(String latestId) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(latestId);
        int number = 0;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group()) + 1;
        }
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        String result = decimalFormat.format(number);
        return "S" + result;
    }
}
