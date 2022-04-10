package com.timedia.metatron.Response_Model;

public class ServerMessage_Response {

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
    private String SiteLogo;

    public String getLogo() {
        return SiteLogo;
    }

    public void setLogo(String logo) {
        SiteLogo = logo;
    }
}
