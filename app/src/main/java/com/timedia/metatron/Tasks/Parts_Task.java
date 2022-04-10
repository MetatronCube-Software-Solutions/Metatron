package com.timedia.metatron.Tasks;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.timedia.metatron.Interfaces.IPreferenceKeys;
import com.timedia.metatron.Interfaces.ITaskFinishListener;
import com.timedia.metatron.Interfaces.SwitchConstantKeys;
import com.timedia.metatron.Request_Model.AddParts_Request;
import com.timedia.metatron.Request_Model.EditParts_Request;
import com.timedia.metatron.Response_Model.Add_Parts_Response;
import com.timedia.metatron.Response_Model.Parts_List_Response;
import com.timedia.metatron.Response_Model.ServerMessage_Response;
import com.timedia.metatron.shared_data.PreferenceDetails;
import com.timedia.metatron.shared_data.PreferenceManager;
import com.timedia.metatron.utils.Utils_Functions;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Parts_Task {

    Context mContext;
    ITaskFinishListener mITaskFinishListener;

    public Parts_Task(Context aContext, ITaskFinishListener aITaskFinishListener) {
        this.mContext = aContext;
        this.mITaskFinishListener = aITaskFinishListener;
    }

    public void GetParts_List() throws IOException {

        Call<Parts_List_Response> aGetPartsApi = Utils_Functions.Get_API_Services().GetParts_List(PreferenceDetails.GetUserid());
        aGetPartsApi.enqueue(new Callback<Parts_List_Response>() {
            @Override
            public void onResponse(Call<Parts_List_Response> call, Response<Parts_List_Response> response) {
                if (response.code() == 200) {
                    Parts_List_Response aResponse = response.body();
                    if (aResponse != null) {
                        if (!TextUtils.isEmpty(aResponse.getServerMsg().getMsg())) {
                            if (aResponse.getServerMsg().getMsg().equalsIgnoreCase("Success")) {
                                mITaskFinishListener.onFinished(SwitchConstantKeys.Parts_Mgmt.ListParts_Success, aResponse);
                            } else {
                                mITaskFinishListener.onFinished(SwitchConstantKeys.Parts_Mgmt.ListParts_Failure, aResponse);
                            }
                        }
                    }
                } else
                    mITaskFinishListener.onFinished(SwitchConstantKeys.Parts_Mgmt.SomethingError, "Something Went Wrong");

            }

            @Override
            public void onFailure(Call<Parts_List_Response> call, Throwable t) {
                mITaskFinishListener.onFinished(SwitchConstantKeys.Parts_Mgmt.SomethingError, "Something Went Wrong");
            }
        });
    }

    public void Remove_Parts(String aPartId) throws IOException {
        Call<ServerMessage_Response> aGetPartsApi = Utils_Functions.Get_API_Services().RemoveParts(aPartId);
        aGetPartsApi.enqueue(new Callback<ServerMessage_Response>() {
            @Override
            public void onResponse(Call<ServerMessage_Response> call, Response<ServerMessage_Response> response) {
                if (response.code() == 200) {
                    ServerMessage_Response aResponse = response.body();
                    if (aResponse != null) {
                        if (!TextUtils.isEmpty(aResponse.getMsg())) {
                            if (aResponse.getMsg().equalsIgnoreCase("Success")) {
                                mITaskFinishListener.onFinished(SwitchConstantKeys.Parts_Mgmt.PartsRemove_Success, aResponse);
                            } else {
                                mITaskFinishListener.onFinished(SwitchConstantKeys.Parts_Mgmt.PartsRemove_Failure, aResponse);
                            }
                        }
                    }
                } else
                    mITaskFinishListener.onFinished(SwitchConstantKeys.Parts_Mgmt.SomethingError, "Something Went Wrong");
            }

            @Override
            public void onFailure(Call<ServerMessage_Response> call, Throwable t) {
                mITaskFinishListener.onFinished(SwitchConstantKeys.Parts_Mgmt.SomethingError, "Something Went Wrong");
            }
        });

    }

    public void AddParts(AddParts_Request aRequest) throws IOException {
        Call<Add_Parts_Response> aGetPartsApi = Utils_Functions.Get_API_Services().AddParts(aRequest);
        aGetPartsApi.enqueue(new Callback<Add_Parts_Response>() {
            @Override
            public void onResponse(Call<Add_Parts_Response> call, Response<Add_Parts_Response> response) {
                if (response.code() == 200) {
                    Add_Parts_Response aResponse = response.body();
                    if (aResponse != null) {
                        if (!TextUtils.isEmpty(aResponse.getServerMsg().getMsg())) {
                            if (aResponse.getServerMsg().getMsg().equalsIgnoreCase("Success")) {
                                mITaskFinishListener.onFinished(SwitchConstantKeys.Parts_Mgmt.AddParts_Success, aResponse);
                            } else {
                                mITaskFinishListener.onFinished(SwitchConstantKeys.Parts_Mgmt.AddParts_failure, aResponse);
                            }
                        }
                    }
                } else
                    mITaskFinishListener.onFinished(SwitchConstantKeys.Parts_Mgmt.SomethingError, "Something Went Wrong");

            }

            @Override
            public void onFailure(Call<Add_Parts_Response> call, Throwable t) {
                mITaskFinishListener.onFinished(SwitchConstantKeys.Parts_Mgmt.SomethingError, "Something Went Wrong");
            }
        });
    }

    public void ModifyParts(EditParts_Request aRequest) throws IOException {
        Call<Add_Parts_Response> aGetPartsApi = Utils_Functions.Get_API_Services().ModifyParts(aRequest);
        aGetPartsApi.enqueue(new Callback<Add_Parts_Response>() {
            @Override
            public void onResponse(Call<Add_Parts_Response> call, Response<Add_Parts_Response> response) {
                if (response.code() == 200) {
                    Add_Parts_Response aResponse = response.body();
                    if (aResponse != null) {
                        if (!TextUtils.isEmpty(aResponse.getServerMsg().getMsg())) {
                            if (aResponse.getServerMsg().getMsg().equalsIgnoreCase("Success")) {
                                mITaskFinishListener.onFinished(SwitchConstantKeys.Parts_Mgmt.AddParts_Success, aResponse);
                            } else {
                                mITaskFinishListener.onFinished(SwitchConstantKeys.Parts_Mgmt.AddParts_failure, aResponse);
                            }
                        }
                    }
                } else
                    mITaskFinishListener.onFinished(SwitchConstantKeys.Parts_Mgmt.SomethingError, "Something Went Wrong");

            }

            @Override
            public void onFailure(Call<Add_Parts_Response> call, Throwable t) {
                mITaskFinishListener.onFinished(SwitchConstantKeys.Parts_Mgmt.SomethingError, "Something Went Wrong");
            }
        });
    }


}
