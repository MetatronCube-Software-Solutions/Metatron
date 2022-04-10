package com.timedia.metatron.Response_Model;

public class AddUser_Response {
    private String User;

    private ServerMsg ServerMsg;

    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }

    public ServerMsg getServerMsg() {
        return ServerMsg;
    }

    public void setServerMsg(ServerMsg ServerMsg) {
        this.ServerMsg = ServerMsg;
    }

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
