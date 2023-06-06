package bomoncntt.svk62.mssv2051067158.data.local.repository;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import bomoncntt.svk62.mssv2051067158.data.local.KirinNoodlesSQLiteHelper;
import bomoncntt.svk62.mssv2051067158.domain.models.Dish;
import bomoncntt.svk62.mssv2051067158.domain.models.Invoice;
import bomoncntt.svk62.mssv2051067158.domain.models.Order;
import bomoncntt.svk62.mssv2051067158.domain.models.OrderedDish;
import bomoncntt.svk62.mssv2051067158.domain.models.TableLocation;
import bomoncntt.svk62.mssv2051067158.domain.repository.DishRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.InvoiceRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.OrderRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.OrderedDishRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.TableLocationRepository;

public class OrderRepositoryImpl implements OrderRepository {

    private static OrderRepositoryImpl instance;

    public synchronized static OrderRepositoryImpl getInstance(KirinNoodlesSQLiteHelper helper){
        if(instance == null){
            instance = new OrderRepositoryImpl(helper);
        }
        return instance;
    }

    private final InvoiceRepository invoiceRepository;
    private final OrderedDishRepository orderedDishRepository;
    private final TableLocationRepository tableLocationRepository;
    private final DishRepository dishRepository;

    private OrderRepositoryImpl(KirinNoodlesSQLiteHelper helper){
        invoiceRepository = InvoiceRepositoryImpl.getInstance(helper);
        orderedDishRepository = OrderedDishRepositoryImpl.getInstance(helper);
        tableLocationRepository = TableLocationRepositoryImpl.getInstance(helper);
        dishRepository = DishRepositoryImpl.getInstance(helper);
    }

    @Override
    public boolean addOrder(Order order) {
        TableLocation tableLocation = order.getTableLocation();
        Invoice invoice = order.getInvoice();
        List<OrderedDish> orderedDishes = new ArrayList<>(order.getOrderMap().values());

        if(tableLocation != null){
            invoice.setTableID(order.getTableLocation().getTableID());
        }
        else {
            invoice.setTableID(-1);
        }
        invoiceRepository.addInvoice(invoice);

        for(OrderedDish orderedDish : orderedDishes){
            orderedDish.setInvoiceID(invoice.getInvoiceID());
            orderedDishRepository.addOrderedDish(orderedDish);
        }

        return true;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        List<Invoice> invoices = invoiceRepository.getAllInvoices();


        for (Invoice invoice : invoices){

            TableLocation tableLocation = tableLocationRepository.getTableLocationByID(invoice.getTableID());
            Order order = new Order(invoice, tableLocation);

            List<OrderedDish> orderedDishes = orderedDishRepository.getOrderedDishesByInvoiceID(invoice.getInvoiceID());

            for (OrderedDish orderedDish : orderedDishes){
                Dish dish = dishRepository.getDishByID(orderedDish.getDishID());
                order.getOrderMap().put(dish, orderedDish);
            }

            orders.add(order);

        }

        orders.sort(Comparator.comparing(o -> o.getInvoice().getPaymentStatus()));

        return orders;
    }

    @Override
    public boolean deleteOrder(Order order) {
        Invoice invoice = order.getInvoice();
        invoiceRepository.deleteInvoice(invoice.getInvoiceID());
        orderedDishRepository.deleteOrderedDishByInvoiceID(invoice);

        return true;
    }
}
