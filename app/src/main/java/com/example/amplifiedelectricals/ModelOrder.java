package com.example.amplifiedelectricals;

public class ModelOrder {

    String itemID;
    String orderID;
    String date;
    String time;
    String title;
    String manufacturer;
    String quantity;
    String totalPrice;
    String price;

    public ModelOrder(String itemID, String orderID, String date, String time, String title, String manufacturer, String quantity, String totalPrice, String price) {
        this.itemID = itemID;
        this.orderID = orderID;
        this.date = date;
        this.time = time;
        this.title = title;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.price = price;
    }

    public ModelOrder(){

    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
