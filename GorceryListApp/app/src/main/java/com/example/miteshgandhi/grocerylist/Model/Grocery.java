package com.example.miteshgandhi.grocerylist.Model;

/**
 * Created by miteshgandhi on 11/6/17.
 */

public class Grocery {
    private int id;
    private String groceryItem;
    private String groceryQty;
    private String grocery_Date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroceryItem() {
        return groceryItem;
    }

    public void setGroceryItem(String groceryItem) {
        this.groceryItem = groceryItem;
    }

    public String getGroceryQty() {
        return groceryQty;
    }

    public void setGroceryQty(String groceryQty) {
        this.groceryQty = groceryQty;
    }

    public String getGrocery_Date() {
        return grocery_Date;
    }

    public void setGrocery_Date(String grocery_Date) {
        this.grocery_Date = grocery_Date;
    }
}
