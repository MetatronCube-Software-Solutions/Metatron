package com.timedia.metatron.Response_Model;

import java.util.ArrayList;

public class Job_ServiceList {


    private ServerMsg ServerMsg;

    public Job_ServiceList.ServerMsg getServerMsg() {
        return ServerMsg;
    }

    public void setServerMsg(Job_ServiceList.ServerMsg serverMsg) {
        ServerMsg = serverMsg;
    }

    public ArrayList<com.timedia.metatron.Response_Model.ServicesList> getServicesList() {
        return ServicesList;
    }

    public void setServicesList(ArrayList<com.timedia.metatron.Response_Model.ServicesList> servicesList) {
        ServicesList = servicesList;
    }

    private ArrayList<ServicesList> ServicesList;

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

}
