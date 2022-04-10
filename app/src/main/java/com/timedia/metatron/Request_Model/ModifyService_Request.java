package com.timedia.metatron.Request_Model;

public class ModifyService_Request {

    private String cost;

    private String timing;

    private String name;

    private String modifieddate;

    private String modifiedby;

    private String serviceid;

    private String status;

    public String getCost ()
    {
        return cost;
    }

    public void setCost (String cost)
    {
        this.cost = cost;
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

    public String getModifieddate ()
    {
        return modifieddate;
    }

    public void setModifieddate (String modifieddate)
    {
        this.modifieddate = modifieddate;
    }

    public String getModifiedby ()
    {
        return modifiedby;
    }

    public void setModifiedby (String modifiedby)
    {
        this.modifiedby = modifiedby;
    }

    public String getServiceid ()
    {
        return serviceid;
    }

    public void setServiceid (String serviceid)
    {
        this.serviceid = serviceid;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }
}
