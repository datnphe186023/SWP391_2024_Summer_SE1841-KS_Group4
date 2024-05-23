package models.event;

import models.personnel.Personnel;

public class Event {
    private String id;
    private Personnel createdBy;
    private String heading;
    private String details;

    public Event(String id, Personnel createdBy, String heading, String details) {
        this.id = id;
        this.createdBy = createdBy;
        this.heading = heading;
        this.details = details;
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
