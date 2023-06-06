package bomoncntt.svk62.mssv2051067158.data.local.repository.factory;

import android.content.Context;

import bomoncntt.svk62.mssv2051067158.data.local.KirinNoodlesSQLiteHelper;
import bomoncntt.svk62.mssv2051067158.data.local.repository.DishRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.data.local.repository.InvoiceRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.data.local.repository.OrderRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.data.local.repository.OrderedDishRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.data.local.repository.TableLocationRepositoryImpl;
import bomoncntt.svk62.mssv2051067158.domain.repository.DishRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.InvoiceRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.OrderRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.OrderedDishRepository;
import bomoncntt.svk62.mssv2051067158.domain.repository.Repository;
import bomoncntt.svk62.mssv2051067158.domain.repository.TableLocationRepository;

public class LocalRepositoryFactory {
    public enum RepositoryType {
        DISH,
        INVOICE,
        ORDERED_DISH,
        ORDER,
        TABLE_LOCATION
    }

    private static KirinNoodlesSQLiteHelper helper;

    private static DishRepository dishRepository;
    private static InvoiceRepository invoiceRepository;
    private static OrderedDishRepository orderedDishRepository;
    private static OrderRepository orderRepository;
    private static TableLocationRepository tableLocationRepository;

    public synchronized static void init(Context context){
        helper = KirinNoodlesSQLiteHelper.getInstance(context.getApplicationContext());

        dishRepository = DishRepositoryImpl.getInstance(helper);
        invoiceRepository = InvoiceRepositoryImpl.getInstance(helper);
        orderedDishRepository = OrderedDishRepositoryImpl.getInstance(helper);
        orderRepository = OrderRepositoryImpl.getInstance(helper);
        tableLocationRepository = TableLocationRepositoryImpl.getInstance(helper);
    }

    public synchronized static Repository getInstance(RepositoryType repositoryType) {
        if (repositoryType == null) {
            throw new IllegalArgumentException("repositoryType cannot be null.");
        }

        if (helper == null) {
            throw new IllegalStateException("LocalRepositoryFactory has not been initialized. Call init() first.");
        }
        switch (repositoryType){
            case DISH:
                return dishRepository;
            case INVOICE:
                return invoiceRepository;
            case ORDERED_DISH:
                return orderedDishRepository;
            case ORDER:
                return orderRepository;
            case TABLE_LOCATION:
                return tableLocationRepository;
            default:
                return null;
        }
    }


}
