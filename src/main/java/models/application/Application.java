package models.application;

import models.personnel.Personnel;

import java.util.Date;

public class Application {
    private String id;
    private Date date;
    private ApplicationType type;
    private String details;
    private String status;
    private Personnel createdBy;
    private Date created_at;
    private Personnel processed_by;

    public Application() {
    }

    public Application(String id, Date date, ApplicationType type, String details, String status, Personnel createdBy,
                       Date created_at, Personnel processed_by) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.details = details;
        this.status = status;
        this.createdBy = createdBy;
        this.created_at = created_at;
        this.processed_by = processed_by;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Personnel getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Personnel createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Personnel getProcessed_by() {
        return processed_by;
    }

    public void setProcessed_by(Personnel processed_by) {
        this.processed_by = processed_by;
    }

    public ApplicationType getType() {
        return type;
    }

    public void setType(ApplicationType type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
