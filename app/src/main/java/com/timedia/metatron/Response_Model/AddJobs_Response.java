package com.timedia.metatron.Response_Model;

public class AddJobs_Response {

    private ServerMsg ServerMsg;

    private String Job;

    public ServerMsg getServerMsg ()
    {
        return ServerMsg;
    }

    public void setServerMsg (ServerMsg ServerMsg)
    {
        this.ServerMsg = ServerMsg;
    }

    public String getJob ()
    {
        return Job;
    }

    public void setJob (String Job)
    {
        this.Job = Job;
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
