package com.timedia.metatron.Tasks;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.timedia.metatron.Interfaces.IPreferenceKeys;
import com.timedia.metatron.Interfaces.ITaskFinishListener;
import com.timedia.metatron.Interfaces.SwitchConstantKeys;
import com.timedia.metatron.Request_Model.AddUser_Request;
import com.timedia.metatron.Response_Model.AddUser_Response;
import com.timedia.metatron.Response_Model.ListService_Response;
import com.timedia.metatron.shared_data.PreferenceDetails;
import com.timedia.metatron.shared_data.PreferenceManager;
import com.timedia.metatron.utils.Utils_Functions;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobService_Task {
    Context mContext;
    ITaskFinishListener mITaskFinishListener;

    public JobService_Task(Context aContext, ITaskFinishListener aITaskFinishListener) {
        this.mContext = aContext;
        this.mITaskFinishListener = aITaskFinishListener;
    }

    public void ListServices() throws IOException {

        Call<ListService_Response> aLoginApiCall = Utils_Functions.Get_API_Services().GetListOfServices(PreferenceDetails.GetUserid());
        aLoginApiCall.enqueue(new Callback<ListService_Response>() {
            @Override
            public void onResponse(Call<ListService_Response> call, Response<ListService_Response> response) {
                if (response.code() == 200) {
                    ListService_Response aResJson = response.body();
                    if (aResJson != null) {
                        if (!TextUtils.isEmpty(aResJson.getServerMsg().getMsg())) {
                            if (aResJson.getServerMsg().getMsg().equalsIgnoreCase("Success")) {
                                mITaskFinishListener.onFinished(SwitchConstantKeys.Service_Mgmt.ListJob_Success, aResJson);

                            } else {
                                mITaskFinishListener.onFinished(SwitchConstantKeys.Service_Mgmt.ListJob_Failure, aResJson);
                            }
                        }
                    }
                } else
                    mITaskFinishListener.onFinished(SwitchConstantKeys.Service_Mgmt.SomethingError, "Something Went Wrong");

            }

            @Override
            public void onFailure(Call<ListService_Response> call, Throwable t) {
                mITaskFinishListener.onFinished(SwitchConstantKeys.Service_Mgmt.SomethingError, "Something Went Wrong");
            }
        });
    }

}


