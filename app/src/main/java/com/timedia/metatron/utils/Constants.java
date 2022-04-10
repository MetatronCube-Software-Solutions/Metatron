package com.timedia.metatron.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import java.util.HashMap;


@SuppressLint("Registered")
public class Constants extends MultiDexApplication {

    public Context context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setContext(this);
    }

    public void setContext(Context con) {
        context = con;
    }

    public Context getContext() {
        return context;
    }

    //public static final String BASE_URL = "http://angkattechnologies.com/projects/metatronthermo/app/";
    //public static final String BASE_URL = "http://metatroncs.com/projects/app/jobtack/app/";
    public static final String BASE_URL = "http://jobtack.metatroncubesolutions.com/projects/app/jobtack/app/";
    public static final String UserLoginValidation = "Users/UserLoginValidate";
    public static final String AddUsers = "Users/AddUsers";
    public static final String Changepassword = "Users/ChangePassword";
    public static final String ForgotPassword = "Users/ForgotPassword";
    public static final String ListUsers = "Users/ListOfUsers";
    public static final String RemoveUsers = "Users/RemoveUsers";
    public static final String ModifyUsers = "Users/ModifyUsers";
    public static final String IsUniqueUser = "Users/IsUniqueUserID?";
    public static final String GetListOfParts = "Parts/ListOfParts";
    public static final String RemoveParts = "Parts/RemovePart?";
    public static final String AddParts = "Parts/AddPart";
    public static final String ModifyParts = "Parts/ModifyPart";
    public static final String ListPartNames = "Parts/Partnames";
    public static final String ListOfServices = "Services/ListOfServices";
    public static final String AddService = "Services/AddService";
    public static final String RemoveService = "Services/RemoveService?";
    public static final String ModifyService = "Services/ModifyService";
    public static final String ServiceListNames = "Services/Servicenames";
    public static final String AddJob = "Jobs/AddJob";
    public static final String RemoveJob = "Jobs/RemoveJob";
    public static final String ListJobs = "Jobs/ListOfJobs";
    public static final String ModiyJobs = "Jobs/ModifyJob";
    public static final String TechnicianList = "Users/ListOfUsernameID";
    public static final String Assign_Technician = "Jobs/AssignTechnician";
    public static final String GetPartsDetail = "Parts/GetPartDetail";
    public static final String UpdateSiteData = "Users/SiteData";
    public static final String UserRegistration = "Shops/ShopRegistration";
    public static final String CountryDetails = "Users/GetLocation";

    public static int mAdd_PartsOnclick = 0;
    public static int mAdd_ServiceOnclick = 0;
    public static int mAdd_JobsOnclick = 0;
    public static int mSubmitUnAssignedJobOnclick = 0;
    public static int mAdd_UsersOnclick = 0;
    public static int mShowServices = 0;
    public static int mShowServicesAssigned = 0;
    public static int mShowServicesUnAssigned = 0;
    public static int mShowParts = 0;
    public static HashMap<Integer, String> mChecked_Position = new HashMap<>();
    public static HashMap<Integer, String> mChecked_Position_Parts = new HashMap<>();

}
