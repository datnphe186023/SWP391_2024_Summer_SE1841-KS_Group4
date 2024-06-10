package models.application;

import models.personnel.PersonnelDAO;
import models.schoolYear.SchoolYear;
import utils.DBContext;
import utils.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO extends DBContext {

    private Application createApplication(ResultSet rs) throws SQLException {
        Application app = new Application();
        app.setId(rs.getString("id"));
        app.setProcessedAt(rs.getDate("processed_at"));
        app.setType(getById(rs.getString("application_type")));
        app.setDetails(rs.getString("details"));
        app.setStatus(rs.getString("status"));
        app.setCreatedBy(rs.getString("created_by"));
        app.setCreatedAt(rs.getDate("created_at"));
        PersonnelDAO personnelDAO = new PersonnelDAO();
        app.setProcessedBy(personnelDAO.getPersonnel(rs.getString("processed_by")));
        return app;
    }

    private ApplicationType getById(String id) {
        ApplicationType app = new ApplicationType();
        String sql = "select * from [Application_Types] where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                app.setId(resultSet.getString("id"));
                app.setName(resultSet.getString("name"));
                app.setDescription(resultSet.getString("description"));
                return app;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ApplicationType> getAllApplicationTypes(String role) {
        List<ApplicationType> applicationTypes = new ArrayList<ApplicationType>();
        String sql = "select id, name, description from [Application_Types] where role = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, role);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ApplicationType applicationType = new ApplicationType();
                applicationType.setId(resultSet.getString("id"));
                applicationType.setName(resultSet.getString("name"));
                applicationType.setDescription(resultSet.getString("description"));
                applicationTypes.add(applicationType);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return applicationTypes;
    }


    public List<Application> getBySchoolYear(SchoolYear schoolYear){
        List<Application> applications = new ArrayList<>();
        String sql = "select * from [Applications] where created_at between ? and ?";
        try{
             PreparedStatement statement = connection.prepareStatement(sql);
             statement.setString(1, Helper.convertDateToLocalDate(schoolYear.getStartDate()).toString());
             statement.setString(2, Helper.convertDateToLocalDate(schoolYear.getEndDate()).toString());
             ResultSet resultSet = statement.executeQuery();
             while (resultSet.next()) {
                 Application application = createApplication(resultSet);
                 applications.add(application);
             }
        }catch (Exception e){
            e.printStackTrace();
        }
        return applications;
    }

    public Application getApplicationById(String id){
        Application app = new Application();
        String sql = "select * from [Applications] where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                app = createApplication(resultSet);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return app;
    }
}
