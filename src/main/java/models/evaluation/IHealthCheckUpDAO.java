package models.evaluation;

import java.util.List;

public interface IHealthCheckUpDAO {
    List<HealthCheckUp> getAllHealthCheckUps();

    HealthCheckUp getHealthCheckUpsById(String healthId);
}
