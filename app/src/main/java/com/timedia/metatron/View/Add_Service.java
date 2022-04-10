package com.timedia.metatron.View;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.timedia.metatron.R;
import com.timedia.metatron.Request_Model.AddService_Request;
import com.timedia.metatron.Request_Model.ModifyService_Request;
import com.timedia.metatron.Response_Model.AddService_Response;
import com.timedia.metatron.Response_Model.ModifyService_Reponse;
import com.timedia.metatron.shared_data.PreferenceDetails;
import com.timedia.metatron.utils.Constants;
import com.timedia.metatron.utils.Utils_Functions;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_Service extends AppCompatActivity implements View.OnClickListener {

    private EditText mProductName, mTiming, mProductCost;
    private KProgressHUD mProgress_Loader;
    private boolean mEdit_Service = false;
    private String mServiceId_Intent, mServiceName_Intent, mServiceCost_Intent, mServiceTiming_Intent,
            mServiceModify_Intent, mServiceStatus_Intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__service);
        Variableinit();
    }

    private void Variableinit() {
        ImageView aBackbutton = findViewById(R.id.backbutton);
        mProductName = findViewById(R.id.productedt);
        mTiming = findViewById(R.id.producttimeedt);
        mProductCost = findViewById(R.id.productcostedt);
        TextView aAdd_service = findViewById(R.id.serviceaddbtn);
        TextView aToolTitle = findViewById(R.id.tooltitle);
        aAdd_service.setOnClickListener(this);
        aBackbutton.setOnClickListener(this);
        mProgress_Loader = KProgressHUD.create(Add_Service.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);

        if (getIntent() != null) {
            mServiceId_Intent = getIntent().getStringExtra("serviceid");
            mServiceName_Intent = getIntent().getStringExtra("name");
            mServiceTiming_Intent = getIntent().getStringExtra("timing");
            mServiceCost_Intent = getIntent().getStringExtra("cost");
            mServiceModify_Intent = getIntent().getStringExtra("modifyby");
            mServiceStatus_Intent = getIntent().getStringExtra("mServiceStatus_Intent");
            if (!TextUtils.isEmpty(mServiceId_Intent)) {
                mEdit_Service = true;
                aToolTitle.setText("Modify Service");
                aAdd_service.setText("Update Service");
                if (!TextUtils.isEmpty(mServiceName_Intent)) {
                    mProductName.setText(mServiceName_Intent);
                }
                if (!TextUtils.isEmpty(mServiceTiming_Intent)) {
                    mTiming.setText(mServiceTiming_Intent);
                }
                if (!TextUtils.isEmpty(mServiceCost_Intent)) {
                    mProductCost.setText(mServiceCost_Intent);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backbutton:
                finish();
                break;
            case R.id.serviceaddbtn:
              //  HideSoftKeyboard(getApplicationContext());
                String aCurrentDateTime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                String aProdnameVal = mProductName.getText().toString();
                String aTimingval = mTiming.getText().toString();
                String aCostval = mProductCost.getText().toString();
                if (TextUtils.isEmpty(aProdnameVal)) {
                    Show_Toast("Please Enter the Service Name");
                } else if (TextUtils.isEmpty(aTimingval)) {
                    Show_Toast("Please Enter the Service Timing");
                } else if (TextUtils.isEmpty(aCostval)) {
                    Show_Toast("Please Enter the Service Cost");
                } else {
                    mProgress_Loader.show();

                    if (mEdit_Service) {
                        final ModifyService_Request aRequest = new ModifyService_Request();
                        aRequest.setName(aProdnameVal);
                        aRequest.setTiming(aTimingval);
                        aRequest.setCost(aCostval);
                        aRequest.setModifiedby(PreferenceDetails.GetUserid());
                        aRequest.setModifieddate(aCurrentDateTime);
                        aRequest.setStatus(mServiceStatus_Intent);
                        aRequest.setServiceid(mServiceId_Intent);
                        Call<ModifyService_Reponse> aAddServiceApi = Utils_Functions.Get_API_Services().ModifyService(aRequest);
                        aAddServiceApi.enqueue(new Callback<ModifyService_Reponse>() {
                            @Override
                            public void onResponse(Call<ModifyService_Reponse> call, Response<ModifyService_Reponse> response) {
                                if (response.code() == 200) {
                                    mProgress_Loader.dismiss();
                                    ModifyService_Reponse aResponseJson = response.body();
                                    if (aResponseJson != null) {
                                        if (!TextUtils.isEmpty(aResponseJson.getServerMsg().getMsg())) {
                                            if (aResponseJson.getServerMsg().getMsg().equalsIgnoreCase("Success")) {
                                                Show_Toast(aResponseJson.getServerMsg().getDisplayMsg());
                                                Constants.mAdd_ServiceOnclick = 1;
                                                finish();
                                            } else {
                                                Show_Toast(aResponseJson.getServerMsg().getDisplayMsg());
                                            }
                                        }
                                    }
                                } else
                                    mProgress_Loader.dismiss();
                                ;

                            }

                            @Override
                            public void onFailure(Call<ModifyService_Reponse> call, Throwable t) {
                                mProgress_Loader.dismiss();
                            }
                        });
                    } else {
                        final AddService_Request aRequest = new AddService_Request();
                        aRequest.setName(aProdnameVal);
                        aRequest.setTiming(aTimingval);
                        aRequest.setCost(aCostval);
                        aRequest.setCreatedby(PreferenceDetails.GetUserid());
                        aRequest.setCreateddate(aCurrentDateTime);
                        Call<AddService_Response> aAddServiceApi = Utils_Functions.Get_API_Services().AddService(aRequest);
                        aAddServiceApi.enqueue(new Callback<AddService_Response>() {
                            @Override
                            public void onResponse(Call<AddService_Response> call, Response<AddService_Response> response) {
                                if (response.code() == 200) {
                                    mProgress_Loader.dismiss();
                                    AddService_Response aResponseJson = response.body();
                                    if (aResponseJson != null) {
                                        if (!TextUtils.isEmpty(aResponseJson.getServerMsg().getMsg())) {
                                            if (aResponseJson.getServerMsg().getMsg().equalsIgnoreCase("Success")) {
                                                Show_Toast(aResponseJson.getServerMsg().getDisplayMsg());
                                                Constants.mAdd_ServiceOnclick = 1;
                                                finish();
                                            } else {
                                                Show_Toast(aResponseJson.getServerMsg().getDisplayMsg());
                                            }
                                        }
                                    }
                                } else
                                    mProgress_Loader.dismiss();
                                ;

                            }

                            @Override
                            public void onFailure(Call<AddService_Response> call, Throwable t) {
                                mProgress_Loader.dismiss();
                            }
                        });
                    }


                }

                break;
        }
    }

    public void HideSoftKeyboard(Context context) {
        View view = Add_Service.this.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void Show_Toast(String aMsg) {
        Toast.makeText(getApplicationContext(), aMsg, Toast.LENGTH_SHORT).show();
    }
}
