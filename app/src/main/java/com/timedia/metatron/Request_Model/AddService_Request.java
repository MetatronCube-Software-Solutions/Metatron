package com.timedia.metatron.Request_Model;

public class AddService_Request {

    private String cost;

    private String createdby;

    private String createddate;

    private String timing;

    private String name;

    public String getCost ()
    {
        return cost;
    }

    public void setCost (String cost)
    {
        this.cost = cost;
    }

    public String getCreatedby ()
    {
        return createdby;
    }

    public void setCreatedby (String createdby)
    {
        this.createdby = createdby;
    }

    public String getCreateddate ()
    {
        return createddate;
    }

    public void setCreateddate (String createddate)
    {
        this.createddate = createddate;
    }

    public String getTiming ()
    {
        return timing;
    }

    public void setTiming (String timing)
    {
        this.timing = timing;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }
}
