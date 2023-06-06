package bomoncntt.svk62.mssv2051067158.domain.models;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private Invoice invoice;
    private TableLocation tableLocation;
    private Map<Dish, OrderedDish> orderMap;

    public Order(Invoice invoice, TableLocation tableLocation) {
        this.invoice = invoice;
        this.tableLocation = tableLocation;
        this.orderMap = new HashMap<>();
    }


    public double getOrderTotalPrice() {
        double total = 0;
        for (Map.Entry<Dish, OrderedDish> entry : getOrderMap().entrySet()) {
            Dish dish = entry.getKey();
            OrderedDish orderedDish = entry.getValue();
            total += (double)orderedDish.getQuantity() * dish.getPrice();
        }
        return total;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public TableLocation getTableLocation() {
        return tableLocation;
    }

    public void setTableLocation(TableLocation tableLocation) {
        this.tableLocation = tableLocation;
    }

    public Map<Dish, OrderedDish> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(Map<Dish, OrderedDish> orderMap) {
        this.orderMap = orderMap;
    }
}
