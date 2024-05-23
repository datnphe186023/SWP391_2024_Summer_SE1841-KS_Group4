package models.day;

import java.util.Date;

public class TimeInDay {
    private String id;
    private Day date;
    private String name;

    public TimeInDay(String id, Day date, String name) {
        this.id = id;
        this.date = date;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Day getDate() {
        return date;
    }

    public void setDate(Day date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TimeInDay() {
    }
}
