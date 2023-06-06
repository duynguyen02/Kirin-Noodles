package bomoncntt.svk62.mssv2051067158.data.remote.mapper;

import com.google.gson.annotations.SerializedName;

import bomoncntt.svk62.mssv2051067158.domain.models.OrderedDish;

public class OrderedDishDto {
    @SerializedName("DishID")
    private int dishID;
    @SerializedName("Quantity")
    private int quantity;
    @SerializedName("Note")
    private String note;
    @SerializedName("InvoiceID")
    private int invoiceID;

    public OrderedDishDto(int dishID, int quantity, String note, int invoiceID) {
        this.dishID = dishID;
        this.quantity = quantity;
        this.note = note;
        this.invoiceID = invoiceID;
    }

    public static OrderedDishDto mapToDto(OrderedDish orderedDish){
        return new OrderedDishDto(
                orderedDish.getDishID(),
                orderedDish.getQuantity(),
                orderedDish.getNote(),
                orderedDish.getInvoiceID()
        );
    }

    public OrderedDish mapFromDto(){
        return new OrderedDish(
                dishID,
                quantity,
                note,
                invoiceID
        );
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
