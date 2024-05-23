package models.schoolYear;
import models.personnel.Personnel;
import models.personnel.PersonnelAttendance;

import java.util.Date;

public class SchoolYear {
    private String id;
    private String name;
    private Date startDate;
    private Date endDate;
    private String description;
    private Personnel createdBy;

    public SchoolYear(String id, String name, Date startDate, Date endDate,
                      String description, Personnel createdBy) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.createdBy = createdBy;
    }

    public SchoolYear() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Personnel getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Personnel createdBy) {
        this.createdBy = createdBy;
    }
}
