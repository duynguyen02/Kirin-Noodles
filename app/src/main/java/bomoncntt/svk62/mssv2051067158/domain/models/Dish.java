package bomoncntt.svk62.mssv2051067158.domain.models;

import androidx.annotation.NonNull;

public class Dish {
    private int dishID;
    private String dishName;
    private double price;
    private String imageLocation;


    public Dish(int dishID, String dishName, double price, String imageLocation) {
        this.dishID = dishID;
        this.dishName = dishName;
        this.price = price;
        this.imageLocation = imageLocation;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getDishID() {
        return dishID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    @NonNull
    @Override
    public String toString() {
        return "Dish{" +
                "dishID=" + dishID +
                ", dishName='" + dishName + '\'' +
                ", price=" + price +
                ", imageLocation='" + imageLocation + '\'' +
                '}';
    }
}
