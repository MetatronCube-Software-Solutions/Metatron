package com.timedia.metatron.Response_Model;

import java.util.ArrayList;

public class ListService_Response {

    private ServerMsg ServerMsg;

    public ListService_Response.ServerMsg getServerMsg() {
        return ServerMsg;
    }

    public void setServerMsg(ListService_Response.ServerMsg serverMsg) {
        ServerMsg = serverMsg;
    }

    public ArrayList<ServicesList> getServicesList() {
        return ServicesList;
    }

    public void setServicesList(ArrayList<ServicesList> servicesList) {
        ServicesList = servicesList;
    }

    private ArrayList<ServicesList> ServicesList;

    public class ServerMsg {
        private String Msg;

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

        private String DisplayMsg;

        private String ExMsg;

    }
}
