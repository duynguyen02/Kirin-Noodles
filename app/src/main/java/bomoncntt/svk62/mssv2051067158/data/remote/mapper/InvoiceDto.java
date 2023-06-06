package bomoncntt.svk62.mssv2051067158.data.remote.mapper;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import bomoncntt.svk62.mssv2051067158.domain.models.Invoice;
import bomoncntt.svk62.mssv2051067158.utils.DateHelper;

public class InvoiceDto {
    @SerializedName("InvoiceID")
    private int invoiceID;
    @SerializedName("Total")
    private double total;
    @SerializedName("OrderTime")
    private String orderTime;
    @SerializedName("TableID")
    private int tableID;
    @SerializedName("PaymentStatus")
    private String paymentStatus;


    public InvoiceDto(int invoiceID, double total, String orderTime, int tableID, String paymentStatus) {
        this.invoiceID = invoiceID;
        this.total = total;
        this.orderTime = orderTime;
        this.tableID = tableID;
        this.paymentStatus = paymentStatus;
    }

    public static InvoiceDto mapToDto(Invoice invoice){
        return new InvoiceDto(
                invoice.getInvoiceID(),
                invoice.getTotal(),
                invoice.getOrderTime().toString(),
                invoice.getTableID(),
                invoice.getPaymentStatus().getValue()
        );
    }

    public Invoice mapFromDto(){
        return new Invoice(
                invoiceID,
                total,
                DateHelper.stringToDateConverter(orderTime),
                tableID,
                Invoice.PaymentStatus.getEnumByValue(paymentStatus)
        );
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
