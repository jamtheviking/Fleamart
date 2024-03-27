package com.csis3175.fleamart.model;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {

    private int transactionId;
    private int itemId;
    private int sellerId;
    private int buyerId;
    private String date;
    private String status;
    private String delivery;

    public Transaction(){

    }

    public Transaction(int transactionId, int itemId, int sellerId, int buyerId, String date, String status, String delivery){
        this.transactionId = transactionId;
        this.itemId = itemId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.date = date;
        this.status = status;
        this.delivery = delivery;
    }

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
