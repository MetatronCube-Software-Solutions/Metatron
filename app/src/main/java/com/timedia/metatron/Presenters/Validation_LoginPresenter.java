package com.timedia.metatron.Presenters;

import android.content.Context;

import com.timedia.metatron.IPresenters.IPresenters;
import com.timedia.metatron.Interfaces.ITaskFinishListener;
import com.timedia.metatron.Interfaces.SwitchConstantKeys;
import com.timedia.metatron.Presenter_Views.IViews;
import com.timedia.metatron.Request_Model.UserLogin_Request;
import com.timedia.metatron.Response_Model.UserRegister_Request;
import com.timedia.metatron.Tasks.GetUserLoginTask;

import java.io.IOException;

  public class Validation_LoginPresenter implements IPresenters.UserLogin_Presenters{

    IViews.GetUserLoginDetails mUserViews;
    ITaskFinishListener mFinish_Listener;


    public Validation_LoginPresenter(IViews.GetUserLoginDetails aLoginResponse) {

        mUserViews = aLoginResponse;
        mFinish_Listener = new ITaskFinishListener() {
            @Override
            public void onFinished(int logicType, Object obj) {
                if (logicType == SwitchConstantKeys.UserLogin.Login_Success) {
                    mUserViews.UserLoginSuccess(SwitchConstantKeys.UserLogin.Login_Success, obj);
                } else if (logicType == SwitchConstantKeys.UserLogin.Login_Failure) {
                    mUserViews.UserError(SwitchConstantKeys.UserLogin.Login_Failure, obj);
                } else if (logicType == SwitchConstantKeys.UserLogin.Internet_Failure) {
                    mUserViews.UserError(SwitchConstantKeys.UserLogin.Internet_Failure, obj);
                } else if (logicType == SwitchConstantKeys.UserLogin.SomethingError) {
                    mUserViews.UserError(SwitchConstantKeys.UserLogin.SomethingError, obj);
                }
            }
        };
    }


    @Override
    public void LoginValidation(Context aContext, UserLogin_Request aRequest) {

        try {
            GetUserLoginTask aUserLoginTask = new GetUserLoginTask(aContext, mFinish_Listener);
            aUserLoginTask.CheckUserLogin(aRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
