package com.timedia.metatron.Request_Model;

import com.timedia.metatron.Response_Model.PartsList;
import com.timedia.metatron.Response_Model.ServicesList;

import java.util.ArrayList;

public class ModiyJobs_Request {
    private String submittype;
    private String paymentmode;
    private String customersign;
    private String techsign;

    public String getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        this.paymentmode = paymentmode;
    }

    public String getCustomersign() {
        return customersign;
    }

    public void setCustomersign(String customersign) {
        this.customersign = customersign;
    }

    public String getTechsign() {
        return techsign;
    }

    public void setTechsign(String techsign) {
        this.techsign = techsign;
    }

    public String getSubmittype() {
        return submittype;
    }

    public void setSubmittype(String submittype) {
        this.submittype = submittype;
    }

    private String techid;

    private String address;

    private String city;

    private String mobile;

    private String modifieddate;

    private String jobid;

    private String province;

    private String phone;

    private String name;

    private String workdesc;

    private String modifiedby;

    private String callpromised;

    private String email;

    private String callreceived;

    private String findus;

    private String status;

    public String getTechid() {
        return techid;
    }

    public void setTechid(String techid) {
        this.techid = techid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getModifieddate() {
        return modifieddate;
    }

    public void setModifieddate(String modifieddate) {
        this.modifieddate = modifieddate;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkdesc() {
        return workdesc;
    }

    public void setWorkdesc(String workdesc) {
        this.workdesc = workdesc;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    public String getCallpromised() {
        return callpromised;
    }

    public void setCallpromised(String callpromised) {
        this.callpromised = callpromised;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCallreceived() {
        return callreceived;
    }

    public void setCallreceived(String callreceived) {
        this.callreceived = callreceived;
    }

    public String getFindus() {
        return findus;
    }

    public void setFindus(String findus) {
        this.findus = findus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<com.timedia.metatron.Response_Model.ServicesList> getServicesList() {
        return ServicesList;
    }

    public void setServicesList(ArrayList<com.timedia.metatron.Response_Model.ServicesList> servicesList) {
        ServicesList = servicesList;
    }

    private ArrayList<com.timedia.metatron.Response_Model.ServicesList> ServicesList;
    private ArrayList<com.timedia.metatron.Response_Model.PartsList> PartsList;

    public ArrayList<com.timedia.metatron.Response_Model.PartsList> getPartsList() {
        return PartsList;
    }

    public void setPartsList(ArrayList<com.timedia.metatron.Response_Model.PartsList> partsList) {
        PartsList = partsList;
    }
}
