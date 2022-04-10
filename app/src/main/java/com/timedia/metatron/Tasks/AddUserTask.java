package com.timedia.metatron.Tasks;

import android.content.Context;
import android.text.TextUtils;

import com.timedia.metatron.Interfaces.ITaskFinishListener;
import com.timedia.metatron.Interfaces.SwitchConstantKeys;
import com.timedia.metatron.Request_Model.AddUser_Request;
import com.timedia.metatron.Request_Model.UserLogin_Request;
import com.timedia.metatron.Response_Model.AddUser_Response;
import com.timedia.metatron.Response_Model.UserLogin_Response;
import com.timedia.metatron.utils.Utils_Functions;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserTask {

    Context mContext;
    ITaskFinishListener mITaskFinishListener;

    public AddUserTask(Context aContext, ITaskFinishListener aITaskFinishListener) {
        this.mContext = aContext;
        this.mITaskFinishListener = aITaskFinishListener;
    }

    public void RegisterUser(AddUser_Request aRequest) throws IOException {

        Call<AddUser_Response> aLoginApiCall = Utils_Functions.Get_API_Services().AddUsers(aRequest);
        aLoginApiCall.enqueue(new Callback<AddUser_Response>() {
            @Override
            public void onResponse(Call<AddUser_Response> call, Response<AddUser_Response> response) {
                if (response.code() == 200) {
                    AddUser_Response aResJson = response.body();
                    if (aResJson != null) {
                        if (!TextUtils.isEmpty(aResJson.getServerMsg().getMsg())) {
                            if (aResJson.getServerMsg().getMsg().equalsIgnoreCase("Success")) {
                                mITaskFinishListener.onFinished(SwitchConstantKeys.AddUsers.AddUser_Success, aResJson);

                            } else {
                                mITaskFinishListener.onFinished(SwitchConstantKeys.AddUsers.AddUser_Failure, aResJson);
                            }
                        }
                    }
                } else
                    mITaskFinishListener.onFinished(SwitchConstantKeys.AddUsers.SomethingError, "Something Went Wrong");

            }

            @Override
            public void onFailure(Call<AddUser_Response> call, Throwable t) {
                mITaskFinishListener.onFinished(SwitchConstantKeys.AddUsers.SomethingError, "Something Went Wrong");
            }
        });
    }

}

