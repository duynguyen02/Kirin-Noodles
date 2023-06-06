package bomoncntt.svk62.mssv2051067158.domain.repository;

import java.util.List;

import bomoncntt.svk62.mssv2051067158.domain.models.Invoice;
import bomoncntt.svk62.mssv2051067158.domain.models.Order;
import bomoncntt.svk62.mssv2051067158.domain.models.OrderedDish;

public interface OrderRepository extends Repository{
    boolean addOrder(Order order);
    List<Order> getAllOrders();
    boolean deleteOrder(Order order);
}
