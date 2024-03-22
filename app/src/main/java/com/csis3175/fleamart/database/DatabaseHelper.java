package com.csis3175.fleamart.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.csis3175.fleamart.model.Item;
import com.csis3175.fleamart.model.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fleamartDB";
    private static final int DATABASE_VERSION = 3;

    //------ USERS TABLE -------- //
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "userId";
    private static final String COLUMN_FIRST_NAME = "firstName";
    private static final String COLUMN_LAST_NAME = "lastName";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    //------ END OF USERS TABLE -------- //

    //------ ITEMS TABLE -------- //
    private static final String TABLE_ITEMS = "items";
    private static final String COLUMN_ITEM_ID = "itemid";
    private static final String COLUMN_ITEM_NAME = "name";
    private static final String COLUMN_ITEM_PRICE = "price";
    private static final String COLUMN_ITEM_DESCRIPTION = "description";
    private static final String COLUMN_ITEM_LOCATION = "location";
    private static final String COLUMN_ITEM_CATEGORY = "category";
    private static final String COLUMN_ITEM_TAG = "tag";
    private static final String COLUMN_ITEM_IMAGE = "image";
    private static final String COLUMN_ITEM_SHAREABLE = "isShareable";
    private static final String COLUMN_ITEM_DATE = "date";
    private static final String  COLUMN_USER_ID = "userId";

    //------END OF ITEMS TABLE -------- //



    public DatabaseHelper(Context context){
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
                + COLUMN_ITEM_TAG + " TEXT,"
                + COLUMN_ITEM_IMAGE + " BLOB,"
                + COLUMN_ITEM_SHAREABLE + " BOOLEAN,"
                + COLUMN_ITEM_DATE + " TEXT,"
                + COLUMN_USER_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + ")"
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

    }

    public void insertItem(String name, Double price, String description, String location, String category, String tag, byte[] img,boolean isShareable,String date, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, name);
        values.put(COLUMN_ITEM_PRICE, price);
        values.put(COLUMN_ITEM_DESCRIPTION, description);
        values.put(COLUMN_ITEM_LOCATION, location);
        values.put(COLUMN_ITEM_CATEGORY, category);
        values.put(COLUMN_ITEM_TAG, tag);
        values.put(COLUMN_ITEM_IMAGE, img);
        values.put(COLUMN_ITEM_SHAREABLE, isShareable);
        values.put(COLUMN_ITEM_DATE, date);
        values.put(COLUMN_USER_ID, userId);
        db.insert(TABLE_ITEMS, null, values);


    }

    //View All items excluding ones posted by current USER

   public Cursor viewAllItems(int userID){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ITEMS +
                " WHERE userId <> ? or userId is NULL";
        Cursor c = sqLiteDatabase.rawQuery(query, new String[]{String.valueOf(userID)});
        return c;
    }



    public boolean isValidUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        //This query looks for the a user using user name and password. The "?" is used to avoid SQL Injection Attacks and allows parameterization input
        Cursor cursor = db.query(TABLE_USERS, null, "username=? AND password=?", new String[]{username, password}, null, null, null);
        boolean isValid = cursor.moveToFirst();
        cursor.close();
        return isValid;
    }

    public int updateUser(int oldId,String firstName, String lastName, String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Updating values
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        // updating row
        int updateStatus = db.update(TABLE_USERS, values, COLUMN_ID + " = ?", new String[] { String.valueOf(oldId) });

        return updateStatus;
    }

    /**
     * This method returns a User object that pulls the data from the database.
     * @param username
     * @param password
     * @return
     */
    @SuppressLint("Range")//This suppresses warning that the column index might return -1
    public User getUserDetails(String username, String password) {
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

    public Item getItemById(int id) {
        //TODO exception handling

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ITEMS + " where itemid = ? ";
        Cursor c = sqLiteDatabase.rawQuery(query, new String[]{String.valueOf(id)});
        Item item = new Item();
        if (c != null && c.moveToFirst()) {
            item.setItemName(c.getString(c.getColumnIndexOrThrow("name")));
            item.setItemPrice(c.getDouble(c.getColumnIndexOrThrow("price")));
            item.setShareable(c.getInt(c.getColumnIndexOrThrow("isShareable")) == 1);
            item.setImageData(c.getBlob(c.getColumnIndexOrThrow("image")));
            item.setItemDescription(c.getString(c.getColumnIndexOrThrow("description")));
            item.setLocation(c.getString(c.getColumnIndexOrThrow("location")));
            item.setDate(c.getString(c.getColumnIndexOrThrow("date")));
            item.setCategory(c.getString(c.getColumnIndexOrThrow("category")));
            item.setUserID(c.getInt(c.getColumnIndexOrThrow("userId")));
            c.close();
            //TODO add tag
//            private static final String COLUMN_ITEM_TAG = "tag";
//            private static final String  COLUMN_USER_ID = "userId";
        }
        return item;
    }


}
