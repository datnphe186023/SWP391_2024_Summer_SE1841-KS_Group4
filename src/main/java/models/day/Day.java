package models.day;

import models.week.Week;

import java.util.Date;

public class Day {
    private String id;
    private Week week;
    private Date date;

    public Day() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Day(String id, Week week, Date date) {
        this.id = id;
        this.week = week;
        this.date = date;
    }
}
