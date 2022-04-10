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
import com.timedia.metatron.Adapter.JobList_Adapter;
import com.timedia.metatron.Adapter_OnclickInterface.Jobs_Onclick;
import com.timedia.metatron.Interfaces.IPreferenceKeys;
import com.timedia.metatron.R;
import com.timedia.metatron.Response_Model.JobList_Response;
import com.timedia.metatron.Response_Model.ServerMessage_Response;
import com.timedia.metatron.View.Add_Jobs;
import com.timedia.metatron.View.SHow_Services;
import com.timedia.metatron.shared_data.PreferenceDetails;
import com.timedia.metatron.shared_data.PreferenceManager;
import com.timedia.metatron.utils.Constants;
import com.timedia.metatron.utils.Utils_Functions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InProgressJob_Fragment extends Fragment implements Jobs_Onclick {

    PreferenceManager mPref_Mgr;
    private KProgressHUD mProgress_Loader;
    private FloatingActionButton mAddJobs;
    private RecyclerView mJobs_Recyclerview;
    private ArrayList<JobList_Response.JobsList> mListJobArray = new ArrayList<>();
    private String aJobResponseJson;
    private boolean IsVisisble = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View aview = inflater.inflate(R.layout.jobs_fragment, container, false);
        return aview;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && IsVisisble) {
            Variableinit(getView());
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        IsVisisble = true;

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

        Call<JobList_Response> aJobsApi = Utils_Functions.Get_API_Services().ListJobs("0", PreferenceDetails.GetUserid());
        aJobsApi.enqueue(new Callback<JobList_Response>() {
            @Override
            public void onResponse(Call<JobList_Response> call, Response<JobList_Response> response) {
                if (response.code() == 200) {
                    mProgress_Loader.dismiss();
                    JobList_Response aResponseJson = response.body();
                    Gson aGson = new Gson();
                    aJobResponseJson = aGson.toJson(aResponseJson);
                    mListJobArray = aResponseJson.getJobsList();
                    if (mListJobArray != null) {
                        if (mListJobArray.size() > 0) {
                            mJobs_Recyclerview.setAdapter(new JobList_Adapter(getActivity(), mListJobArray, InProgressJob_Fragment.this));
                        } else {
                            mListJobArray = new ArrayList<>();
                            mJobs_Recyclerview.setAdapter(new JobList_Adapter(getActivity(), mListJobArray, InProgressJob_Fragment.this));
                        }
                    } else {
                        mListJobArray = new ArrayList<>();
                        mJobs_Recyclerview.setAdapter(new JobList_Adapter(getActivity(), mListJobArray, InProgressJob_Fragment.this));
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
    public void onJobs_OnclickCallback(final int position, int type) {
        if (type == 0) {

        } else if (type == 1) {

            Intent aIntent = new Intent(getActivity(), Add_Jobs.class);
            aIntent.putExtra("jobid", mListJobArray.get(position).getJob_id());
            aIntent.putExtra("name", mListJobArray.get(position).getJob_customername());
            aIntent.putExtra("address", mListJobArray.get(position).getJob_address());
            aIntent.putExtra("province", mListJobArray.get(position).getJob_province());
            aIntent.putExtra("city", mListJobArray.get(position).getJob_city());
            aIntent.putExtra("mobile", mListJobArray.get(position).getJob_mobile());
            aIntent.putExtra("phone", mListJobArray.get(position).getJob_phone());
            aIntent.putExtra("email", mListJobArray.get(position).getJob_email());
            aIntent.putExtra("callreceived", mListJobArray.get(position).getJob_callreceived());
            aIntent.putExtra("callpromised", mListJobArray.get(position).getJob_callpromised());
            aIntent.putExtra("findus", mListJobArray.get(position).getJob_findus());
            aIntent.putExtra("workdesc", mListJobArray.get(position).getJob_workdesc());
            aIntent.putExtra("job_status", mListJobArray.get(position).getJob_status());
            aIntent.putExtra("aJobslistjson", aJobResponseJson);
            aIntent.putExtra("pos", String.valueOf(position));
            startActivity(aIntent);

        } else if (type == 2) {
            mProgress_Loader.show();
            AlertDialog.Builder aBuilderDialog = new AlertDialog.Builder(getActivity());
            aBuilderDialog.setTitle("Confirmation");
            aBuilderDialog.setMessage("Do You wish to Delete the Jobs?");
            aBuilderDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Call<ServerMessage_Response> aDeleteService = Utils_Functions.Get_API_Services().RemoveJobs(mListJobArray.get(position).getJob_id(),PreferenceDetails.GetUserid());
                    aDeleteService.enqueue(new Callback<ServerMessage_Response>() {
                        @Override
                        public void onResponse(Call<ServerMessage_Response> call, Response<ServerMessage_Response> response) {
                            if (response.code() == 200) {
                                mProgress_Loader.dismiss();
                                ServerMessage_Response aResponse = response.body();
                                if (aResponse != null) {
                                    if (!TextUtils.isEmpty(aResponse.getMsg())) {
                                        if (aResponse.getMsg().equalsIgnoreCase("Success")) {
                                            Show_Toast(aResponse.getDisplayMsg());
                                            Load_JobListApi();
                                        } else {
                                            Show_Toast(aResponse.getDisplayMsg());
                                        }
                                    }
                                }
                            } else
                                mProgress_Loader.dismiss();
                        }

                        @Override
                        public void onFailure(Call<ServerMessage_Response> call, Throwable t) {
                            mProgress_Loader.dismiss();
                        }
                    });
                }
            });
            aBuilderDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog aDialogwindow = aBuilderDialog.create();
            aDialogwindow.show();


        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Constants.mAdd_JobsOnclick == 1) {
            Constants.mAdd_JobsOnclick = 0;
            Load_JobListApi();
        } else {
            Variableinit(getView());
        }
    }

    private void Show_Toast(String aMsg) {
        Toast.makeText(getActivity(), aMsg, Toast.LENGTH_SHORT).show();
    }
}