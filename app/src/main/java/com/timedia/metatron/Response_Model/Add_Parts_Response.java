package com.timedia.metatron.Response_Model;

public class Add_Parts_Response {

    private ServerMsg ServerMsg;

    private String Part;

    public ServerMsg getServerMsg ()
    {
        return ServerMsg;
    }

    public void setServerMsg (ServerMsg ServerMsg)
    {
        this.ServerMsg = ServerMsg;
    }

    public String getPart ()
    {
        return Part;
    }

    public void setPart (String Part)
    {
        this.Part = Part;
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
