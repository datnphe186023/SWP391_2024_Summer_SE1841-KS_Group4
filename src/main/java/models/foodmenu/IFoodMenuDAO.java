package models.foodmenu;

import java.util.List;

public interface IFoodMenuDAO {
    public List<FoodMenu> getAllFoodMenu();
    public FoodMenu getFoodMenu(String id);
    public List<MenuDetail> getMenuDetails(String grade, String week ,String school_year_id );
    public void createMenuDetail(MenuDetail menuDetail);
    public int getTotalID();
    public boolean existsMealTimetableForGradeInCurrentWeek(String gradeId, String dayId) ;
    public List<MenuDetail> getMenuDetailsforCreate(String grade, String week ,String school_year_id );
}
