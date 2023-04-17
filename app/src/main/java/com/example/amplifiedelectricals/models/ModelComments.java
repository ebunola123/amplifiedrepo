package com.example.amplifiedelectricals.models;

public class ModelComments {

    String email;
    String itemID;
    String comment;
    String name;
    String date;

    public ModelComments(String email, String itemID, String comment, String name, String date) {
        this.email = email;
        this.itemID = itemID;
        this.comment = comment;
        this.name = name;
        this.date = date;
    }

    public ModelComments(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
