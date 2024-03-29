package com.csis3175.fleamart.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.csis3175.fleamart.model.Item;
import com.csis3175.fleamart.model.TransacationsAdapter;
import com.csis3175.fleamart.model.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fleamartDB";
    private static final int DATABASE_VERSION = 6; /** Updated column Transaction_id to transaction_id*/

    //------ USERS TABLE -------- //
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "userId";
    private static final String COLUMN_FIRST_NAME = "firstName";
    private static final String COLUMN_LAST_NAME = "lastName";
    private static final String COLUMN_EMAIL = "email";
//    private static final String COLUMN_CELL = "cell";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    //------ END OF USERS TABLE -------- //

    //------ ITEMS TABLE -------- //
    private static final String TABLE_ITEMS = "items";
    private static final String COLUMN_ITEM_ID = "itemid";
    private static final String COLUMN_ITEM_NAME = "name";
    private static final String COLUMN_ITEM_PRICE = "price";
    private static final String COLUMN_ITEM_DISCOUNT = "discount";
    private static final String COLUMN_ITEM_DESCRIPTION = "description";
    private static final String COLUMN_ITEM_LOCATION = "location";
    private static final String COLUMN_ITEM_CATEGORY = "category";
    private static final String COLUMN_ITEM_TAG = "tag";
    private static final String COLUMN_ITEM_IMAGE = "image";
    private static final String COLUMN_ITEM_SHAREABLE = "isShareable";
    private static final String COLUMN_ITEM_STATUS = "itemstatus";
    private static final String COLUMN_ITEM_DATE = "date";
    private static final String  COLUMN_USER_ID = "posterid";

    //------END OF ITEMS TABLE -------- //
    //------ TRANSACTION TABLE -------- //
    private static final String TABLE_TRANSACTION = "transactions";
    private static final String COLUMN_TRANSACTION_ID = "transaction_id";
    private static final String COLUMN_TRANSACTION_BUYER_ID = "transaction_buyer_id";
    private static final String COLUMN_TRANSACTION_SELLER_ID = "transaction_seller_id";
    private static final String COLUMN_TRANSACTION_DATE = "transaction_date";
    private static final String COLUMN_TRANSACTION_DELIVERY = "transaction_delivery";
    private static final String COLUMN_TRANSACTION_STATUS = "transaction_status";

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
//                + COLUMN_CELL + " INTEGER, "
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT"
                + ")";

        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
                + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_ITEM_PRICE + " REAL,"
                + COLUMN_ITEM_DISCOUNT + " REAL,"
                + COLUMN_ITEM_DESCRIPTION + " TEXT,"
                + COLUMN_ITEM_LOCATION + " TEXT,"
                + COLUMN_ITEM_CATEGORY + " TEXT,"
                + COLUMN_ITEM_TAG + " TEXT,"
                + COLUMN_ITEM_IMAGE + " BLOB,"
                + COLUMN_ITEM_SHAREABLE + " BOOLEAN,"
                + COLUMN_ITEM_STATUS + " TEXT,"
                + COLUMN_ITEM_DATE + " TEXT,"
                + COLUMN_USER_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + ")"
                + ")";

        String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + TABLE_TRANSACTION + "("
                + COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ITEM_ID + " INTEGER,"
                + COLUMN_TRANSACTION_BUYER_ID + " INTEGER,"
                + COLUMN_TRANSACTION_SELLER_ID + " INTEGER,"
                + COLUMN_TRANSACTION_DATE + " TEXT,"
                + COLUMN_TRANSACTION_DELIVERY + " TEXT,"
                + COLUMN_TRANSACTION_STATUS + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_ITEM_ID + ") REFERENCES " + TABLE_ITEMS + "(" + COLUMN_ITEM_ID + "),"
                + "FOREIGN KEY(" + COLUMN_TRANSACTION_BUYER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + "),"
                + "FOREIGN KEY(" + COLUMN_TRANSACTION_SELLER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + ")"
                + ")";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_ITEMS_TABLE);
        db.execSQL(CREATE_TRANSACTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades (if needed)
        if (oldVersion < newVersion) {
            // Perform necessary upgrades, e.g., add new columns or update existing ones
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
            onCreate(db);
        }
    }

    public void insertUser(String firstName, String lastName, String username, String email, String hashedPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
//        values.put(COLUMN_CELL, cell);
        values.put(COLUMN_PASSWORD, hashedPassword);
        // Inserting Row
        db.insert(TABLE_USERS, null, values);

    }

    public String getUsernameByID(int userId){
        String username = null;
        SQLiteDatabase db = this.getReadableDatabase();

        // Assuming you have a Users table with columns "user_id" and "username"
        String[] projection = { "firstName" };
        String selection = "userId = ?";
        String[] selectionArgs = { String.valueOf(userId) };

        Cursor cursor = db.query("Users", projection, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndexOrThrow("firstName"));
            cursor.close();
        }

        db.close();

        return username;
    }

    public void insertItem(String name, Double price, String description, String location, String category, String tag, byte[] img,boolean isShareable,String date, int userId,double dValue,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, name);
        values.put(COLUMN_ITEM_PRICE, price);
        values.put(COLUMN_ITEM_DISCOUNT, dValue);
        values.put(COLUMN_ITEM_DESCRIPTION, description);
        values.put(COLUMN_ITEM_LOCATION, location);
        values.put(COLUMN_ITEM_CATEGORY, category);
        values.put(COLUMN_ITEM_TAG, tag);
        values.put(COLUMN_ITEM_IMAGE, img);
        values.put(COLUMN_ITEM_SHAREABLE, isShareable);
        values.put(COLUMN_ITEM_DATE, date);
        values.put(COLUMN_ITEM_STATUS, status);
        values.put(COLUMN_USER_ID, userId);
        db.insert(TABLE_ITEMS, null, values);
    }

    public int updateItem(int oldId, String name, Double price, String description, String location, String category, String tag, byte[] img,boolean isShareable,String date, int userId,double dValue,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_ID, oldId);
        values.put(COLUMN_ITEM_NAME, name);
        values.put(COLUMN_ITEM_PRICE, price);
        values.put(COLUMN_ITEM_DISCOUNT, dValue);
        values.put(COLUMN_ITEM_DESCRIPTION, description);
        values.put(COLUMN_ITEM_LOCATION, location);
        values.put(COLUMN_ITEM_CATEGORY, category);
        values.put(COLUMN_ITEM_TAG, tag);
        values.put(COLUMN_ITEM_IMAGE, img);
        values.put(COLUMN_ITEM_SHAREABLE, isShareable);
        values.put(COLUMN_ITEM_DATE, date);
        values.put(COLUMN_ITEM_STATUS, status);
        values.put(COLUMN_USER_ID, userId);
        int updateItemStatus = db.update(TABLE_ITEMS, values, COLUMN_ITEM_ID + " = ?", new String[] { String.valueOf(oldId) });

        return updateItemStatus;
    }

    public void insertTransaction(int transaction_buyer_id, int transaction_seller_id, int itemId, String transaction_date, String transaction_delivery, String transaction_status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRANSACTION_BUYER_ID,transaction_buyer_id );
        values.put(COLUMN_TRANSACTION_SELLER_ID,transaction_seller_id );
        values.put(COLUMN_TRANSACTION_DATE,transaction_date );
        values.put(COLUMN_TRANSACTION_DELIVERY, transaction_delivery);
        values.put(COLUMN_TRANSACTION_STATUS,transaction_status );
        values.put(COLUMN_ITEM_ID,itemId );
        db.insert(TABLE_TRANSACTION, null, values);

    }

    //View All items excluding ones posted by current USER
    //TODO modify query to display items that are available

   public Cursor viewAllItems(int userID){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ITEMS +
                " WHERE posterid <> ? or posterid is NULL";
        Cursor c = sqLiteDatabase.rawQuery(query, new String[]{String.valueOf(userID)});
        return c;
    }

    public Cursor viewPostedItemsByUser(int userID){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ITEMS +
                " WHERE posterid = ? or posterid is NULL";
        Cursor c = sqLiteDatabase.rawQuery(query, new String[]{String.valueOf(userID)});
        return c;
    }

    public Cursor viewUserTransactions(int userId){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT transactions.*, users.username AS buyerName, items.name AS itemName, items.image,items.price AS price " +
                "FROM " + TABLE_TRANSACTION + " AS transactions " +
                "LEFT JOIN " + TABLE_USERS + " AS users ON transactions.transaction_buyer_id = users.userId " +
                "LEFT JOIN " + TABLE_ITEMS + " AS items ON transactions.itemid = items.itemid " +
                "WHERE transaction_seller_id = ? OR transaction_seller_id IS NULL";


        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        return cursor;
    }

    public String getHashedPassword(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String hashedPassword = null;
        Cursor c = null;
        try{
            c = db.query(TABLE_USERS,null,"username=?",new String[]{username},null,null,null);
            if (c !=null && c.moveToFirst())
            {
                int hIndex = c.getColumnIndex("password");
                if(hIndex != -1){
                    hashedPassword = c.getString(hIndex);
                }
            }
            else {
                c.close();
            }
        } catch (Exception e){
            //Error?
        }
        return hashedPassword;

    }



