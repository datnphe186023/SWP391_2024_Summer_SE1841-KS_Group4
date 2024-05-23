package models.classes;

import utils.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClassDAO extends DBContext {
    public List<Class> getAll() {
        List<Class> classes = new ArrayList<Class>();
        String sql = "select * from Class";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Class c = new Class();
                c.setId(resultSet.getString("id"));
                c.setName(resultSet.getString("name"));
//                c.setGradeId(resultSet.getString("grade_id"));
//                c.setSchoolYearId(resultSet.getString("school_year_id"));
                c.setStatus(resultSet.getString("status"));
//                c.setCreatedBy(resultSet.getString("created_by"));
                classes.add(c);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }
}
