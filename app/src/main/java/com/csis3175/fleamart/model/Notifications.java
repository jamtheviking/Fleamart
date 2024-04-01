package com.csis3175.fleamart.model;

import java.io.Serializable;

public class Notifications implements Serializable {

    private int notificationId;
    private String notificationMessage;
    private int buyerId;
    private int sellerId;
    private int transaction_id;

    public Notifications(){

    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public int getTransaction_id(){
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id){
        this.transaction_id = transaction_id;
    }
    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public Notifications(int notificationId, String notificationMessage,int transaction_id, int buyerId, int sellerId) {
        this.notificationId = notificationId;
        this.notificationMessage = notificationMessage;
        this.transaction_id = transaction_id;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
    }
}
