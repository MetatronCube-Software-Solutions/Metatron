package com.timedia.metatron.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;
import com.timedia.metatron.Adapter.Assigned_Jobs_Adapter;
import com.timedia.metatron.Adapter.JobList_Adapter;
import com.timedia.metatron.Adapter.UnAssigned_Jobs_Adapter;
import com.timedia.metatron.Adapter_OnclickInterface.Jobs_Onclick;
import com.timedia.metatron.Interfaces.IPreferenceKeys;
import com.timedia.metatron.R;
import com.timedia.metatron.Request_Model.ModiyJobs_Request;
import com.timedia.metatron.Response_Model.AssignTechnician_Request;
import com.timedia.metatron.Response_Model.JobList_Response;
import com.timedia.metatron.Response_Model.ServerMessage_Response;
import com.timedia.metatron.Response_Model.Server_Msg_Object;
import com.timedia.metatron.View.Add_Jobs;
import com.timedia.metatron.View.Partsnames;
import com.timedia.metatron.View.SHow_Services;
import com.timedia.metatron.View.View_Job_Technician;
import com.timedia.metatron.View.View_UnAssignedJob_Technician;
import com.timedia.metatron.shared_data.PreferenceDetails;
import com.timedia.metatron.shared_data.PreferenceManager;
import com.timedia.metatron.utils.Constants;
import com.timedia.metatron.utils.Utils_Functions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UNAssigned_Jobs_Fragment extends Fragment implements View.OnClickListener, Jobs_Onclick {

    PreferenceManager mPref_Mgr;
    private KProgressHUD mProgress_Loader;
    private RecyclerView mJobs_Recyclerview;
    private ArrayList<JobList_Response.JobsList> mListJobArray = new ArrayList<>();
    private boolean IsLoaded = false;
    private AlertDialog mDialogwindow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View aview = inflater.inflate(R.layout.jobs_fragment, container, false);
        IsLoaded = true;
        return aview;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && IsLoaded) {
            Variableinit(getView());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    private void Variableinit(View view) {
        mPref_Mgr = PreferenceManager.getInstance();
        mProgress_Loader = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
        mJobs_Recyclerview = view.findViewById(R.id.joblist);
        mJobs_Recyclerview.setHasFixedSize(false);
        mJobs_Recyclerview.setItemViewCacheSize(20);
        mJobs_Recyclerview.setDrawingCacheEnabled(true);
        mJobs_Recyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mJobs_Recyclerview.setLayoutManager(mLayoutManager);
        Load_JobListApi();
    }

    private void Load_JobListApi() {
        mProgress_Loader.show();
        Call<JobList_Response> aJobsApi = Utils_Functions.Get_API_Services().ListJobsTechnician("0",PreferenceDetails.GetUserid());
        aJobsApi.enqueue(new Callback<JobList_Response>() {
            @Override
            public void onResponse(Call<JobList_Response> call, Response<JobList_Response> response) {
                if (response.code() == 200) {
                    mProgress_Loader.dismiss();
                    JobList_Response aResponseJson = response.body();
                    mListJobArray = aResponseJson.getJobsList();
                    if (mListJobArray != null) {
                        if (mListJobArray.size() > 0) {
                            mJobs_Recyclerview.setAdapter(new UnAssigned_Jobs_Adapter(getActivity(), mListJobArray, UNAssigned_Jobs_Fragment.this));
                        } else {
                            mListJobArray = new ArrayList<>();
                            mJobs_Recyclerview.setAdapter(new UnAssigned_Jobs_Adapter(getActivity(), mListJobArray, UNAssigned_Jobs_Fragment.this));
                        }
                    } else {
                        mListJobArray = new ArrayList<>();
                        mJobs_Recyclerview.setAdapter(new UnAssigned_Jobs_Adapter(getActivity(), mListJobArray, UNAssigned_Jobs_Fragment.this));
                    }
                } else
                    mProgress_Loader.dismiss();

            }

            @Override
            public void onFailure(Call<JobList_Response> call, Throwable t) {
                mProgress_Loader.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addjobfab:
                SHow_Services.mServiceDetailsGlobal.clear();
                startActivity(new Intent(getActivity(), Add_Jobs.class));
                break;
        }
    }

    @Override
    public void onJobs_OnclickCallback(final int position, int type) {
        if (type == 1) {

            AlertDialog.Builder aBuilderDialog = new AlertDialog.Builder(getActivity());
            aBuilderDialog.setTitle("Confirmation");
            aBuilderDialog.setMessage("Do you wish to Assign this Job to Technician?");
            aBuilderDialog.setPositiveButton("Assign", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Modify_JobApiCall(position);
                }
            });
            aBuilderDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            mDialogwindow = aBuilderDialog.create();
            mDialogwindow.show();
        }
    }

    private void Modify_JobApiCall(int aPos) {
        mProgress_Loader.show();
        AssignTechnician_Request aRequest = new AssignTechnician_Request();
        aRequest.setJobid(mListJobArray.get(aPos).getJob_id());
        aRequest.setTechid(mPref_Mgr.getString(IPreferenceKeys.UserDetail.UserId, IPreferenceKeys.Common.EMPTY));
        Call<ServerMessage_Response> aAssignApi = Utils_Functions.Get_API_Services().Assign_Technician(aRequest);
        aAssignApi.enqueue(new Callback<ServerMessage_Response>() {
            @Override
            public void onResponse(Call<ServerMessage_Response> call, Response<ServerMessage_Response> response) {
                if (response.code() == 200) {
                    mProgress_Loader.dismiss();
                    ServerMessage_Response aResponse_Json = response.body();
                    if (!TextUtils.isEmpty(aResponse_Json.getMsg())) {
                        if (aResponse_Json.getMsg().equalsIgnoreCase("Success")) {
                            Show_Toast(aResponse_Json.getDisplayMsg());
                            Load_JobListApi();
                            mDialogwindow.dismiss();
                        } else
                        {
                            Show_Toast(aResponse_Json.getDisplayMsg());
                            mDialogwindow.dismiss();
                        }
                    }
                } else
                {
                    mProgress_Loader.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ServerMessage_Response> call, Throwable t) {
                mProgress_Loader.dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Variableinit(getView());
    }

    private void Show_Toast(String aMsg) {
        Toast.makeText(getActivity(), aMsg, Toast.LENGTH_SHORT).show();
    }
}

