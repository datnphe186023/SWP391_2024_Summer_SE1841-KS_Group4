package models.evaluation;

import java.sql.Date;
import java.util.List;

public interface IHealthCheckUpDAO {
    List<HealthCheckUp> getAllHealthCheckUps();

    HealthCheckUp getHealthCheckUpsById(String healthId);
    List<HealthCheckUp> getHealthCheckUpsByPupilAndSchoolYear(String pupilId);
    boolean addHealthCheckUp(HealthCheckUp healthCheckUp);
    String generateHealthCheckUpId();
    boolean healthCheckUpExists(String pupil_id, Date checkUpDate);
}
