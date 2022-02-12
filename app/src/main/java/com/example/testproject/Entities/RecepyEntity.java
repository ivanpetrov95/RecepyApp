package com.example.testproject.Entities;

import java.io.Serializable;

public class RecepyEntity implements Serializable
{
    private int ID;
    private String recepyName;
    private String recepyDescription;

    public RecepyEntity(int ID, String recepyName, String recepyDescription)
    {
        this.setID(ID);
        this.setRecepyName(recepyName);
        this.setRecepyDescription(recepyDescription);
    }

    public String getRecepyName()
    {
        return this.recepyName;
    }

    public void setRecepyName(String recepyName)
    {
        this.recepyName = recepyName;
    }

    public String getRecepyDescription()
    {
        return this.recepyDescription;
    }

    public void setRecepyDescription(String recepyDescription)
    {
        this.recepyDescription = recepyDescription;
    }

    public int getId() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
