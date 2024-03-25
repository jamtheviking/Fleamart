package com.csis3175.fleamart.model;

import java.io.Serializable;

public class Item implements Serializable {


    private int itemID;
    private String itemName;
    private String itemDescription;
    private double itemPrice;
    private boolean isShareable;
    private double discount;
    private String date;
    private byte[] imageData; //storing image into bytes.
    private int userID; //Todo: change this to sellerId or posterid
    private String location;
    private String category;
    private String tag;
    private String status;
    public Item(int itemID, String itemName, String itemDescription, double itemPrice, boolean isShareable, double discount, String date, byte[] imageData, int userID, String location, String category, String tag, String status) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.isShareable = isShareable;
        this.discount = discount;
        this.date = date;
        this.imageData = imageData;
        this.userID = userID;
        this.location = location;
        this.category = category;
        this.tag = tag;
        this.status = status;
    }
    public Item(int itemID, String itemName, String itemDescription, double itemPrice, boolean isShareable, double discount, String date, byte[] imageData, int userID, String location, String category, String tag) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.isShareable = isShareable;
        this.discount = discount;
        this.date = date;
        this.imageData = imageData;
        this.userID = userID;
        this.location = location;
        this.category = category;
        this.tag = tag;
    }
    //Default Constructor
    public Item() {
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public void setTag(String tag) { this.tag = tag; }
    public String getTag() { return tag; }
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setShareable(boolean shareable) {
        isShareable = shareable;
    }

    public boolean getIsShareable() {
        return isShareable;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }


}
