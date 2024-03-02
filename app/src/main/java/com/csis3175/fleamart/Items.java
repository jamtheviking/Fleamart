package com.csis3175.fleamart;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Items extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "app_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ITEMS = "items";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ITEM_NAME = "itemName";
    private static final String COLUMN_ITEM_DESCRIPTION = "itemDescription";
    private static final String COLUMN_ITEM_CATEGORY = "itemCategory";
    private static final String COLUMN_ITEM_PRICE = "itemPrice";
    private static final String COLUMN_ITEM_LOCATION = "itemLocation";
    private static final String COLUMN_ITEM_SHAREABLE = "itemShareable";
    private static final String COLUMN_ITEM_IMAGE = "itemImage";
    public Items(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_ITEM_DESCRIPTION + " TEXT,"
                + COLUMN_ITEM_CATEGORY + " TEXT,"
                + COLUMN_ITEM_LOCATION + " TEXT,"
                + COLUMN_ITEM_PRICE + " DOUBLE,"
                + COLUMN_ITEM_SHAREABLE + " INTEGER,"
                + COLUMN_ITEM_IMAGE + " BLOB"
                + ")";
            db.execSQL(CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades (if needed)
        if (oldVersion < newVersion) {
            // Perform necessary upgrades, e.g., add new columns or update existing ones
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
            onCreate(db);
        }
    }

    public void insertItem(String itemName, String itemDescription, String itemCategory,String itemLocation, Double itemPrice, int shareable, byte[] imageData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ITEM_NAME, itemName);
        values.put(COLUMN_ITEM_DESCRIPTION, itemDescription);
        values.put(COLUMN_ITEM_CATEGORY, itemCategory);
        values.put(COLUMN_ITEM_PRICE, itemPrice);
        values.put(COLUMN_ITEM_LOCATION, itemLocation);
        values.put(COLUMN_ITEM_SHAREABLE, shareable);
        values.put(COLUMN_ITEM_IMAGE, imageData);
        // Inserting Row
        db.insert(TABLE_ITEMS, null, values);
        db.close(); // Closing database connection
    }


}
