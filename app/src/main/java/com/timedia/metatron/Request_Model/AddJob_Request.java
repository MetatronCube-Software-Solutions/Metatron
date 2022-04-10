package com.timedia.metatron.Request_Model;

import com.timedia.metatron.Response_Model.ServicesList;

import java.util.ArrayList;

public class AddJob_Request {


    private ArrayList<ServicesList> ServicesList;

    private String techid;

    public ArrayList<com.timedia.metatron.Response_Model.ServicesList> getServicesList() {
        return ServicesList;
    }

    public void setServicesList(ArrayList<com.timedia.metatron.Response_Model.ServicesList> servicesList) {
        ServicesList = servicesList;
    }

    private String address;

    private String city;

    private String createddate;

    private String mobile;

    private String province;

    private String phone;

    private String createdby;

    private String name;

    private String workdesc;

    private String callpromised;

    private String email;

    private String callreceived;

    private String findus;

    public String getTechid ()
    {
        return techid;
    }

    public void setTechid (String techid)
    {
        this.techid = techid;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getCity ()
    {
        return city;
    }

    public void setCity (String city)
    {
        this.city = city;
    }

    public String getCreateddate ()
    {
        return createddate;
    }

    public void setCreateddate (String createddate)
    {
        this.createddate = createddate;
    }

    public String getMobile ()
    {
        return mobile;
    }

    public void setMobile (String mobile)
    {
        this.mobile = mobile;
    }

    public String getProvince ()
    {
        return province;
    }

    public void setProvince (String province)
    {
        this.province = province;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getCreatedby ()
    {
        return createdby;
    }

    public void setCreatedby (String createdby)
    {
        this.createdby = createdby;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getWorkdesc ()
    {
        return workdesc;
    }

    public void setWorkdesc (String workdesc)
    {
        this.workdesc = workdesc;
    }

    public String getCallpromised ()
    {
        return callpromised;
    }

    public void setCallpromised (String callpromised)
    {
        this.callpromised = callpromised;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getCallreceived ()
    {
        return callreceived;
    }

    public void setCallreceived (String callreceived)
    {
        this.callreceived = callreceived;
    }

    public String getFindus ()
    {
        return findus;
    }

    public void setFindus (String findus)
    {
        this.findus = findus;
    }
}
