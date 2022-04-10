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

import com.kaopiz.kprogresshud.KProgressHUD;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;
import com.timedia.metatron.Adapter.ListJobService_Adapter;
import com.timedia.metatron.Adapter_OnclickInterface.Parts_Onclick;
import com.timedia.metatron.IPresenters.IPresenters;
import com.timedia.metatron.Interfaces.SwitchConstantKeys;
import com.timedia.metatron.Presenter_Views.IViews;
import com.timedia.metatron.Presenters.Service_Presenter;
import com.timedia.metatron.R;
import com.timedia.metatron.Response_Model.ListService_Response;
import com.timedia.metatron.Response_Model.ServerMessage_Response;
import com.timedia.metatron.Response_Model.ServicesList;
import com.timedia.metatron.View.Add_Service;
import com.timedia.metatron.shared_data.PreferenceManager;
import com.timedia.metatron.utils.Constants;
import com.timedia.metatron.utils.Utils_Functions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceList_Fragment extends Fragment implements View.OnClickListener, IViews.ServiceManagementDetails,
        Parts_Onclick {

    private RecyclerView mListService_Recyclerview;
    private PreferenceManager mPref_Mgr;
    private KProgressHUD mProgress_Loader;
    private FloatingActionButton mJobAdd_FAB;
    private IPresenters.AdminServices_Presenters mAdminServices;
    private ArrayList<ServicesList> mServicesList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View aview = inflater.inflate(R.layout.parts_fragment, container, false);
        return aview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Variableinit(view);
    }

    private void Variableinit(View view) {
        mAdminServices = new Service_Presenter(this);
        mPref_Mgr = PreferenceManager.getInstance();
        mProgress_Loader = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
        mJobAdd_FAB = view.findViewById(R.id.addjobfab);
        mJobAdd_FAB.setOnClickListener(this);
        mListService_Recyclerview = view.findViewById(R.id.joblist);
        mListService_Recyclerview.setHasFixedSize(false);
        mListService_Recyclerview.setItemViewCacheSize(20);
        mListService_Recyclerview.setDrawingCacheEnabled(true);
        mListService_Recyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mListService_Recyclerview.setLayoutManager(mLayoutManager);
        mProgress_Loader.show();
        mAdminServices.ListOfServices(getActivity());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addjobfab:
                startActivity(new Intent(getActivity(), Add_Service.class));
                break;
        }
    }

    @Override
    public void SuccessResult(int aResultCode, Object aObject) {
        if (aResultCode == SwitchConstantKeys.Service_Mgmt.ListJob_Success) {
            mProgress_Loader.dismiss();
            ListService_Response aResponseService = (ListService_Response) aObject;
            if (aResponseService != null) {
                if (aResponseService.getServicesList() != null) {
                    if (aResponseService.getServicesList().size() > 0) {
                        mServicesList.clear();
                        mServicesList.addAll(aResponseService.getServicesList());
                        mListService_Recyclerview.setAdapter(new ListJobService_Adapter(getActivity(), mServicesList, ServiceList_Fragment.this));
                    } else {
                        mServicesList.clear();
                        mListService_Recyclerview.setAdapter(new ListJobService_Adapter(getActivity(), mServicesList, ServiceList_Fragment.this));
                    }
                }
            }
        } else
            mProgress_Loader.dismiss();
    }

    @Override
    public void FailureResult(int aResultCode, Object aObject) {
        if (aResultCode == SwitchConstantKeys.Service_Mgmt.ListJob_Failure) {
            if (aObject.toString().contains("Something Went Wrong")) {
                mProgress_Loader.dismiss();
                Show_Toast(aObject.toString());
            } else {
                mProgress_Loader.dismiss();
                ListService_Response aResponseService = (ListService_Response) aObject;
                Show_Toast(aResponseService.getServerMsg().getDisplayMsg());
                mServicesList.clear();
                mListService_Recyclerview.setAdapter(new ListJobService_Adapter(getActivity(), mServicesList, ServiceList_Fragment.this));
            }

        } else
            mProgress_Loader.dismiss();

    }

    private void Show_Toast(String aMsg) {
        Toast.makeText(getActivity(), aMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onParts_OnclickCallback(final int position, int type) {
        if (type == 0) {

        } else if (type == 1) {

            Intent aIntent = new Intent(getActivity(), Add_Service.class);
            aIntent.putExtra("serviceid", mServicesList.get(position).getService_id());
            aIntent.putExtra("name", mServicesList.get(position).getService_name());
            aIntent.putExtra("timing", mServicesList.get(position).getService_timing());
            aIntent.putExtra("cost", mServicesList.get(position).getService_cost());
            aIntent.putExtra("modifyby", mServicesList.get(position).getService_modifiedby());
            aIntent.putExtra("status", mServicesList.get(position).getService_status());
            startActivity(aIntent);

        } else if (type == 2) {

            AlertDialog.Builder aBuilderDialog = new AlertDialog.Builder(getActivity());
            aBuilderDialog.setTitle("Confirmation");
            aBuilderDialog.setMessage("Do You wish to Delete the Services?");
            aBuilderDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mProgress_Loader.show();
                    Call<ServerMessage_Response> aDeleteService = Utils_Functions.Get_API_Services().RemoveService(mServicesList.get(position).getService_id());
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
                                            mAdminServices.ListOfServices(getActivity());
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
        if (Constants.mAdd_ServiceOnclick == 1) {
            Constants.mAdd_ServiceOnclick = 0;
            mAdminServices.ListOfServices(getActivity());
        }
    }
}
