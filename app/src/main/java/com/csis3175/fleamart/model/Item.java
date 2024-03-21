package com.csis3175.fleamart.model;

public class Item {


    private String itemName;
    private String itemDescription;
    private double itemPrice;
    private boolean isShareable;
    private String date;
    private byte[] imageData; //storing image into bytes.


    public String getItemName() {
        return itemName;
    }

    public boolean isShareable() {
        return isShareable;
    }

    public String getDate() {
        return date;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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





    public byte[] getImageData() {
        return imageData;
    }

    public Item(String itemName, String itemDescription, double itemPrice,byte[] imageData,boolean isShareable,String date) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.imageData = imageData;
        this.isShareable = isShareable;
        this.date = date;
    }
}
