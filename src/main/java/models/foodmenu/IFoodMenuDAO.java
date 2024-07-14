package models.foodmenu;

import java.util.List;

public interface IFoodMenuDAO {
    public List<FoodMenu> getAllFoodMenu();
    public FoodMenu getFoodMenu(String id);
    public List<MenuDetail> getMenuDetails(String grade, String week ,String school_year_id,String status);
    public void createMenuDetail(MenuDetail menuDetail);
    public int getTotalID();
    public boolean existsMealTimetableForGradeInCurrentWeek(String gradeId, String dayId) ;
    public List<MenuDetail> getMenuDetailsforCreate(String grade, String week ,String school_year_id );
    String createFoodMenu(FoodMenu foodMenu);
    public List<String> getMenuDetailsforProcess(String schoolyearID);
    public boolean AcceptorDenyMenu(String id, String status);
    public boolean AcceptorDenyFoodMenu(String id, String status);
    String editFoodMenu(FoodMenu foodMenu);
    public List<FoodMenu> getAllFoodMenuDESC(String exception);
    List<FoodMenu> getFoodMenuWithStatus(String status);
}
