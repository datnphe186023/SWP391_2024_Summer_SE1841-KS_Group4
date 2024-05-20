package models.day;

public class TimeInDay {
    private String id;
    private String dateId;
    private String name;

    public TimeInDay(String id, String dateId, String name) {
        this.id = id;
        this.dateId = dateId;
        this.name = name;
    }

    public TimeInDay() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateId() {
        return dateId;
    }

    public void setDateId(String dateId) {
        this.dateId = dateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
