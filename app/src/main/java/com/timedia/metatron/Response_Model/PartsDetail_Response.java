package com.timedia.metatron.Response_Model;

public class PartsDetail_Response {

    private ServerMsg ServerMsg;

    private PartsData PartsData;

    public ServerMsg getServerMsg ()
    {
        return ServerMsg;
    }

    public void setServerMsg (ServerMsg ServerMsg)
    {
        this.ServerMsg = ServerMsg;
    }

    public PartsData getPartsData ()
    {
        return PartsData;
    }

    public void setPartsData (PartsData PartsData)
    {
        this.PartsData = PartsData;
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

    public class PartsData {
        private String part_warranty;

        private String part_model;

        private String part_createdby;

        private String part_type;

        private String part_modifieddate;

        private String part_make;

        private String part_modifiedby;

        private String part_serial;

        public String getPart_warranty() {
            return part_warranty;
        }

        public void setPart_warranty(String part_warranty) {
            this.part_warranty = part_warranty;
        }

        public String getPart_model() {
            return part_model;
        }

        public void setPart_model(String part_model) {
            this.part_model = part_model;
        }

        public String getPart_createdby() {
            return part_createdby;
        }

        public void setPart_createdby(String part_createdby) {
            this.part_createdby = part_createdby;
        }

        public String getPart_type() {
            return part_type;
        }

        public void setPart_type(String part_type) {
            this.part_type = part_type;
        }

        public String getPart_modifieddate() {
            return part_modifieddate;
        }

        public void setPart_modifieddate(String part_modifieddate) {
            this.part_modifieddate = part_modifieddate;
        }

        public String getPart_make() {
            return part_make;
        }

        public void setPart_make(String part_make) {
            this.part_make = part_make;
        }

        public String getPart_modifiedby() {
            return part_modifiedby;
        }

        public void setPart_modifiedby(String part_modifiedby) {
            this.part_modifiedby = part_modifiedby;
        }

        public String getPart_serial() {
            return part_serial;
        }

        public void setPart_serial(String part_serial) {
            this.part_serial = part_serial;
        }

        public String getPart_id() {
            return part_id;
        }

        public void setPart_id(String part_id) {
            this.part_id = part_id;
        }

        public String getPart_cost() {
            return part_cost;
        }

        public void setPart_cost(String part_cost) {
            this.part_cost = part_cost;
        }

        public String getPart_createddate() {
            return part_createddate;
        }

        public void setPart_createddate(String part_createddate) {
            this.part_createddate = part_createddate;
        }

        public String getPart_name() {
            return part_name;
        }

        public void setPart_name(String part_name) {
            this.part_name = part_name;
        }

        public String getPart_status() {
            return part_status;
        }

        public void setPart_status(String part_status) {
            this.part_status = part_status;
        }

        private String part_id;

        private String part_cost;

        private String part_createddate;

        private String part_name;

        private String part_status;

    }

}
