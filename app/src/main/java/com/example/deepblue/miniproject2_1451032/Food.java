package com.example.deepblue.miniproject2_1451032;

/**
 * Created by deepblue on 8/13/2016.
 */
public class Food {
    private int ID;
    private String name;
    private int price;
    private int quantity;

    public Food(int ID,String name, int price,int quantity) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getID() {
        return ID;
    }

    public void setID(){
        this.ID=ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity(){return quantity;}

    public void setQuantity(){this.quantity = quantity;}

}
