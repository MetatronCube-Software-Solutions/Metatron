package com.timedia.metatron.Presenters;

import android.content.Context;

import com.timedia.metatron.IPresenters.IPresenters;
import com.timedia.metatron.Interfaces.ITaskFinishListener;
import com.timedia.metatron.Interfaces.SwitchConstantKeys;
import com.timedia.metatron.Presenter_Views.IViews;
import com.timedia.metatron.Response_Model.UserRegister_Request;
import com.timedia.metatron.Tasks.GetUserLoginTask;

import java.io.IOException;


public class Validation_ShopregPresenter implements IPresenters.UserShopReg_Presenters {

    IViews.AddUserShopReg mUserRegisterViews;
    ITaskFinishListener mFinish_Listener;


    public Validation_ShopregPresenter(IViews.AddUserShopReg aLoginResponse) {

        mUserRegisterViews = aLoginResponse;
        mFinish_Listener = new ITaskFinishListener() {
            @Override
            public void onFinished(int logicType, Object obj) {
                if (logicType == SwitchConstantKeys.UserRegister.RegisterSuccess) {
                    mUserRegisterViews.RegisterShopsuccess(SwitchConstantKeys.UserRegister.RegisterSuccess, obj);
                } else if (logicType == SwitchConstantKeys.UserRegister.Register_Failure) {
                    mUserRegisterViews.RegisterShopsuccess(SwitchConstantKeys.UserRegister.Register_Failure, obj);
                } else if (logicType == SwitchConstantKeys.UserRegister.Internet_Failure) {
                    mUserRegisterViews.RegisterShopsuccess(SwitchConstantKeys.UserRegister.Internet_Failure, obj);
                } else if (logicType == SwitchConstantKeys.UserRegister.SomethingError) {
                    mUserRegisterViews.RegisterShopsuccess(SwitchConstantKeys.UserRegister.SomethingError, obj);
                }
            }
        };
    }


    @Override
    public void RegisterValidation(Context aContext, UserRegister_Request aRequest) {
        try {
            GetUserLoginTask aUserLoginTask = new GetUserLoginTask(aContext, mFinish_Listener);
            aUserLoginTask.UserRegistration(aRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

