package models.day;

import java.util.Date;

public class Day {
    private String id;
    private String weekId;
    private Date date;

    public Day() {
    }

    public Day(String id, String weekId, Date date) {
        this.id = id;
        this.weekId = weekId;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeekId() {
        return weekId;
    }

    public void setWeekId(String weekId) {
        this.weekId = weekId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
