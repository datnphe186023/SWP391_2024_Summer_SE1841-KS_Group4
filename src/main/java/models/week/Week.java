package models.week;

import models.schoolYear.SchoolYear;

import java.util.Date;

public class Week {
    private String id;
    private Date startDate;
    private Date endDate;
    private SchoolYear schoolYear;

    public Week(Date startDate, String id, Date endDate, SchoolYear schoolYear) {
        this.startDate = startDate;
        this.id = id;
        this.endDate = endDate;
        this.schoolYear = schoolYear;
    }

    public Week() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
    }
}
