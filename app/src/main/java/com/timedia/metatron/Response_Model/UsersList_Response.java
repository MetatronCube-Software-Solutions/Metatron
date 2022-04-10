package com.timedia.metatron.Response_Model;

import java.util.ArrayList;

public class UsersList_Response {

    public ArrayList<UsersList> UsersList;

    private ServerMsg ServerMsg;

    public ArrayList<UsersList_Response.UsersList> getUsersList() {
        return UsersList;
    }

    public void setUsersList(ArrayList<UsersList_Response.UsersList> usersList) {
        UsersList = usersList;
    }

    public UsersList_Response.ServerMsg getServerMsg() {
        return ServerMsg;
    }

    public void setServerMsg(UsersList_Response.ServerMsg serverMsg) {
        ServerMsg = serverMsg;
    }

    public class UsersList {
        private String user_status;

        private String user_email;

        private String user_password;

        private String user_empid;

        private String user_firstname;

        private String user_username;

        private String user_lastname;

        private String user_modifieddate;

        private String user_type;

        private String user_id;

        private String user_mobile;

        private String user_createddate;

        private String user_createdby;

        private String user_modifiedby;

        public String getUser_status() {
            return user_status;
        }

        public void setUser_status(String user_status) {
            this.user_status = user_status;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public String getUser_password() {
            return user_password;
        }

        public void setUser_password(String user_password) {
            this.user_password = user_password;
        }

        public String getUser_empid() {
            return user_empid;
        }

        public void setUser_empid(String user_empid) {
            this.user_empid = user_empid;
        }

        public String getUser_firstname() {
            return user_firstname;
        }

        public void setUser_firstname(String user_firstname) {
            this.user_firstname = user_firstname;
        }

        public String getUser_username() {
            return user_username;
        }

        public void setUser_username(String user_username) {
            this.user_username = user_username;
        }

        public String getUser_lastname() {
            return user_lastname;
        }

        public void setUser_lastname(String user_lastname) {
            this.user_lastname = user_lastname;
        }

        public String getUser_modifieddate() {
            return user_modifieddate;
        }

        public void setUser_modifieddate(String user_modifieddate) {
            this.user_modifieddate = user_modifieddate;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_mobile() {
            return user_mobile;
        }

        public void setUser_mobile(String user_mobile) {
            this.user_mobile = user_mobile;
        }

        public String getUser_createddate() {
            return user_createddate;
        }

        public void setUser_createddate(String user_createddate) {
            this.user_createddate = user_createddate;
        }

        public String getUser_createdby() {
            return user_createdby;
        }

        public void setUser_createdby(String user_createdby) {
            this.user_createdby = user_createdby;
        }

        public String getUser_modifiedby() {
            return user_modifiedby;
        }

        public void setUser_modifiedby(String user_modifiedby) {
            this.user_modifiedby = user_modifiedby;
        }

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
