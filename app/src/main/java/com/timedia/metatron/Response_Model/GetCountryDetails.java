package com.timedia.metatron.Response_Model;

import java.util.ArrayList;

public class GetCountryDetails {
    private ServerMsg ServerMsg;

    private ArrayList<LocationsList> LocationsList;

    public GetCountryDetails.ServerMsg getServerMsg() {
        return ServerMsg;
    }

    public void setServerMsg(GetCountryDetails.ServerMsg serverMsg) {
        ServerMsg = serverMsg;
    }

    public ArrayList<com.timedia.metatron.Response_Model.LocationsList> getLocationsList() {
        return LocationsList;
    }

    public void setLocationsList(ArrayList<com.timedia.metatron.Response_Model.LocationsList> locationsList) {
        LocationsList = locationsList;
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
