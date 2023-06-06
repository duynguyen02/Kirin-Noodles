package bomoncntt.svk62.mssv2051067158.data.remote.mapper;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KirinNoodlesBackup {

    @SerializedName("Password")
    private String password;
    @SerializedName("Dish")
    private List<DishDto> dishDtos;
    @SerializedName("TableLocation")
    private List<TableLocationDto> tableLocationDtos;
    @SerializedName("Invoice")
    private List<InvoiceDto> invoiceDtos;
    @SerializedName("OrderedDish")
    private List<OrderedDishDto> orderedDishDtos;

    public KirinNoodlesBackup(String password, List<DishDto> dishDtos, List<TableLocationDto> tableLocationDtos, List<InvoiceDto> invoiceDtos, List<OrderedDishDto> orderedDishDtos) {
        this.password = password;
        this.dishDtos = dishDtos;
        this.tableLocationDtos = tableLocationDtos;
        this.invoiceDtos = invoiceDtos;
        this.orderedDishDtos = orderedDishDtos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<DishDto> getDishDtos() {
        return dishDtos;
    }

    public void setDishDtos(List<DishDto> dishDtos) {
        this.dishDtos = dishDtos;
    }

    public List<TableLocationDto> getTableLocationDtos() {
        return tableLocationDtos;
    }

    public void setTableLocationDtos(List<TableLocationDto> tableLocationDtos) {
        this.tableLocationDtos = tableLocationDtos;
    }

    public List<InvoiceDto> getInvoiceDtos() {
        return invoiceDtos;
    }

    public void setInvoiceDtos(List<InvoiceDto> invoiceDtos) {
        this.invoiceDtos = invoiceDtos;
    }

    public List<OrderedDishDto> getOrderedDishDtos() {
        return orderedDishDtos;
    }

    public void setOrderedDishDtos(List<OrderedDishDto> orderedDishDtos) {
        this.orderedDishDtos = orderedDishDtos;
    }
}
