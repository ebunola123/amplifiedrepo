package com.example.amplifiedelectricals;

public class ModelStock {

    String itemID;
    String stock;

    public ModelStock(String itemID, String stock) {
        this.itemID = itemID;
        this.stock = stock;
    }

    public ModelStock(){

    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }


    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
