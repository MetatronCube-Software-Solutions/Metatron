package com.timedia.metatron.Response_Model;

public class UserRegistration_Response {

    private SiteData SiteData;

    private ServerMsg ServerMsg;

    private LoginReturn LoginReturn;

    public Support Support;

    public UserRegistration_Response.Support getSupport() {
        return Support;
    }

    public void setSupport(UserRegistration_Response.Support support) {
        Support = support;
    }

    public SiteData getSiteData() {
        return SiteData;
    }

    public void setSiteData(SiteData SiteData) {
        this.SiteData = SiteData;
    }

    public ServerMsg getServerMsg() {
        return ServerMsg;
    }

    public void setServerMsg(ServerMsg ServerMsg) {
        this.ServerMsg = ServerMsg;
    }

    public LoginReturn getLoginReturn() {
        return LoginReturn;
    }

    public void setLoginReturn(LoginReturn LoginReturn) {
        this.LoginReturn = LoginReturn;
    }

    public class Support {

        private String Phone;
        private String Email;

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String phone) {
            Phone = phone;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public String getContact() {
            return Contact;
        }

        public void setContact(String contact) {
            Contact = contact;
        }

        private String Contact;
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

    public class SiteData {
        private String Email;

        private String Address;

        private String HST;

        private String City;

        private String Mobile;

        private String Name;

        private String Logo;

        private String TollFree;

        private String Phone;

        private String State;

        private String WebAddress;

        private String Zipcode;

        private String Country;

        private String Id;

        private String Fax;

        public String getEmail() {
            return Email;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getHST() {
            return HST;
        }

        public void setHST(String HST) {
            this.HST = HST;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String City) {
            this.City = City;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getLogo() {
            return Logo;
        }

        public void setLogo(String Logo) {
            this.Logo = Logo;
        }

        public String getTollFree() {
            return TollFree;
        }

        public void setTollFree(String TollFree) {
            this.TollFree = TollFree;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }

        public String getWebAddress() {
            return WebAddress;
        }

        public void setWebAddress(String WebAddress) {
            this.WebAddress = WebAddress;
        }

        public String getZipcode() {
            return Zipcode;
        }

        public void setZipcode(String Zipcode) {
            this.Zipcode = Zipcode;
        }

        public String getCountry() {
            return Country;
        }

        public void setCountry(String Country) {
            this.Country = Country;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getFax() {
            return Fax;
        }

        public void setFax(String Fax) {
            this.Fax = Fax;
        }

    }

    public class LoginReturn {
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

        private String user_shop;

        private String user_createddate;

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

        public String getUser_shop() {
            return user_shop;
        }

        public void setUser_shop(String user_shop) {
            this.user_shop = user_shop;
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

        private String user_createdby;

        private String user_modifiedby;


    }

}
