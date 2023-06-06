package bomoncntt.svk62.mssv2051067158.data.local.repository;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bomoncntt.svk62.mssv2051067158.data.local.KirinNoodlesSQLiteHelper;
import bomoncntt.svk62.mssv2051067158.domain.models.TableLocation;
import bomoncntt.svk62.mssv2051067158.domain.repository.TableLocationRepository;

public class TableLocationRepositoryImpl implements TableLocationRepository {

    private final SQLiteDatabase database;
    private static TableLocationRepositoryImpl instance;

    public synchronized static TableLocationRepositoryImpl getInstance(KirinNoodlesSQLiteHelper helper){
        if(instance == null){
            instance = new TableLocationRepositoryImpl(helper);
        }
        return instance;
    }

    private TableLocationRepositoryImpl(KirinNoodlesSQLiteHelper sqLiteHelper){
        this.database = sqLiteHelper.getWritableDatabase();
    }


    @Override
    public boolean addTableLocation(TableLocation tableLocation) {
        ContentValues values = new ContentValues();
        values.put("TableName", tableLocation.getTableName());
        long result = database.insert("TableLocation", null, values);
        return result != -1;
    }

    @SuppressLint("Range")
    @Override
    public TableLocation getTableLocationByID(int tableLocationID) {
        Cursor cursor = database.query(
                "TableLocation",
                null,
                "TableID = ?",
                new String[]{String.valueOf(tableLocationID)},
                null,
                null,
                null
        );

        TableLocation tableLocation = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("TableID"));
            String tableName = cursor.getString(cursor.getColumnIndex("TableName"));

            tableLocation = new TableLocation(id, tableName);
        }

        cursor.close();
        return tableLocation;
    }

    @SuppressLint("Range")
    @Override
    public List<TableLocation> getAllTableLocations() {
        Cursor cursor = database.query("TableLocation", null, null, null, null, null, null);

        List<TableLocation> tableLocations = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("TableID"));
            String tableName = cursor.getString(cursor.getColumnIndex("TableName"));

            TableLocation tableLocation = new TableLocation(id, tableName);
            tableLocations.add(tableLocation);
        }

        cursor.close();
        return tableLocations;
    }

    @Override
    public boolean updateTableLocation(TableLocation tableLocation) {
        ContentValues values = new ContentValues();
        values.put("TableName", tableLocation.getTableName());

        int rowsAffected = database.update(
                "TableLocation",
                values,
                "TableID = ?",
                new String[]{String.valueOf(tableLocation.getTableID())}
        );

        return rowsAffected > 0;
    }

    @Override
    public boolean deleteTableLocation(int tableLocationID) {
        int rowsAffected = database.delete(
                "TableLocation",
                "TableID = ?",
                new String[]{String.valueOf(tableLocationID)}
        );

        return rowsAffected > 0;
    }
}
