package bomoncntt.svk62.mssv2051067158.data.remote.mapper;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import bomoncntt.svk62.mssv2051067158.domain.models.Dish;
import bomoncntt.svk62.mssv2051067158.utils.Constraints;
import bomoncntt.svk62.mssv2051067158.utils.FileIOHelper;
import bomoncntt.svk62.mssv2051067158.utils.ViewHelper;

public class DishDto {
    @SerializedName("DishID")
    private int dishID;
    @SerializedName("DishName")
    private String dishName;
    @SerializedName("Price")
    private double price;
    @SerializedName("ImageBase64")
    private String imageBase64;

    public DishDto(int dishID, String dishName, double price, String imageBase64) {
        this.dishID = dishID;
        this.dishName = dishName;
        this.price = price;
        this.imageBase64 = imageBase64;
    }

    public static DishDto mapToDto(Dish dish){
        return new DishDto(
                dish.getDishID(),
                dish.getDishName(),
                dish.getPrice(),
                "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAACXBIWXMAAAB2AAAAdgFOeyYIAAAAGXRFWHRTb2Z0d2FyZQB3d3cuaW5rc2NhcGUub3Jnm+48GgAAApRJREFUOI2lk0lME3EUxr9/2+nMtJ3pQhdKa6mAFCwqIjZwMUJcIjEaSTRqjMYDN2NiTAwnTx68GS548WS8GBMhRBK3ixAhRCwiu1WaspdNKJ0O02lnPEmQ1Kjxu30v7/0O730P+E+RXMVApeeeLd9cLWxsJsWUNBoZmL37twD20u265qr60huOArMNAMb6pzfaWrs65yKzN5NJLO0EaLcbnQ41DddrHxUGnKbx/inMTa6gosZPh06UV4z2zfLL84kXOwGa7aak0tdUWOaiuto/YyI8g+HeKACAZins3ueus9u50pwAh4e7VlBiblYVtZw16hE6XgYDR8PiNEDKCMgqWZy6GipVKPXp4Yai97VniweLDjjuAwBhWarmzK2q14GQi+toCcu19X6dVq8jNMWgoyWMgMcJUwmL+GZCOnjSqeQXu1lCgOcPwqO97V+DGlGUM5yV1n18OYlChlBXfBQ5t8uA6JNJvD3dhNbqRtyhj4ATtLTFxbOdDwchp7PIphXh5xLV1fnUeU+x2eaWFFgdVqQkIDK9DnWNQkxIIJ4SMDKzgO6ebwiEvEgsSehpi4yJQvoVsdu8w0WF+4MmkwV6mv3duXNqaOTdsI5hjBnelAez1QGaNsBAaeHngP7pBahEDwAgahrVXheiSQJRzm4BjEZLRpNKfR+QZQmSKAAACngGFyr3YK/Hh2hsCNHYEIIeHy4eCsDDM1vDUlrE+vrSAAFgzLO423ze8qNOh082cXkGQnImHACgqFmsrM6L41/6uuOL0catToqiQgaGP5ZVlMv5Dn+Q1ht+iZmqKJiaG/9E08yzRGL5jSzLH4Acz0TTXIBj+cc0w7KEaESesy9rNBolvhgzJ4S1JknamPinTf9JPwDkVu+RHYtXlAAAAABJRU5ErkJggg=="
        );
    }

    public Dish mapFromDto(Context context){
        FileIOHelper fileIOHelper =  FileIOHelper.getInstance(context);
        Bitmap bitmap = ViewHelper.convertStringToBitmap(imageBase64);

        String imagePath = "";
        if(bitmap != null){
          imagePath = fileIOHelper.copyImageToApplication(bitmap, Constraints.FOOD_IMAGES_DIR).toString();
        }

        return new Dish(
                dishID,
                dishName,
                price,
                imagePath
        );
    }

    public int getDishID() {
        return dishID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
