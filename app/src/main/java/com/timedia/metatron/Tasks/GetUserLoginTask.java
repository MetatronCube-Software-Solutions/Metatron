package com.timedia.metatron.Tasks;

import android.content.Context;
import android.text.TextUtils;

import com.timedia.metatron.Interfaces.ITaskFinishListener;
import com.timedia.metatron.Interfaces.SwitchConstantKeys;
import com.timedia.metatron.Request_Model.UserLogin_Request;
import com.timedia.metatron.Response_Model.UserLogin_Response;
import com.timedia.metatron.Response_Model.UserRegister_Request;
import com.timedia.metatron.Response_Model.UserRegistration_Response;
import com.timedia.metatron.utils.Utils_Functions;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUserLoginTask {

    Context mContext;
    ITaskFinishListener mITaskFinishListener;

    public GetUserLoginTask(Context aContext, ITaskFinishListener aITaskFinishListener) {
        this.mContext = aContext;
        this.mITaskFinishListener = aITaskFinishListener;
    }

    public void CheckUserLogin(UserLogin_Request aRequest) throws IOException {

        Call<UserLogin_Response> aLoginApiCall = Utils_Functions.Get_API_Services().UserLoginValidation(aRequest);
        aLoginApiCall.enqueue(new Callback<UserLogin_Response>() {
            @Override
            public void onResponse(Call<UserLogin_Response> call, Response<UserLogin_Response> response) {
                if (response.code() == 200) {
                    UserLogin_Response aResJson = response.body();
                    if (aResJson != null) {
                        if (!TextUtils.isEmpty(aResJson.getServerMsg().getMsg())) {
                            if (aResJson.getServerMsg().getMsg().equalsIgnoreCase("Success")) {
                                if (aResJson.getLoginReturn() != null) {
                                    mITaskFinishListener.onFinished(SwitchConstantKeys.UserLogin.Login_Success, aResJson);
                                }
                            } else {
                                mITaskFinishListener.onFinished(SwitchConstantKeys.UserLogin.Login_Failure, aResJson);
                            }
                        }
                    }
                } else
                    mITaskFinishListener.onFinished(SwitchConstantKeys.UserLogin.SomethingError, "Something Went Wrong");

            }

            @Override
            public void onFailure(Call<UserLogin_Response> call, Throwable t) {
                mITaskFinishListener.onFinished(SwitchConstantKeys.UserLogin.SomethingError, "Something Went Wrong");
            }
        });
    }

    public void UserRegistration(UserRegister_Request aRequest) throws IOException {

        Call<UserRegistration_Response> aRegisterApi = Utils_Functions.Get_API_Services().UserRegistration(aRequest);
        aRegisterApi.enqueue(new Callback<UserRegistration_Response>() {
            @Override
            public void onResponse(Call<UserRegistration_Response> call, Response<UserRegistration_Response> response) {
                if (response.code() == 200) {
                    UserRegistration_Response aResJson = response.body();
                    if (aResJson != null) {
                        if (!TextUtils.isEmpty(aResJson.getServerMsg().getMsg())) {
                            if (aResJson.getServerMsg().getMsg().equalsIgnoreCase("Success")) {
                                if (aResJson.getLoginReturn() != null) {
                                    mITaskFinishListener.onFinished(SwitchConstantKeys.UserRegister.RegisterSuccess, aResJson);
                                }
                            } else {
                                mITaskFinishListener.onFinished(SwitchConstantKeys.UserRegister.Register_Failure, aResJson);
                            }
                        }
                    }
                } else
                    mITaskFinishListener.onFinished(SwitchConstantKeys.UserRegister.SomethingError, "Something Went Wrong");

            }

            @Override
            public void onFailure(Call<UserRegistration_Response> call, Throwable t) {
                mITaskFinishListener.onFinished(SwitchConstantKeys.UserRegister.SomethingError, "Something Went Wrong");
            }
        });
    }


}
