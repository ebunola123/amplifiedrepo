package com.example.amplifiedelectricals;

public class ModelItems {

    String title;
    String manufacturer;
    double price;
    String category;
    int image;

    public ModelItems(String title, String manufacturer, double price, String category, int image) {
        this.title = title;
        this.manufacturer = manufacturer;
        this.price = price;
        this.category = category;
        this.image = image;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
}
