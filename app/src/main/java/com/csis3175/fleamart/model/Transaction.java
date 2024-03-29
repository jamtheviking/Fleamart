package com.csis3175.fleamart.model;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {

    private int transactionId;
    private String buyerName;
    private String itemName;
    private byte[] imageData;
    private int itemId;
    private int sellerId;
    private int buyerId;
    private String date;
    private String status;
    private String delivery;
    private double itemPrice;

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Transaction(){

    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getItemName() {
        return itemName;
    }



    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Transaction(int transactionId, int itemId, int sellerId, int buyerId, String date, String status, String delivery, String buyerName, String itemName, byte[] imageData,double itemPrice){
        this.transactionId = transactionId;
        this.itemId = itemId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.date = date;
        this.status = status;
        this.delivery = delivery;
        this.buyerName =buyerName;
        this.itemName = itemName;
        this.imageData = imageData;
        this.itemPrice = itemPrice;
    }
//    public Transaction(int transactionId, int itemId, int sellerId, int buyerId, String date, String status, String delivery){
//        this.transactionId = transactionId;
//        this.itemId = itemId;
//        this.sellerId = sellerId;
//        this.buyerId = buyerId;
//        this.date = date;
//        this.status = status;
//        this.delivery = delivery;
//    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}
