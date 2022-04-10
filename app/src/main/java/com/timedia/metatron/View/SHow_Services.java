package com.timedia.metatron.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.timedia.metatron.Adapter.Service_Jobs_Adapter;
import com.timedia.metatron.Adapter_OnclickInterface.Package_Onclick;
import com.timedia.metatron.Interfaces.IPreferenceKeys;
import com.timedia.metatron.R;
import com.timedia.metatron.Response_Model.ServiceListNames_Response;
import com.timedia.metatron.Response_Model.ServicesList;
import com.timedia.metatron.shared_data.PreferenceDetails;
import com.timedia.metatron.shared_data.PreferenceManager;
import com.timedia.metatron.utils.Constants;
import com.timedia.metatron.utils.Utils_Functions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SHow_Services extends AppCompatActivity implements View.OnClickListener, Package_Onclick {

    private KProgressHUD mProgress_Loader;
    private RecyclerView mListService_Recyclerview;
    private Service_Jobs_Adapter mService_Jobs_Adapter;
    private ArrayList<ServicesList> mServiceList_Array;
    public static ArrayList<ServicesList> mServiceDetailsGlobal=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__services);
        Variableinit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Constants.mShowServices=1;
        Constants.mShowServicesAssigned=1;
        Constants.mShowServicesUnAssigned=1;
    }

    private void Variableinit() {
        mProgress_Loader = KProgressHUD.create(SHow_Services.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);

        ImageView aBackbutton = findViewById(R.id.backbutton);
        mListService_Recyclerview = findViewById(R.id.servicelist);
        mListService_Recyclerview.setHasFixedSize(false);
        mListService_Recyclerview.setItemViewCacheSize(20);
        mListService_Recyclerview.setDrawingCacheEnabled(true);
        mListService_Recyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mListService_Recyclerview.setLayoutManager(mLayoutManager);
        aBackbutton.setOnClickListener(this);
        mProgress_Loader.show();

        Call<ServiceListNames_Response> aServiceNames = Utils_Functions.Get_API_Services().GetServiceListNames(PreferenceDetails.GetUserid());
        aServiceNames.enqueue(new Callback<ServiceListNames_Response>() {
            @Override
            public void onResponse(Call<ServiceListNames_Response> call, Response<ServiceListNames_Response> response) {
                if (response.code() == 200) {
                    mProgress_Loader.dismiss();
                    ServiceListNames_Response aResponseJson = response.body();
                    if (aResponseJson != null) {
                        if (!TextUtils.isEmpty(aResponseJson.getServerMsg().getMsg())) {
                            if (aResponseJson.getServerMsg().getMsg().equalsIgnoreCase("Success")) {
                                mServiceList_Array = aResponseJson.getServicesList();
                                mService_Jobs_Adapter = new Service_Jobs_Adapter(getApplicationContext(), mServiceList_Array, SHow_Services.this);
                                mListService_Recyclerview.setAdapter(mService_Jobs_Adapter);
                            }
                        }
                    }
                } else
                    mProgress_Loader.dismiss();
            }

            @Override
            public void onFailure(Call<ServiceListNames_Response> call, Throwable t) {
                mProgress_Loader.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backbutton:
                finish();
                break;
        }
    }

    @Override
    public void onMethodCallback(String[] aTags) {

    }
}
