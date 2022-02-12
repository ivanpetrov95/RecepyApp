package com.example.testproject.Entities;

import java.io.Serializable;

public class ProductEntity implements Serializable
{
    private int ID;
    private String productName;
    private boolean isInDb;
    public ProductEntity(String productName)
    {
        setProductName(productName);
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public boolean getIsInDb() {
        return isInDb;
    }

    public void setIsInDb(boolean inDb) {
        isInDb = inDb;
    }
}
