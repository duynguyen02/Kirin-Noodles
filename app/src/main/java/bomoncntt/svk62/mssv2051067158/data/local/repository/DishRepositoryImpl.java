package bomoncntt.svk62.mssv2051067158.data.local.repository;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


import bomoncntt.svk62.mssv2051067158.data.local.KirinNoodlesSQLiteHelper;
import bomoncntt.svk62.mssv2051067158.domain.models.Dish;
import bomoncntt.svk62.mssv2051067158.domain.repository.DishRepository;

public class DishRepositoryImpl implements DishRepository {

    private final SQLiteDatabase database;

    private static DishRepositoryImpl instance;

    public synchronized static DishRepositoryImpl getInstance(KirinNoodlesSQLiteHelper sqLiteHelper){
        if (instance == null) {
            instance = new DishRepositoryImpl(sqLiteHelper);
        }
        return instance;
    }

    private DishRepositoryImpl(KirinNoodlesSQLiteHelper sqLiteHelper){
        this.database = sqLiteHelper.getWritableDatabase();
    }

    @Override
    public boolean addDish(Dish dish) {
        ContentValues values = new ContentValues();
        values.put("DishName", dish.getDishName());
        values.put("Price", dish.getPrice());
        values.put("ImageLocation", dish.getImageLocation());

        return database.insert("Dish", null, values) != -1;
    }

    @SuppressLint("Range")
    @Override
    public Dish getDishByID(int dishID) {
        Cursor cursor = database.query("Dish", null, "DishID=?", new String[]{String.valueOf(dishID)}, null, null, null);
        Dish dish = null;

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("DishID"));
            String name = cursor.getString(cursor.getColumnIndex("DishName"));
            double price = cursor.getDouble(cursor.getColumnIndex("Price"));
            String imageLocation = cursor.getString(cursor.getColumnIndex("ImageLocation"));
            dish = new Dish(id, name, price, imageLocation);
        }

        cursor.close();
        return dish;
    }

    @SuppressLint("Range")
    @Override
    public List<Dish> getAllDishes() {
        List<Dish> dishes = new ArrayList<>();
        Cursor cursor = database.query("Dish", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("DishID"));
                String name = cursor.getString(cursor.getColumnIndex("DishName"));
                double price = cursor.getDouble(cursor.getColumnIndex("Price"));
                String imageLocation = cursor.getString(cursor.getColumnIndex("ImageLocation"));
                Dish dish = new Dish(id, name, price, imageLocation);
                dishes.add(dish);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return dishes;
    }

    @Override
    public boolean updateDish(Dish dish) {
        ContentValues values = new ContentValues();
        values.put("DishName", dish.getDishName());
        values.put("Price", dish.getPrice());
        values.put("ImageLocation", dish.getImageLocation());

        return database.update("Dish", values, "DishID=?", new String[]{String.valueOf(dish.getDishID())}) > 0;
    }

    @Override
    public boolean deleteDish(int dishID) {
        return database.delete("Dish", "DishID=?", new String[]{String.valueOf(dishID)}) > 0;
    }
}
