package com.example.amplifiedelectricals.patterns;

import com.example.amplifiedelectricals.models.ModelStock;

import java.util.ArrayList;

public class ModelStockCaretaker {

    //In this class, all the mementos will be saved
    ArrayList<ModelStock> savedStock = new ArrayList<ModelStock>();

    //Adding memento to arraylist
    public void addMemento(ModelStock modelStock){
        savedStock.add(modelStock);
    }

    //Getting the memnto from the arraylist
    public ModelStock getMemento(int index){
        return savedStock.get(index);
    }

}
