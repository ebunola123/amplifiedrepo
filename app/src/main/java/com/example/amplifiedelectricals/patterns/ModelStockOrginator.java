package com.example.amplifiedelectricals.patterns;

import com.example.amplifiedelectricals.models.ModelStock;

public class ModelStockOrginator {

    String itemID;
    String stock;

    public void setItemID(String itemID){
        System.out.println("Originator Class: Current Version of Item ID: '" + itemID + "'. ");
        this.itemID = itemID;
    }

    public void setStock(String stock){
        System.out.println("Originator Class: Current Amount of Stock: '" + stock + "'. ");
        this.stock = stock;
    }

    //Now creating a new memento with a new stockID
    public ModelStock storingInMemento(){
        System.out.println("From the ModelStockOriginator Class: Saving to Memento");
        return new ModelStock(itemID, stock);
    }

    //Now getting the itemID & stock that is currently being stored in memento
    public String restoreFromModelStock(ModelStock modelStock){
        itemID = modelStock.getItemID();
        stock = modelStock.getStock();

        System.out.println("From ModelStockOriginator: Previous Stock Details Saved in Memento Class:\n" + "Item ID: " + itemID + "\n Stock: " + stock);
        return itemID + modelStock;
    }

}
