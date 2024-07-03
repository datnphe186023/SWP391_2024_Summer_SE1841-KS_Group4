package models.evaluation;

import models.pupil.PupilDAO;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HealthCheckUpDAO extends DBContext implements IHealthCheckUpDAO{
    private HealthCheckUp createHealthCheckUp(ResultSet resultSet) throws SQLException {
        PupilDAO pupilDAO = new PupilDAO();
        HealthCheckUp healthCheckUp = new HealthCheckUp(
            resultSet.getString("id"),
            pupilDAO.getPupilsById(resultSet.getString("pupil_id")),
            resultSet.getDate("check_up_date"),
            resultSet.getFloat("height"),
            resultSet.getFloat("weight"),
            resultSet.getString("average_development_stage"),
            resultSet.getString("blood_pressure"),
            resultSet.getString("teeth"),
            resultSet.getString("eyes"),
            resultSet.getString("ear_nose_throat"),
            resultSet.getString("notes")
        );
        return healthCheckUp;
    }
    @Override
    public List<HealthCheckUp> getAllHealthCheckUps() {
        List<HealthCheckUp> healthCheckUps = new ArrayList<>();
        String sql = "SELECT * FROM HealthCheckUps";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                healthCheckUps.add(createHealthCheckUp(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return healthCheckUps;
    }
    
    
   @Override
    public HealthCheckUp getHealthCheckUpsById(String healthId) {
        String sql = "SELECT * FROM HealthCheckUps WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, healthId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createHealthCheckUp(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
