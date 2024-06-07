package models.application;

import utils.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO extends DBContext {

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


}
