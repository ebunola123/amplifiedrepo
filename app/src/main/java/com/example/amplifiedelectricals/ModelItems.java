package com.example.amplifiedelectricals;

public class ModelItems {

    String title;
    String manufacturer;
    String price;
    String category;
    String itemID;
    int image;

    public ModelItems(String title, String manufacturer, String price, String category, int image, String itemID) {
        this.title = title;
        this.manufacturer = manufacturer;
        this.price = price;
        this.category = category;
        this.image = image;
        this.itemID = itemID;
    }

    public ModelItems(){

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }
}
