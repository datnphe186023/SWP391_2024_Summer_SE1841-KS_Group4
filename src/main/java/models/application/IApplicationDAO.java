package models.application;

import models.schoolYear.SchoolYear;

import java.util.List;

public interface IApplicationDAO {
    ApplicationType getById(String id);
    List<ApplicationType> getAllApplicationTypes(String role);
    List<Application> getForPersonnel(SchoolYear schoolYear, String role);
    Application getApplicationById(String id);
    String addApplication(Application application);
    String processApplication(Application application);
    List<Application> getSentApplications(String senderUserId);
}
