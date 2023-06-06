package bomoncntt.svk62.mssv2051067158.domain.models;

public class OrderedDish {
    private int dishID;
    private int quantity;
    private String note;
    private int invoiceID;

    public OrderedDish(int dishID, int quantity, String note, int invoiceID) {
        this.dishID = dishID;
        this.quantity = quantity;
        this.note = note;
        this.invoiceID = invoiceID;
    }

    public int getDishID() {
        return dishID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }
}
