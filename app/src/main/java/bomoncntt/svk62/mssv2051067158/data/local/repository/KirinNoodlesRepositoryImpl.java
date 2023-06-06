package bomoncntt.svk62.mssv2051067158.data.local.repository;

import android.content.Context;

import java.util.List;

import bomoncntt.svk62.mssv2051067158.data.local.KirinNoodlesSQLiteHelper;
import bomoncntt.svk62.mssv2051067158.domain.models.Dish;
import bomoncntt.svk62.mssv2051067158.domain.models.Invoice;
import bomoncntt.svk62.mssv2051067158.domain.models.OrderedDish;
import bomoncntt.svk62.mssv2051067158.domain.models.TableLocation;
import bomoncntt.svk62.mssv2051067158.domain.repository.DishRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.InvoiceRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.KirinNoodlesRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.OrderedDishRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.TableLocationRepository;

public class KirinNoodlesRepositoryImpl implements KirinNoodlesRepository {
    private DishRepository dishRepository;
    private InvoiceRepository invoiceRepository;
    private OrderedDishRepository orderedDishRepository;
    private TableLocationRepository tableLocationRepository;
    private static KirinNoodlesRepositoryImpl instance;

    public synchronized static KirinNoodlesRepositoryImpl getInstance(Context context) {
        if (instance == null){
            KirinNoodlesSQLiteHelper helper = KirinNoodlesSQLiteHelper.getInstance(context.getApplicationContext());
            instance = new KirinNoodlesRepositoryImpl(
                    DishRepositoryImpl.getInstance(helper),
                    InvoiceRepositoryImpl.getInstance(helper),
                    OrderedDishRepositoryImpl.getInstance(helper),
                    TableLocationRepositoryImpl.getInstance(helper)
            );
        }
        return instance;
    }


    private KirinNoodlesRepositoryImpl(DishRepository dishRepository, InvoiceRepository invoiceRepository, OrderedDishRepository orderedDishRepository, TableLocationRepository tableLocationRepository) {
        this.dishRepository = dishRepository;
        this.invoiceRepository = invoiceRepository;
        this.orderedDishRepository = orderedDishRepository;
        this.tableLocationRepository = tableLocationRepository;
    }

    @Override
    public boolean addDish(Dish dish) {
        return dishRepository.addDish(dish);
    }

    @Override
    public Dish getDishByID(int dishID) {
        return dishRepository.getDishByID(dishID);
    }

    @Override
    public List<Dish> getAllDishes() {
        return dishRepository.getAllDishes();
    }

    @Override
    public boolean updateDish(Dish dish) {
        return dishRepository.updateDish(dish);
    }

    @Override
    public boolean deleteDish(int dishID) {
        return dishRepository.deleteDish(dishID);
    }

    @Override
    public boolean addInvoice(Invoice invoice) {
        return invoiceRepository.addInvoice(invoice);
    }

    @Override
    public Invoice getInvoiceByID(int invoiceID) {
        return invoiceRepository.getInvoiceByID(invoiceID);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.getAllInvoices();
    }

    @Override
    public Invoice getInvoicesByTableID(int tableID) {
        return invoiceRepository.getInvoicesByTableID(tableID);
    }

    @Override
    public boolean updateInvoice(Invoice invoice) {
        return invoiceRepository.updateInvoice(invoice);
    }

    @Override
    public boolean deleteInvoice(int invoiceID) {
        return invoiceRepository.deleteInvoice(invoiceID);
    }

    @Override
    public boolean addOrderedDish(OrderedDish orderedDish) {
        return orderedDishRepository.addOrderedDish(orderedDish);
    }

    @Override
    public List<OrderedDish> getAllOrderedDishes() {
        return orderedDishRepository.getAllOrderedDishes();
    }

    @Override
    public List<OrderedDish> getOrderedDishesByInvoiceID(int invoiceID) {
        return orderedDishRepository.getOrderedDishesByInvoiceID(invoiceID);
    }

    @Override
    public boolean updateOrderedDish(OrderedDish orderedDish) {
        return orderedDishRepository.updateOrderedDish(orderedDish);
    }

    @Override
    public boolean deleteOrderedDishByInvoiceId(Invoice invoice) {
        return orderedDishRepository.deleteOrderedDishByInvoiceID(invoice);
    }

    @Override
    public boolean addTableLocation(TableLocation tableLocation) {
        return tableLocationRepository.addTableLocation(tableLocation);
    }

    @Override
    public TableLocation getTableLocationByID(int tableLocationID) {
        return tableLocationRepository.getTableLocationByID(tableLocationID);
    }

    @Override
    public List<TableLocation> getAllTableLocations() {
        return tableLocationRepository.getAllTableLocations();
    }

    @Override
    public boolean updateTableLocation(TableLocation tableLocation) {
        return tableLocationRepository.updateTableLocation(tableLocation);
    }

    @Override
    public boolean deleteTableLocation(int tableLocationID) {
        return tableLocationRepository.deleteTableLocation(tableLocationID);
    }
}
