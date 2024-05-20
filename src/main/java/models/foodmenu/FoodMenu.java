package models.foodmenu;

public class FoodMenu {
    private String id;
    private String foodDetails;
    private String timeInDayId;
    private String classId;
    private String status;

    public FoodMenu(String id, String foodDetails, String timeInDayId,
                    String classId, String status) {
        this.id = id;
        this.foodDetails = foodDetails;
        this.timeInDayId = timeInDayId;
        this.classId = classId;
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

    public String getTimeInDayId() {
        return timeInDayId;
    }

    public void setTimeInDayId(String timeInDayId) {
        this.timeInDayId = timeInDayId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
