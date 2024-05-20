package models.event;

public class Event {
    private String id;
    private String createdBy;
    private String heading;
    private String details;

    public Event(String id, String createdBy, String heading, String details) {
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
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
