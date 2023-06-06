package bomoncntt.svk62.mssv2051067158.domain.repository;

import java.util.List;

import bomoncntt.svk62.mssv2051067158.domain.models.Dish;
import bomoncntt.svk62.mssv2051067158.domain.models.Invoice;
import bomoncntt.svk62.mssv2051067158.domain.models.OrderedDish;
import bomoncntt.svk62.mssv2051067158.domain.models.TableLocation;

public interface KirinNoodlesRepository extends Repository{
    boolean addDish(Dish dish);
    Dish getDishByID(int dishID);
    List<Dish> getAllDishes();
    boolean updateDish(Dish dish);
    boolean deleteDish(int dishID);
    boolean addInvoice(Invoice invoice);
    Invoice getInvoiceByID(int invoiceID);
    List<Invoice> getAllInvoices();
    Invoice getInvoicesByTableID(int tableID);
    boolean updateInvoice(Invoice invoice);
    boolean deleteInvoice(int invoiceID);
    boolean addOrderedDish(OrderedDish orderedDish);
    List<OrderedDish> getAllOrderedDishes();
    List<OrderedDish> getOrderedDishesByInvoiceID(int invoiceID);
    boolean updateOrderedDish(OrderedDish orderedDish);
    boolean deleteOrderedDishByInvoiceId(Invoice invoice);
    boolean addTableLocation(TableLocation tableLocation);
    TableLocation getTableLocationByID(int tableLocationID);
    List<TableLocation> getAllTableLocations();
    boolean updateTableLocation(TableLocation tableLocation);
    boolean deleteTableLocation(int tableLocationID);
}
