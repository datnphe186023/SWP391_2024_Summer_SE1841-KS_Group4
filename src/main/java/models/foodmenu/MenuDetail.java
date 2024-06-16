package models.foodmenu;

import models.day.TimeInDay;
import models.grade.Grade;

public class MenuDetail {
    private String id;
    private FoodMenu foodMenu;
    private Grade grade;
    private TimeInDay timeInDay;
    private String status;
    public MenuDetail() {}

    public MenuDetail(String id, FoodMenu foodMenu, Grade grade, TimeInDay timeInDay, String status) {
        this.id = id;
        this.foodMenu = foodMenu;
        this.grade = grade;
        this.timeInDay = timeInDay;
        this.status = status;

    }
     public String getId() { return id; }

     public void setId(String id) { this.id = id; }

     public FoodMenu getFoodMenu() { return foodMenu; }

     public void setFoodMenu(FoodMenu foodMenu) { this.foodMenu = foodMenu; }

     public Grade getGrade() { return grade; }

     public void setGrade(Grade grade) { this.grade = grade; }

     public void setTimeInDay(TimeInDay timeInDay) { this.timeInDay = timeInDay; }

     public TimeInDay getTimeInDay() { return timeInDay; }


     public String getStatus() { return status; }

     public void setStatus(String status) { this.status = status; }



}