//    public boolean isValidUser(String username, String password) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        //This query looks for the a user using user name and password. The "?" is used to avoid SQL Injection Attacks and allows parameterization input
//        Cursor cursor = db.query(TABLE_USERS, null, "username=? AND password=?", new String[]{username, password}, null, null, null);
//        boolean isValid = cursor.moveToFirst();
//        cursor.close();
//        return isValid;
//    }

    public boolean doesUsernameExist(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID}, "username=?", new String[]{username}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean updateUser(int oldId,String firstName, String lastName,String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Updating values
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);

        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        // updating row
        int updateStatus = db.update(TABLE_USERS, values, COLUMN_ID + " = ?", new String[] { String.valueOf(oldId) });

        return updateStatus >0;
    }

    public boolean updateItemStatus(int itemId, String itemStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Updating values
        values.put(COLUMN_ITEM_STATUS, itemStatus);

        // updating row
        int rowsAffected = db.update(TABLE_ITEMS, values, COLUMN_ITEM_ID + " = ?", new String[]{String.valueOf(itemId)});
        return rowsAffected > 0;
    }

    /**
     * This method returns a User object that pulls the data from the database. Modified params - KK
     * @param userid
     *
     * @return
     */
    @SuppressLint("Range")//This suppresses warning that the column index might return -1
    public String[] getUserDetails(int userid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] userDetails = new String[4];
        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{COLUMN_ID, COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_EMAIL,COLUMN_USERNAME},
                "userid=?",
                new String[]{String.valueOf(userid)},
                null,
                null,
                null
        );

        try {
            if (cursor.moveToFirst()) {
                int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
                if (idColumnIndex != -1) {

                    userDetails[0] = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
                    userDetails[1] = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
                    userDetails[2] = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                    userDetails[3] = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));

                    // Add other user details as needed
                } else {
                    // Handle the case where the COLUMN_ID does not exist
                }
            }
        } finally {
            cursor.close();
            db.close();
        }

        return userDetails;
    }
    /**GET USER ID by username and password. To be used once validate and will store in SharedPreferences for session*/
    public int getUserId(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        int userId = 0;
        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{COLUMN_ID},
                "username=? AND password=?",
                new String[]{username, password},
                null,
                null,
                null
        );

        try {
            if (cursor.moveToFirst()) {
                int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
                if (idColumnIndex != -1) {
                    userId = cursor.getInt(idColumnIndex);

                } else {
                    // Handle the case where the COLUMN_ID does not exist
                }
            }
        } finally {
            cursor.close();
            db.close();
        }

        return userId;
    }

    public Item getItemById(int id) {
        //TODO exception handling

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ITEMS + " where itemid = ? ";
        Cursor c = sqLiteDatabase.rawQuery(query, new String[]{String.valueOf(id)});
        Item item = new Item();
        if (c != null && c.moveToFirst()) {
            item.setItemID(c.getInt(c.getColumnIndexOrThrow("itemid")));
            item.setItemName(c.getString(c.getColumnIndexOrThrow("name")));
            item.setItemPrice(c.getDouble(c.getColumnIndexOrThrow("price")));
            item.setDiscount(c.getDouble(c.getColumnIndexOrThrow("discount")));
            item.setStatus(c.getString(c.getColumnIndexOrThrow("itemstatus")));
            item.setShareable(c.getInt(c.getColumnIndexOrThrow("isShareable")) == 1);
            item.setImageData(c.getBlob(c.getColumnIndexOrThrow("image")));
            item.setItemDescription(c.getString(c.getColumnIndexOrThrow("description")));
            item.setLocation(c.getString(c.getColumnIndexOrThrow("location")));
            item.setDate(c.getString(c.getColumnIndexOrThrow("date")));
            item.setCategory(c.getString(c.getColumnIndexOrThrow("category")));
            item.setTag(c.getString(c.getColumnIndexOrThrow("tag")));
            item.setUserID(c.getInt(c.getColumnIndexOrThrow("posterid")));
            c.close();
            //TODO add tag
//            private static final String COLUMN_ITEM_TAG = "tag";
//            private static final String  COLUMN_USER_ID = "userId";
        }
        return item;
    }
    public boolean updateTransactionStatus(int transactionId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRANSACTION_STATUS, newStatus);

        int rowsAffected = db.update(TABLE_TRANSACTION, values, COLUMN_TRANSACTION_ID + " = ?",
                new String[]{String.valueOf(transactionId)});

        return rowsAffected > 0;
    }


    public boolean isTransactionFinalized(int currentUserId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_TRANSACTION_BUYER_ID + " = ? AND " + COLUMN_TRANSACTION_STATUS + " LIKE ?";
        String[] selectionArgs = {String.valueOf(currentUserId), "finalized"};
        Cursor cursor = db.query(TABLE_TRANSACTION, null, selection, selectionArgs, null, null, null);
        boolean isFinalized = cursor != null && cursor.getCount() > 0;
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return isFinalized;
    }


}
