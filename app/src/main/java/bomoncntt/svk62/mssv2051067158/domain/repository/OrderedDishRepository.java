package bomoncntt.svk62.mssv2051067158.domain.repository;

import java.util.List;

import bomoncntt.svk62.mssv2051067158.domain.models.Invoice;
import bomoncntt.svk62.mssv2051067158.domain.models.OrderedDish;

public interface OrderedDishRepository extends Repository{
    boolean addOrderedDish(OrderedDish orderedDish);
    List<OrderedDish> getOrderedDishesByInvoiceID(int invoiceID);
    List<OrderedDish> getAllOrderedDishes();
    boolean updateOrderedDish(OrderedDish orderedDish);
    boolean deleteOrderedDishByInvoiceID(Invoice invoice);
    boolean isDishExist(int DishId);
}
