package bomoncntt.svk62.mssv2051067158.data.local.repository;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bomoncntt.svk62.mssv2051067158.data.local.KirinNoodlesSQLiteHelper;
import bomoncntt.svk62.mssv2051067158.domain.models.Invoice;
import bomoncntt.svk62.mssv2051067158.domain.models.OrderedDish;
import bomoncntt.svk62.mssv2051067158.domain.repository.OrderedDishRepository;

public class OrderedDishRepositoryImpl implements OrderedDishRepository {
    private final SQLiteDatabase database;
    private static OrderedDishRepositoryImpl instance;

    public synchronized static OrderedDishRepositoryImpl getInstance(KirinNoodlesSQLiteHelper sqLiteHelper){
        if(instance == null){
            instance = new OrderedDishRepositoryImpl(sqLiteHelper);
        }
        return instance;
    }

    private OrderedDishRepositoryImpl(KirinNoodlesSQLiteHelper sqLiteHelper){
        this.database = sqLiteHelper.getWritableDatabase();
    }

    @Override
    public boolean addOrderedDish(OrderedDish orderedDish) {
        ContentValues values = new ContentValues();
        values.put("DishID", orderedDish.getDishID());
        values.put("Quantity", orderedDish.getQuantity());
        values.put("Note", orderedDish.getNote());
        values.put("InvoiceID", orderedDish.getInvoiceID());

        long result = database.insert("OrderedDish", null, values);
        return result != -1;
    }

    @SuppressLint("Range")
    @Override
    public List<OrderedDish> getOrderedDishesByInvoiceID(int invoiceID) {
        Cursor cursor = database.query(
                "OrderedDish",
                null,
                "InvoiceID = ?",
                new String[]{String.valueOf(invoiceID)},
                null,
                null,
                null
        );

        List<OrderedDish> orderedDishes = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("DishID"));
            int quantity = cursor.getInt(cursor.getColumnIndex("Quantity"));
            String note = cursor.getString(cursor.getColumnIndex("Note"));

            OrderedDish orderedDish = new OrderedDish(id, quantity, note, invoiceID);
            orderedDishes.add(orderedDish);
        }

        cursor.close();
        return orderedDishes;

    }

    @SuppressLint("Range")
    @Override
    public List<OrderedDish> getAllOrderedDishes() {
        Cursor cursor = database.query(
                "OrderedDish",
                null,
                null,
                null,
                null,
                null,
                null
        );

        List<OrderedDish> orderedDishes = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("DishID"));
            int quantity = cursor.getInt(cursor.getColumnIndex("Quantity"));
            String note = cursor.getString(cursor.getColumnIndex("Note"));
            int invoiceID = cursor.getInt(cursor.getColumnIndex("InvoiceID"));
            OrderedDish orderedDish = new OrderedDish(id, quantity, note, invoiceID);
            orderedDishes.add(orderedDish);
        }

        cursor.close();
        return orderedDishes;
    }

    @Override
    public boolean updateOrderedDish(OrderedDish orderedDish) {
        ContentValues values = new ContentValues();
        values.put("Quantity", orderedDish.getQuantity());
        values.put("Note", orderedDish.getNote());
        values.put("InvoiceID", orderedDish.getInvoiceID());

        int rowsAffected = database.update(
                "OrderedDish",
                values,
                "InvoiceID = ?",
                new String[]{String.valueOf(orderedDish.getInvoiceID())}
        );

        return rowsAffected > 0;
    }

    @Override
    public boolean deleteOrderedDishByInvoiceID(Invoice invoice) {
        int rowsAffected = database.delete(
                "OrderedDish",
                "InvoiceID = ?",
                new String[]{String.valueOf(invoice.getInvoiceID())}
        );

        return rowsAffected > 0;
    }

    @Override
    public boolean isDishExist(int dishId) {
        Cursor cursor = database.query(
                "OrderedDish",
                null,
                "DishID = ?",
                new String[]{String.valueOf(dishId)},
                null,
                null,
                null
        );

        boolean isExists = (cursor.getCount() > 0);
        cursor.close();

        return isExists;
    }
}
