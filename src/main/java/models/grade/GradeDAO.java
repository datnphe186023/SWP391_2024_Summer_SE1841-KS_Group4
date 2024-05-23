package models.grade;

import utils.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GradeDAO extends DBContext {
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
}
