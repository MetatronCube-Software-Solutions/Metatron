package com.timedia.metatron.Request_Model;

public class Changepassword_Request {
    private String oldpassword;

    private String newpassword;

    private String userid;

    public String getOldpassword ()
    {
        return oldpassword;
    }

    public void setOldpassword (String oldpassword)
    {
        this.oldpassword = oldpassword;
    }

    public String getNewpassword ()
    {
        return newpassword;
    }

    public void setNewpassword (String newpassword)
    {
        this.newpassword = newpassword;
    }

    public String getUserid ()
    {
        return userid;
    }

    public void setUserid (String userid)
    {
        this.userid = userid;
    }

}
