package com.timedia.metatron.IPresenters;

import android.content.Context;

import com.timedia.metatron.Request_Model.AddParts_Request;
import com.timedia.metatron.Request_Model.AddUser_Request;
import com.timedia.metatron.Request_Model.EditParts_Request;
import com.timedia.metatron.Request_Model.UserLogin_Request;
import com.timedia.metatron.Response_Model.UserRegister_Request;

public interface IPresenters {

    interface UserLogin_Presenters {
        void LoginValidation(Context aContext, UserLogin_Request aRequest);
    }

    interface UserShopReg_Presenters {
        void RegisterValidation(Context aContext, UserRegister_Request aRequest);
    }

    interface AddUser_Presenters {
        void AddUser_Registration(Context aContext, AddUser_Request aRequest);
    }

    interface UserParts_Presenters {
        void ListParts(Context aContext);

        void RemoveParts(Context aContext, String aPartid);

        void AddParts(Context aContext, AddParts_Request aRequest);

        void ModifyParts(Context aContext, EditParts_Request aRequest);
    }

    interface AdminServices_Presenters {

        void ListOfServices(Context aContext);

    }

}
