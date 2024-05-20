package models;

import java.util.Date;

public class Week {
    private String id;
    private Date startDate;
    private Date endDate;
    private String schoolYearId;

    public Week(Date startDate, String id, Date endDate, String schoolYearId) {
        this.startDate = startDate;
        this.id = id;
        this.endDate = endDate;
        this.schoolYearId = schoolYearId;
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

    public String getSchoolYearId() {
        return schoolYearId;
    }

    public void setSchoolYearId(String schoolYearId) {
        this.schoolYearId = schoolYearId;
    }
}
