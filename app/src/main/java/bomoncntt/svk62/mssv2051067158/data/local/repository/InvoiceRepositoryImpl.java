package bomoncntt.svk62.mssv2051067158.data.local.repository;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bomoncntt.svk62.mssv2051067158.data.local.KirinNoodlesSQLiteHelper;
import bomoncntt.svk62.mssv2051067158.domain.models.Invoice;
import bomoncntt.svk62.mssv2051067158.domain.models.TableLocation;
import bomoncntt.svk62.mssv2051067158.domain.repository.InvoiceRepository;
import bomoncntt.svk62.mssv2051067158.utils.DateHelper;

public class InvoiceRepositoryImpl implements InvoiceRepository {

    private final SQLiteDatabase database;
    private static InvoiceRepositoryImpl instance;

    public synchronized static InvoiceRepositoryImpl getInstance(KirinNoodlesSQLiteHelper sqLiteHelper){
        if(instance == null){
            instance = new InvoiceRepositoryImpl(sqLiteHelper);
        }
        return instance;
    }

    private InvoiceRepositoryImpl(KirinNoodlesSQLiteHelper sqLiteHelper){
        this.database = sqLiteHelper.getWritableDatabase();
    }


    @Override
    public boolean addInvoice(Invoice invoice) {
        ContentValues values = new ContentValues();
        values.put("InvoiceID", invoice.getInvoiceID());
        values.put("Total",invoice.getTotal());
        values.put("OrderTime", invoice.getOrderTime().toString());
        values.put("TableID", invoice.getTableID());
        values.put("PaymentStatus", invoice.getPaymentStatus().getValue());

        long result = database.insert("Invoice", null, values);
        return result != -1;
    }

    @SuppressLint("Range")
    @Override
    public Invoice getInvoiceByID(int invoiceID) {
        Cursor cursor = database.query(
                "Invoice",
                null,
                "InvoiceID = ?",
                new String[]{String.valueOf(invoiceID)},
                null,
                null,
                null
        );

        Invoice invoice = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("InvoiceID"));
            double total = cursor.getDouble(cursor.getColumnIndex("Total"));
            String timeCreated = cursor.getString(cursor.getColumnIndex("OrderTime"));
            int tableID = cursor.getInt(cursor.getColumnIndex("TableID"));
            String paymentStatus = cursor.getString(cursor.getColumnIndex("PaymentStatus"));

            invoice = new Invoice(id, total, DateHelper.stringToDateConverter(timeCreated), tableID, Invoice.PaymentStatus.getEnumByValue(paymentStatus));
        }

        cursor.close();
        return invoice;
    }

    @SuppressLint("Range")
    @Override
    public List<Invoice> getAllInvoices() {
        Cursor cursor = database.query("Invoice", null, null, null, null, null, null);

        List<Invoice> invoices = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("InvoiceID"));
            double total = cursor.getDouble(cursor.getColumnIndex("Total"));
            String timeCreated = cursor.getString(cursor.getColumnIndex("OrderTime"));
            int tableID = cursor.getInt(cursor.getColumnIndex("TableID"));
            String paymentStatus = cursor.getString(cursor.getColumnIndex("PaymentStatus"));
            Invoice invoice = new Invoice(id, total, DateHelper.stringToDateConverter(timeCreated), tableID, Invoice.PaymentStatus.getEnumByValue(paymentStatus));
            System.out.println(invoice.getOrderTime().toString());
            invoices.add(invoice);
        }

        cursor.close();
        return invoices;
    }

    @SuppressLint("Range")
    @Override
    public Invoice getInvoicesByTableID(int tableID) {
        Cursor cursor = database.query(
                "Invoice",
                null,
                "InvoiceID = ?",
                new String[]{String.valueOf(tableID)},
                null,
                null,
                null
        );

        Invoice invoice = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("InvoiceID"));
            double total = cursor.getDouble(cursor.getColumnIndex("Total"));
            String orderTime = cursor.getString(cursor.getColumnIndex("OrderTime"));
            String paymentStatus = cursor.getString(cursor.getColumnIndex("PaymentStatus"));
            invoice = new Invoice(id, total, DateHelper.stringToDateConverter(orderTime), tableID, Invoice.PaymentStatus.getEnumByValue(paymentStatus));
        }

        cursor.close();
        return invoice;
    }

    @Override
    public boolean updateInvoice(Invoice invoice) {
        ContentValues values = new ContentValues();
        values.put("Total", invoice.getTotal());
        values.put("OrderTime", invoice.getOrderTime().toString());
        values.put("TableID", invoice.getTableID());
        values.put("PaymentStatus", invoice.getPaymentStatus().getValue());

        int rowsAffected = database.update(
                "Invoice",
                values,
                "InvoiceID = ?",
                new String[]{String.valueOf(invoice.getInvoiceID())}
        );

        return rowsAffected > 0;
    }

    @Override
    public boolean deleteInvoice(int invoiceID) {
        int rowsAffected = database.delete(
                "Invoice",
                "InvoiceID = ?",
                new String[]{String.valueOf(invoiceID)}
        );

        return rowsAffected > 0;
    }

    @Override
    public int getMaxId() {
        int maxId = -1;
        String query = "SELECT MAX(InvoiceID) FROM Invoice";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            maxId = cursor.getInt(0);
            cursor.close();
        }
        return maxId;
    }

    @Override
    public boolean isTableLocationExist(TableLocation tableLocation) {
        Cursor cursor = database.query(
                "Invoice",
                null,
                "TableID = ?",
                new String[]{String.valueOf(tableLocation.getTableID())},
                null,
                null,
                null
        );

        boolean isExists = (cursor.getCount() > 0);
        cursor.close();

        return isExists;
    }
}
