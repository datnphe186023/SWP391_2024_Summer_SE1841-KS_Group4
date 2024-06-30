package models.grade;

import utils.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO extends DBContext implements IGradeDAO{
    private Grade createGrade(ResultSet resultSet) throws SQLException {
        Grade grade = new Grade();
        grade.setId(resultSet.getString("id"));
        grade.setName(resultSet.getString("name"));
        grade.setDescription(resultSet.getString("description"));
        return grade;
    }

    @Override
    public Grade getGrade(String gradeId) {
        String sql = "select * from Grades where id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, gradeId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                Grade grade = new Grade();
                grade.setId(resultSet.getString("id"));
                grade.setName(resultSet.getString("name"));
                grade.setDescription(resultSet.getString("description"));
                return grade;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Grade> getAll() {
        String sql = "select * from Grades";
        List<Grade> grades = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Grade grade = createGrade(resultSet);
                grades.add(grade);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return grades;
    }

    public List<Grade> getGradeByUserId(String userId) {
        String sql = "select g.id,g.name,g.description from [User] join dbo.Pupils p on [User].id = P.user_id\n" +
                "                                          join  classDetails cd on p.id = cd.pupil_id\n" +
                "                        join Class c on cd.class_id = c.id\n" +
                "                        join Grades g on c.grade_id = g.id where [User].id  = ?";
        List<Grade> grades = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                Grade grade = createGrade(resultSet);
                grades.add(grade);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return grades;
    }

}
