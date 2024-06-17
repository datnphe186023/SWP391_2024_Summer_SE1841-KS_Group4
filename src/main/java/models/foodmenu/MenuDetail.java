package models.foodmenu;

import models.day.Day;
import models.day.TimeInDay;
import models.grade.Grade;
import models.timeslot.Timeslot;

public class MenuDetail {
    private String id;
    private FoodMenu foodMenu;
    private Grade grade;
    private Timeslot timeslot;
    private String status;
    private Day day ;
    public MenuDetail() {}

    public MenuDetail(String id, FoodMenu foodMenu, Grade grade, Timeslot timeslot, String status , Day day) {
        this.id = id;
        this.foodMenu = foodMenu;
        this.grade = grade;
        this.timeslot = timeslot;
        this.status = status;
        this.day = day;

    }
     public String getId() { return id; }

     public void setId(String id) { this.id = id; }

     public FoodMenu getFoodMenu() { return foodMenu; }

     public void setFoodMenu(FoodMenu foodMenu) { this.foodMenu = foodMenu; }

     public Grade getGrade() { return grade; }

     public void setGrade(Grade grade) { this.grade = grade; }

     public Timeslot getTimeslot() { return timeslot; }

    public void setTimeslot(Timeslot timeslot) { this.timeslot = timeslot; }

    public  Day getDay() { return day; }

    public void setDay(Day day) { this.day = day; }


     public String getStatus() { return status; }

     public void setStatus(String status) { this.status = status; }



}
