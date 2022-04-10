package com.timedia.metatron.Response_Model;

import java.util.ArrayList;

public class Parts_List_Response {
    public ServerMsg ServerMsg;

    public ArrayList<PartsList> PartsList;

    public ServerMsg getServerMsg() {
        return ServerMsg;
    }

    public void setServerMsg(ServerMsg serverMsg) {
        ServerMsg = serverMsg;
    }

    public ArrayList<PartsList> getPartsList() {
        return PartsList;
    }

    public void setPartsList(ArrayList<PartsList> partsList) {
        PartsList = partsList;
    }

    public class ServerMsg {
        private String Msg;

        private String DisplayMsg;

        private String ExMsg;

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
    }
}
