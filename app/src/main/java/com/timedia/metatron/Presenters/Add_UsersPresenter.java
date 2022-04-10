package com.timedia.metatron.Presenters;

import android.content.Context;

import com.timedia.metatron.IPresenters.IPresenters;
import com.timedia.metatron.Interfaces.ITaskFinishListener;
import com.timedia.metatron.Interfaces.SwitchConstantKeys;
import com.timedia.metatron.Presenter_Views.IViews;
import com.timedia.metatron.Request_Model.AddUser_Request;
import com.timedia.metatron.Tasks.AddUserTask;

import java.io.IOException;

public class Add_UsersPresenter implements IPresenters.AddUser_Presenters {

    IViews.UserRegistrationDetails mAddUserViews;
    ITaskFinishListener mFinish_Listener;

    public Add_UsersPresenter(IViews.UserRegistrationDetails aAddUserResponse) {

        mAddUserViews = aAddUserResponse;
        mFinish_Listener = new ITaskFinishListener() {
            @Override
            public void onFinished(int logicType, Object obj) {
                if (logicType == SwitchConstantKeys.AddUsers.AddUser_Success) {
                    mAddUserViews.AddUserSuccess(SwitchConstantKeys.AddUsers.AddUser_Success, obj);
                } else if (logicType == SwitchConstantKeys.AddUsers.AddUser_Failure) {
                    mAddUserViews.AddUserError(SwitchConstantKeys.AddUsers.AddUser_Failure, obj);
                } else if (logicType == SwitchConstantKeys.AddUsers.Internet_Failure) {
                    mAddUserViews.AddUserError(SwitchConstantKeys.AddUsers.Internet_Failure, obj);
                } else if (logicType == SwitchConstantKeys.AddUsers.SomethingError) {
                    mAddUserViews.AddUserError(SwitchConstantKeys.AddUsers.SomethingError, obj);
                }
            }
        };
    }


    @Override
    public void AddUser_Registration(Context aContext, AddUser_Request aRequest) {
        try {
            AddUserTask aUserAddTask = new AddUserTask(aContext, mFinish_Listener);
            aUserAddTask.RegisterUser(aRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
