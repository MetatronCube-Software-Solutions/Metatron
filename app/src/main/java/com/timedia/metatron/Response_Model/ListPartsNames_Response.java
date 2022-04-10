package com.timedia.metatron.Response_Model;

import java.util.ArrayList;

public class ListPartsNames_Response {

    private ServerMsg ServerMsg;

    private ArrayList<PartsList> PartsList;

    public ServerMsg getServerMsg() {
        return ServerMsg;
    }

    public void setServerMsg(ServerMsg serverMsg) {
        ServerMsg = serverMsg;
    }

    public ArrayList<com.timedia.metatron.Response_Model.PartsList> getPartsList() {
        return PartsList;
    }

    public void setPartsList(ArrayList<com.timedia.metatron.Response_Model.PartsList> partsList) {
        PartsList = partsList;
    }

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
