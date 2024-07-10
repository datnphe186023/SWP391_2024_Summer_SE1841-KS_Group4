package models.evaluation;

import models.pupil.PupilDAO;
import utils.DBContext;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import models.pupil.Pupil;

public class HealthCheckUpDAO extends DBContext implements IHealthCheckUpDAO {

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

    @Override
    public List<HealthCheckUp> getHealthCheckUpsByPupilAndSchoolYear(String pupilId, String schoolYearId) {
        List<HealthCheckUp> healthCheckUps = new ArrayList<>();
        String sql = "SELECT h.* FROM HealthCheckUps h "
                + "JOIN SchoolYears s ON h.check_up_date BETWEEN s.start_date AND s.end_date "
                + "WHERE s.id = ? AND h.pupil_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, schoolYearId);
            preparedStatement.setString(2, pupilId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    healthCheckUps.add(createHealthCheckUp(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return healthCheckUps;
    }

    @Override
    public boolean addHealthCheckUp(HealthCheckUp healthCheckUp) {
        String sql = "INSERT INTO HealthCheckUps (id, pupil_id, check_up_date, height, weight, average_development_stage, blood_pressure, teeth, eyes, ear_nose_throat, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, healthCheckUp.getId());
            preparedStatement.setString(2, healthCheckUp.getPupil().getId());
            preparedStatement.setDate(3, new java.sql.Date(healthCheckUp.getCheckUpDate().getTime()));
            preparedStatement.setFloat(4, healthCheckUp.getHeight());
            preparedStatement.setFloat(5, healthCheckUp.getWeight());
            preparedStatement.setString(6, healthCheckUp.getAverageDevelopmentStage());
            preparedStatement.setString(7, healthCheckUp.getBloodPressure());
            preparedStatement.setString(8, healthCheckUp.getTeeth());
            preparedStatement.setString(9, healthCheckUp.getEyes());
            preparedStatement.setString(10, healthCheckUp.getEarNoseThroat());
            preparedStatement.setString(11, healthCheckUp.getNotes());
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generateHealthCheckUpId() {
        String latestId = getLatestHealthCheckUpId();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(latestId);
        int number = 0;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group()) + 1;
        }
        DecimalFormat decimalFormat = new DecimalFormat("00000");
        String result = decimalFormat.format(number);
        return "HC" + result;
    }

    private String getLatestHealthCheckUpId() {
        String sql = "SELECT TOP 1 id FROM [HealthCheckUps] ORDER BY id DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "HC00000"; // Giá trị mặc định nếu không có bản ghi nào trong bảng
    }

    @Override
    public boolean healthCheckUpExists(String pupil_id, java.sql.Date checkUpDate) {
        String sql = "SELECT COUNT(*) FROM [HealthCheckUps] WHERE pupil_id = ? AND check_up_date = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pupil_id);
            stmt.setDate(2, checkUpDate);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
