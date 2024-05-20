package models.application;

import java.util.Date;

public class Application {
    private String id;
    private Date date;
    private String status;
    private String createdBy;
    private Date created_at;
    private String processed_by;

    public Application() {
    }

    public Application(String id, Date date, String createdBy, String status,
                       Date created_at, String processed_by) {
        this.id = id;
        this.date = date;
        this.createdBy = createdBy;
        this.status = status;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getProcessed_by() {
        return processed_by;
    }

    public void setProcessed_by(String processed_by) {
        this.processed_by = processed_by;
    }
}
