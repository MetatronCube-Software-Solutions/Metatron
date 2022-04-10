package com.timedia.metatron.Response_Model;

public class AddService_Response {

    private ServerMsg ServerMsg;

    private String Service;

    public ServerMsg getServerMsg ()
    {
        return ServerMsg;
    }

    public void setServerMsg (ServerMsg ServerMsg)
    {
        this.ServerMsg = ServerMsg;
    }

    public String getService ()
    {
        return Service;
    }

    public void setService (String Service)
    {
        this.Service = Service;
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
