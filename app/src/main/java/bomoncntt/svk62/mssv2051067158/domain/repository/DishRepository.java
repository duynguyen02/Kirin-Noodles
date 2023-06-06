package bomoncntt.svk62.mssv2051067158.domain.repository;

import java.util.List;

import bomoncntt.svk62.mssv2051067158.domain.models.Dish;

public interface DishRepository {
    boolean addDish(Dish dish);
    Dish getDishByID(int dishID);
    List<Dish> getAllDishes();
    boolean updateDish(Dish dish);
    boolean deleteDish(int dishID);
}
