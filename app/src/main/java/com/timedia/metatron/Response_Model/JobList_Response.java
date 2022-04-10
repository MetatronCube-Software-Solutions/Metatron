package com.timedia.metatron.Response_Model;

import java.util.ArrayList;

public class JobList_Response {


    private ServerMsg ServerMsg;

    public ServerMsg getServerMsg() {
        return ServerMsg;
    }

    public void setServerMsg(ServerMsg serverMsg) {
        ServerMsg = serverMsg;
    }

    public ArrayList<JobsList> getJobsList() {
        return JobsList;
    }

    public void setJobsList(ArrayList<JobsList> jobsList) {
        JobsList = jobsList;
    }

    private ArrayList<JobsList> JobsList;



    public class ServerMsg {
        private String Msg;

        private String DisplayMsg;

        public String getMsg() {
            return Msg;
        }

        public void setMsg(String msg) {
            Msg = msg;
        }

        public String getDisplayMsg() {
            return DisplayMsg;
        }

        public void setDisplayMsg(String displayMsg) {
            DisplayMsg = displayMsg;
        }

        public String getExMsg() {
            return ExMsg;
        }

        public void setExMsg(String exMsg) {
            ExMsg = exMsg;
        }

        private String ExMsg;

    }

    public class JobsList {

        private String cost;

        public String getJob_invoice() {
            return job_invoice;
        }

        public void setJob_invoice(String job_invoice) {
            this.job_invoice = job_invoice;
        }

        private String job_invoice;

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        private ArrayList<ServicesList> ServicesList;

        private ArrayList<PartsList> PartsList;

        public ArrayList<PartsList> getPartsList() {
            return PartsList;
        }

        public void setPartsList(ArrayList<PartsList> partsList) {
            PartsList = partsList;
        }

        public ArrayList<com.timedia.metatron.Response_Model.ServicesList> getServicesList() {
            return ServicesList;
        }

        public void setServicesList(ArrayList<com.timedia.metatron.Response_Model.ServicesList> servicesList) {
            ServicesList = servicesList;
        }

        private String job_createdby;

        private String job_email;

        private String job_callpromised;

        private String job_mobile;

        private String job_province;

        private String job_customername;

        private String job_pincode;

        private String job_address;

        private String job_callreceived;

        private String job_workdesc;

        private String job_status;

        private String job_workdate;

        private String job_modifieddate;

        private String job_completedby;

        private String job_id;

        private String job_findus;

        private String job_city;

        private String job_createddate;

        private String job_iscompleted;

        private String job_techid;

        private String job_cost;

        private String job_modifiedby;

        private String job_phone;

        public String getJob_createdby() {
            return job_createdby;
        }

        public void setJob_createdby(String job_createdby) {
            this.job_createdby = job_createdby;
        }

        public String getJob_email() {
            return job_email;
        }

        public void setJob_email(String job_email) {
            this.job_email = job_email;
        }

        public String getJob_callpromised() {
            return job_callpromised;
        }

        public void setJob_callpromised(String job_callpromised) {
            this.job_callpromised = job_callpromised;
        }

        public String getJob_mobile() {
            return job_mobile;
        }

        public void setJob_mobile(String job_mobile) {
            this.job_mobile = job_mobile;
        }

        public String getJob_province() {
            return job_province;
        }

        public void setJob_province(String job_province) {
            this.job_province = job_province;
        }

        public String getJob_customername() {
            return job_customername;
        }

        public void setJob_customername(String job_customername) {
            this.job_customername = job_customername;
        }

        public String getJob_pincode() {
            return job_pincode;
        }

        public void setJob_pincode(String job_pincode) {
            this.job_pincode = job_pincode;
        }

        public String getJob_address() {
            return job_address;
        }

        public void setJob_address(String job_address) {
            this.job_address = job_address;
        }

        public String getJob_callreceived() {
            return job_callreceived;
        }

        public void setJob_callreceived(String job_callreceived) {
            this.job_callreceived = job_callreceived;
        }

        public String getJob_workdesc() {
            return job_workdesc;
        }

        public void setJob_workdesc(String job_workdesc) {
            this.job_workdesc = job_workdesc;
        }

        public String getJob_status() {
            return job_status;
        }

        public void setJob_status(String job_status) {
            this.job_status = job_status;
        }

        public String getJob_workdate() {
            return job_workdate;
        }

        public void setJob_workdate(String job_workdate) {
            this.job_workdate = job_workdate;
        }

        public String getJob_modifieddate() {
            return job_modifieddate;
        }

        public void setJob_modifieddate(String job_modifieddate) {
            this.job_modifieddate = job_modifieddate;
        }

        public String getJob_completedby() {
            return job_completedby;
        }

        public void setJob_completedby(String job_completedby) {
            this.job_completedby = job_completedby;
        }

        public String getJob_id() {
            return job_id;
        }

        public void setJob_id(String job_id) {
            this.job_id = job_id;
        }

        public String getJob_findus() {
            return job_findus;
        }

        public void setJob_findus(String job_findus) {
            this.job_findus = job_findus;
        }

        public String getJob_city() {
            return job_city;
        }

        public void setJob_city(String job_city) {
            this.job_city = job_city;
        }

        public String getJob_createddate() {
            return job_createddate;
        }

        public void setJob_createddate(String job_createddate) {
            this.job_createddate = job_createddate;
        }

        public String getJob_iscompleted() {
            return job_iscompleted;
        }

        public void setJob_iscompleted(String job_iscompleted) {
            this.job_iscompleted = job_iscompleted;
        }

        public String getJob_techid() {
            return job_techid;
        }

        public void setJob_techid(String job_techid) {
            this.job_techid = job_techid;
        }

        public String getJob_cost() {
            return job_cost;
        }

        public void setJob_cost(String job_cost) {
            this.job_cost = job_cost;
        }

        public String getJob_modifiedby() {
            return job_modifiedby;
        }

        public void setJob_modifiedby(String job_modifiedby) {
            this.job_modifiedby = job_modifiedby;
        }

        public String getJob_phone() {
            return job_phone;
        }

        public void setJob_phone(String job_phone) {
            this.job_phone = job_phone;
        }


    }
    public class PartsList {
        private String jobsp_id;

        private String part_id;

        private String part_cost;

        private String part_name;

        public String getJobsp_id() {
            return jobsp_id;
        }

        public void setJobsp_id(String jobsp_id) {
            this.jobsp_id = jobsp_id;
        }

        public String getPart_id() {
            return part_id;
        }

        public void setPart_id(String part_id) {
            this.part_id = part_id;
        }

        public String getPart_cost() {
            return part_cost;
        }

        public void setPart_cost(String part_cost) {
            this.part_cost = part_cost;
        }

        public String getPart_name() {
            return part_name;
        }

        public void setPart_name(String part_name) {
            this.part_name = part_name;
        }
    }
    }
