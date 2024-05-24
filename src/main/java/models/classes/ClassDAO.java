package models.classes;

import models.grade.GradeDAO;
import models.personnel.PersonnelDAO;
import models.schoolYear.SchoolYear;
import models.schoolYear.SchoolYearDAO;
import utils.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassDAO extends DBContext {
    private Class createClass(ResultSet resultSet) throws SQLException {
        Class c = new Class();
        c.setId(resultSet.getString("id"));
        c.setName(resultSet.getString("name"));
        GradeDAO gradeDAO = new GradeDAO();
        c.setGradeId(gradeDAO.getGrade(resultSet.getString("grade_id")));
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        c.setSchoolYear(schoolYearDAO.getSchoolYear(resultSet.getString("school_year_id")));
        c.setStatus(resultSet.getString("status"));
        PersonnelDAO personnelDAO = new PersonnelDAO();
        c.setCreatedBy(personnelDAO.getPersonnel(resultSet.getString("created_by")));
        return c;
    }

    public List<Class> getAll() {
        List<Class> classes = new ArrayList<Class>();
        String sql = "select * from Class";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Class c = createClass(resultSet);
                classes.add(c);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    public List<Class> getBySchoolYear(String schoolYearId) {
        List<Class> classes = new ArrayList<>();
        String sql = "select * from Class where school_year_id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, schoolYearId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Class c = createClass(resultSet);
                classes.add(c);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    public Class getClassById(String id){
        String sql="select * from Class where id = ?";
        GradeDAO gradeDAO = new GradeDAO();
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        PersonnelDAO personnelDAO = new PersonnelDAO();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Class c = createClass(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  null;
    }

    public Class getTeacherClassByYear(String year, String teacherId){
        String sql = "select class_id from classDetails cd join Class c on cd.class_id= c.id  where teacher_id= ?  and school_year_id= ?";
        ClassDAO classDAO = new ClassDAO();
        String classId="" ;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,teacherId);
            preparedStatement.setString(2,year);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                 classId = resultSet.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Class classes =  classDAO.getClassById(classId);
        return classes;
    }
}
