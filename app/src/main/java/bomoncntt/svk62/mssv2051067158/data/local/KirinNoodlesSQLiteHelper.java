package bomoncntt.svk62.mssv2051067158.data.local;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KirinNoodlesSQLiteHelper extends SQLiteOpenHelper {
    private static KirinNoodlesSQLiteHelper instance;

    private static final String DATABASE_NAME = "KirinNoodlesDatabase";
    private static final int DATABASE_VERSION = 1;
    private KirinNoodlesSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized KirinNoodlesSQLiteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new KirinNoodlesSQLiteHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createDishTableQuery = "CREATE TABLE Dish (DishID INTEGER PRIMARY KEY, DishName TEXT, Price REAL, ImageLocation TEXT)";
        String createTableTableQuery = "CREATE TABLE TableLocation (TableID INTEGER PRIMARY KEY, TableName TEXT)";
        String createInvoiceTableQuery = "CREATE TABLE Invoice (InvoiceID INTEGER PRIMARY KEY, Total REAL, OrderTime DATETIME, TableID INTEGER, PaymentStatus TEXT CHECK (PaymentStatus IN ('preparing', 'waiting_for_payment', 'paid')), FOREIGN KEY (TableID) REFERENCES TableLocation(TableID) ON DELETE RESTRICT)";
        String createOrderedDishTableQuery = "CREATE TABLE OrderedDish (DishID INTEGER, Quantity INTEGER, Note TEXT, InvoiceID INTEGER, PRIMARY KEY (DishID,InvoiceID), FOREIGN KEY (DishID) REFERENCES Dish(DishID), FOREIGN KEY (InvoiceID) REFERENCES Invoice(InvoiceID) ON DELETE RESTRICT)";

        db.execSQL(createDishTableQuery);
        db.execSQL(createTableTableQuery);
        db.execSQL(createInvoiceTableQuery);
        db.execSQL(createOrderedDishTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void resetAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS OrderedDish");
        db.execSQL("DROP TABLE IF EXISTS Invoice");
        db.execSQL("DROP TABLE IF EXISTS Dish");
        db.execSQL("DROP TABLE IF EXISTS TableLocation");
        onCreate(db);
    }
}
