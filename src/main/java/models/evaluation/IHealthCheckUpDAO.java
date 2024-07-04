package models.evaluation;

import java.util.List;

public interface IHealthCheckUpDAO {
    List<HealthCheckUp> getAllHealthCheckUps();

    HealthCheckUp getHealthCheckUpsById(String healthId);
    List<HealthCheckUp> getHealthCheckUpsByPupilAndSchoolYear(String pupilId, String schoolYearId);
    boolean addHealthCheckUp(HealthCheckUp healthCheckUp);
    String generateHealthCheckUpId();
}
