package com.csis3175.fleamart;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Users extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app_database";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRST_NAME = "firstName";
    private static final String COLUMN_LAST_NAME = "lastName";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String TABLE_ITEMS = "items";
    private static final String COLUMN_ITEM_ID = "id";
    private static final String COLUMN_ITEM_NAME = "name";
    private static final String COLUMN_ITEM_PRICE = "price";
    private static final String COLUMN_ITEM_DESCRIPTION = "description";
    private static final String COLUMN_ITEM_LOCATION = "location";
    private static final String COLUMN_ITEM_CATEGORY = "category";
    private static final String COLUMN_ITEM_TAG = "tag";
    public Users(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FIRST_NAME + " TEXT,"
                + COLUMN_LAST_NAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT"
                + ")";
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
                + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_ITEM_PRICE + " REAL,"
                + COLUMN_ITEM_DESCRIPTION + " TEXT,"
                + COLUMN_ITEM_LOCATION + " TEXT,"
                + COLUMN_ITEM_CATEGORY + " TEXT,"
                + COLUMN_ITEM_TAG + " TEXT"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades (if needed)
        if (oldVersion < newVersion) {
            // Perform necessary upgrades, e.g., add new columns or update existing ones
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
            onCreate(db);
        }
    }

    public void insertUser(String firstName, String lastName, String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection
    }

    public void insertItem(String name, String price, String description, String location, String category, String tag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, name);
        values.put(COLUMN_ITEM_PRICE, price);
        values.put(COLUMN_ITEM_DESCRIPTION, description);
        values.put(COLUMN_ITEM_LOCATION, location);
        values.put(COLUMN_ITEM_CATEGORY, category);
        values.put(COLUMN_ITEM_TAG, tag);

        db.insert(TABLE_ITEMS, null, values);
        db.close();
    }



    public boolean isValidUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, "username=? AND password=?", new String[]{username, password}, null, null, null);
        boolean isValid = cursor.moveToFirst();
        cursor.close();
        db.close();
        return isValid;
    }


    @SuppressLint("Range")//This suppresses warning that the column index might return -1
    public com.csis3175.fleamart.User getUserDetails(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{COLUMN_ID, COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_EMAIL},
                "username=? AND password=?",
                new String[]{username, password},
                null,
                null,
                null
        );

        User user = null;

        try {
            if (cursor.moveToFirst()) {
                int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
                if (idColumnIndex != -1) {
                    user = new User();
                    user.setId(cursor.getInt(idColumnIndex));
                    user.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
                    user.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
                    user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                    // Add other user details as needed
                } else {
                    // Handle the case where the COLUMN_ID does not exist
                }
            }
        } finally {
            cursor.close();
            db.close();
        }

        return user;
    }

    //JO
    /* List<Users> searchProducts(String q) {
        List<Users> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Users user = null;

    /
        String selectQuery = "SELECT * FROM " + TABLE_ITEMS + " WHERE " +
                    COLUMN_ITEM_NAME + " LIKE ? OR " + COLUMN_ITEM_CATEGORY
                + " LIKE ? OR " + COLUMN_ITEM_TAG + " LIKE ?";

        Cursor c = db.rawQuery(selectQuery, new String[]{"%" + q + "%", "%" + q + "%", "%" + q + "%"});
    if (c != null) {
        try {
            int idColumnIndex = c.getColumnIndex(COLUMN_ID);
            if (idColumnIndex != -1) {
                while (c.moveToNext()) {
                    @SuppressLint("Range") String name = c.getString(c.getColumnIndex(COLUMN_ITEM_NAME));
                    @SuppressLint("Range") String price = c.getString(c.getColumnIndex(COLUMN_ITEM_PRICE));
                    @SuppressLint("Range") String description = c.getString(c.getColumnIndex(COLUMN_ITEM_DESCRIPTION));
                    @SuppressLint("Range") String location = c.getString(c.getColumnIndex(COLUMN_ITEM_LOCATION));
                    @SuppressLint("Range") String category = c.getString(c.getColumnIndex(COLUMN_ITEM_CATEGORY));
                    @SuppressLint("Range") String tag = c.getString(c.getColumnIndex(COLUMN_ITEM_TAG));
                    user.insertItem(name, price, description, location, category, tag);
                    productList.add(user);
                }
                }
            } finally {
            c.close();
        }
}
    db.close();
    return productList;

    }
// JO

     */
    }





