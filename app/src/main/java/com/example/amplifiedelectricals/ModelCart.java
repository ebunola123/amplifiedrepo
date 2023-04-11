package com.example.amplifiedelectricals;

public class ModelCart {

    String itemID;
    String title;
    String price;
    String quantity;

    //if quantity is >1, then do quantity * price


    public ModelCart(String itemID, String title, String price, String quantity) {
        this.itemID = itemID;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public ModelCart(){

    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
