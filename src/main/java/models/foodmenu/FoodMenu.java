package models.foodmenu;



public class FoodMenu {
    private String id;
    private String foodDetails;

    private String status;

    public FoodMenu(String id, String foodDetails, String status) {
        this.id = id;
        this.foodDetails = foodDetails;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
