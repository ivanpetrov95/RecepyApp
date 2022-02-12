package com.example.testproject.Entities;

public class RecepyProductConnectionEntity
{
    private int recepyID;
    private int productID;
    private double productQuantity;
    private int productTimeForPreparing;

    public RecepyProductConnectionEntity(int recepyID, int productID, double productQuantity, int productTimeForPreparing) {
        this.setRecepyID(recepyID);
        this.setProductID(productID);
        this.setProductQuantity(productQuantity);
        this.setProductTimeForPreparing(productTimeForPreparing);
    }

    public int getRecepyID() {
        return recepyID;
    }

    public void setRecepyID(int recepyID) {
        this.recepyID = recepyID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public double getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(double productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getProductTimeForPreparing() {
        return productTimeForPreparing;
    }

    public void setProductTimeForPreparing(int productTimeForPreparing) {
        this.productTimeForPreparing = productTimeForPreparing;
    }
}
