package bomoncntt.svk62.mssv2051067158.domain.models;

import androidx.annotation.NonNull;

import java.util.Date;

public class Invoice {
    private int invoiceID;
    private double total;
    private Date orderTime;
    private int tableID;
    private PaymentStatus paymentStatus;

    public enum PaymentStatus{

        PREPARING("preparing"),
        WAITING_FOR_PAYMENT("waiting_for_payment"),
        PAID("paid"),
        CANCEL("cancel");

        private final String value;

        private PaymentStatus(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static PaymentStatus getEnumByValue(String value){
            for (PaymentStatus paymentStatus : PaymentStatus.values()){
                if(paymentStatus.getValue().equals(value)){
                    return paymentStatus;
                }
            }
            return null;
        }

    }

    public Invoice(int invoiceID, double total, Date orderTime, int tableID, PaymentStatus paymentStatus) {
        this.invoiceID = invoiceID;
        this.total = total;
        this.orderTime = orderTime;
        this.tableID = tableID;
        this.paymentStatus = paymentStatus;
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

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @NonNull
    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceID=" + invoiceID +
                ", orderTime=" + orderTime +
                ", tableID=" + tableID +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}

