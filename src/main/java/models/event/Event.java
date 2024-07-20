package models.event;

import models.personnel.Personnel;

import java.util.Date;

public class Event {
    private String id;
    private Personnel createdBy;
    private String heading;
    private String details;
    private Date date;

    public Event(String id, Personnel createdBy, String heading, String details, Date date) {
        this.id = id;
        this.createdBy = createdBy;
        this.heading = heading;
        this.details = details;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Event() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Personnel getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Personnel createdBy) {
        this.createdBy = createdBy;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
