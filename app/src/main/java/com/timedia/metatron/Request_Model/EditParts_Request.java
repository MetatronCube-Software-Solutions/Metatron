package com.timedia.metatron.Request_Model;

public class EditParts_Request {
    private String cost;

    private String partid;

    private String name;

    private String modifieddate;

    private String modifiedby;

    private String status;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    private String make;
    private String model;
    private String serial;
    private String warranty;

    public String getCost ()
    {
        return cost;
    }

    public void setCost (String cost)
    {
        this.cost = cost;
    }

    public String getPartid ()
    {
        return partid;
    }

    public void setPartid (String partid)
    {
        this.partid = partid;
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

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

}
