package com.timedia.metatron.shared_data;

import com.timedia.metatron.Interfaces.IPreferenceKeys;

public class PreferenceDetails {
    protected static final PreferenceManager mPrefsMgr = PreferenceManager.getInstance();


    public static String GetUserid() {
        return mPrefsMgr.getString(IPreferenceKeys.UserDetail.UserId, IPreferenceKeys.Common.EMPTY).trim();
    }
    public static String GetSitedataid() {
        return mPrefsMgr.getString(IPreferenceKeys.SiteData.SiteDataId, IPreferenceKeys.Common.EMPTY).trim();
    }

    public static String GetUserEmployeeId() {
        return mPrefsMgr.getString(IPreferenceKeys.UserDetail.UserEmpId, IPreferenceKeys.Common.EMPTY).trim();
    }

    public static String GetUserEmail() {
        return mPrefsMgr.getString(IPreferenceKeys.UserDetail.UserEmail, IPreferenceKeys.Common.EMPTY).trim();
    }

    public static String GetUserMobile() {
        return mPrefsMgr.getString(IPreferenceKeys.UserDetail.UserMobile, IPreferenceKeys.Common.EMPTY).trim();
    }

    public static String GetUserType() {
        return mPrefsMgr.getString(IPreferenceKeys.UserDetail.UserType, IPreferenceKeys.Common.EMPTY).trim();
    }

    public static String GetUserFname() {
        return mPrefsMgr.getString(IPreferenceKeys.UserDetail.UserFname, IPreferenceKeys.Common.EMPTY).trim();
    }

    public static String GetUserLname() {
        return mPrefsMgr.getString(IPreferenceKeys.UserDetail.UserLname, IPreferenceKeys.Common.EMPTY).trim();
    }
    public static String GetUsername() {
        return mPrefsMgr.getString(IPreferenceKeys.UserDetail.Username, IPreferenceKeys.Common.EMPTY).trim();
    }

    public static String GetProfileImage() {
        return mPrefsMgr.getString(IPreferenceKeys.UserDetail.UserLogo, IPreferenceKeys.Common.EMPTY).trim();
    }

}
