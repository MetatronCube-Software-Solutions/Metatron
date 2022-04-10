package com.timedia.metatron.Interfaces;

public interface IPreferenceKeys {

    interface UserDetail {
        String FirstTimeUser = "FirstTimeUser";
        String UserId = "UserId";
        String Username = "Username";
        String UserType = "UserType";
        String UserEmpId = "UserEmpId";
        String UserFname = "UserFname";
        String UserLname = "UserLname";
        String UserMobile = "UserMobile";
        String UserEmail = "UserEmail";
        String LoginResponse = "UserLoginresponse";
        String UserLogo = "UserLogo";
    }

    interface SiteData{
        String SiteDataId="SiteDataId";
    }

    interface Support {
        String Phone = "Phone";
        String Email = "Email";
        String Contact = "Contact";
    }

    interface Common {
        String EMPTY = " ";
        String DATEFORMAT = "MM-dd-yyyy";
        String TIMEFORMAT = "HH:mm";
    }

    interface PreferenceMgr {
        String IsLoggedIn = "IsLoggedIn";
        String UserType = "UserType";
    }

}
