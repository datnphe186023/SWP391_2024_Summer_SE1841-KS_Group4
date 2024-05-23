package models.foodmenu;

import models.day.TimeInDay;

public class FoodMenu {
    private String id;
    private String foodDetails;
    private TimeInDay timeInDay;
    private Class aClass;
    private String status;

    public FoodMenu(String id, String foodDetails, TimeInDay timeInDay, Class aClass, String status) {
        this.id = id;
        this.foodDetails = foodDetails;
        this.timeInDay = timeInDay;
        this.aClass = aClass;
        this.status = status;
    }

    public FoodMenu() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoodDetails() {
        return foodDetails;
    }

    public void setFoodDetails(String foodDetails) {
        this.foodDetails = foodDetails;
    }

    public TimeInDay getTimeInDay() {
        return timeInDay;
    }

    public void setTimeInDay(TimeInDay timeInDay) {
        this.timeInDay = timeInDay;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
