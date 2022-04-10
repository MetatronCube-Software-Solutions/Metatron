package com.timedia.metatron.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.timedia.metatron.Interfaces.IPreferenceKeys;
import com.timedia.metatron.R;
import com.timedia.metatron.Request_Model.ModiyJobs_Request;
import com.timedia.metatron.Response_Model.JobList_Response;
import com.timedia.metatron.Response_Model.Server_Msg_Object;
import com.timedia.metatron.Response_Model.ServicesList;
import com.timedia.metatron.Response_Model.UsersList_Response;
import com.timedia.metatron.shared_data.PreferenceDetails;
import com.timedia.metatron.shared_data.PreferenceManager;
import com.timedia.metatron.utils.Constants;
import com.timedia.metatron.utils.Utils_Functions;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class View_UnAssignedJob_Technician extends AppCompatActivity implements View.OnClickListener {

    private EditText mNameEdt, mAddressEdt, mProvinceEdt, mCityEdt, mMobileEdt, mHomephoneEdt, mEmailEdtEdt, mWorkDescEdt;
    private TextView mServiceEdt, mCallReceivedEdt,
            mCallPromisedEdt, mPartsEDT;
    private KProgressHUD mProgress_Loader;
    private DatePickerDialog.OnDateSetListener mDate;
    private String mJobId_Intent, mJobStatus_Intent, mTechnicianId;
    private boolean mIsJobs_Editable = false;
    private Spinner mTechnicianSpinner, mFindUsEdt;
    ArrayList<String> mListTechnames = new ArrayList<>();
    private ArrayList<UsersList_Response.UsersList> mUserListTechnican = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__un_assigned_job__technician);
        Variableinit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Constants.mShowParts == 1) {
            Constants.mShowParts = 0;
            if (Partsnames.mPartsListGlobal.size() > 0) {
                for (int a = 0; a < Partsnames.mPartsListGlobal.size(); a++) {
                    Constants.mChecked_Position_Parts.put(Integer.valueOf(Partsnames.mPartsListGlobal.get(a).getPart_id()), Partsnames.mPartsListGlobal.get(a).getPart_id());

                }
                mPartsEDT.setText(Partsnames.mPartsListGlobal.size() + " Parts Selected");
            } else {
                mPartsEDT.setText("Choose Parts");
            }
        }
        if (Constants.mShowServicesUnAssigned==1) {
            Constants.mShowServicesUnAssigned=0;
            if (SHow_Services.mServiceDetailsGlobal.size() > 0) {
                for (int a = 0; a < SHow_Services.mServiceDetailsGlobal.size(); a++)
                {
                    Constants.mChecked_Position.put(Integer.valueOf(SHow_Services.mServiceDetailsGlobal.get(a).getService_id()), SHow_Services.mServiceDetailsGlobal.get(a).getService_id());
                }
                mServiceEdt.setText(SHow_Services.mServiceDetailsGlobal.size() + " Service Selected");
            } else {
                mServiceEdt.setText("Choose Service");
            }
        }
    }

    private void Variableinit() {
        Constants.mChecked_Position.clear();
        Constants.mChecked_Position_Parts.clear();
        mProgress_Loader = KProgressHUD.create(View_UnAssignedJob_Technician.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
        SHow_Services.mServiceDetailsGlobal.clear();
        mTechnicianSpinner = findViewById(R.id.technicianspinner);
        TextView aTooltitle = findViewById(R.id.toolbartitle);
        TextView aSaveJobs = findViewById(R.id.savejobs);
        ImageView aBackbutton = findViewById(R.id.backbutton);
        mNameEdt = findViewById(R.id.name);
        mAddressEdt = findViewById(R.id.address);
        mProvinceEdt = findViewById(R.id.province);
        mCityEdt = findViewById(R.id.city);
        mMobileEdt = findViewById(R.id.mobile);
        mHomephoneEdt = findViewById(R.id.phone);
        mEmailEdtEdt = findViewById(R.id.email);
        mCallReceivedEdt = findViewById(R.id.callreceived);
        mCallPromisedEdt = findViewById(R.id.callpromised);
        mFindUsEdt = findViewById(R.id.findus);
        mServiceEdt = findViewById(R.id.service);
        mPartsEDT = findViewById(R.id.parts);
        mWorkDescEdt = findViewById(R.id.worldesc);
        aBackbutton.setOnClickListener(this);
        aSaveJobs.setOnClickListener(this);
        mServiceEdt.setOnClickListener(this);
        mPartsEDT.setOnClickListener(this);
        ArrayList<String> aFindusArray = new ArrayList<>();
        aFindusArray.add("How did you hear about us?");
        aFindusArray.add("Word of mouth");
        aFindusArray.add("Google");
        aFindusArray.add("Blog");
        aFindusArray.add("Online Ads");
        aFindusArray.add("Social media");
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(View_UnAssignedJob_Technician.this,
                R.layout.layout_adapter, R.id.viewlabel,
                aFindusArray);
        mFindUsEdt.setAdapter(spinnerArrayAdapter);

        if (getIntent() != null) {
            Gson aGson = new Gson();
            mJobId_Intent = getIntent().getStringExtra("jobid");
            mJobStatus_Intent = getIntent().getStringExtra("job_status");
            String aJobname = getIntent().getStringExtra("name");
            String aAddress = getIntent().getStringExtra("address");
            String aProvince = getIntent().getStringExtra("province");
            String aCity = getIntent().getStringExtra("city");
            String aMobile = getIntent().getStringExtra("mobile");
            String aPhone = getIntent().getStringExtra("phone");
            String aEmail = getIntent().getStringExtra("email");
            String aCallreceived = getIntent().getStringExtra("callreceived");
            String aCalPromised = getIntent().getStringExtra("callpromised");
            String aFindus = getIntent().getStringExtra("findus");
            String aWorkdesc = getIntent().getStringExtra("workdesc");
            if (!TextUtils.isEmpty(mJobId_Intent)) {
                SHow_Services.mServiceDetailsGlobal.clear();
                int aPosition = Integer.parseInt(getIntent().getStringExtra("pos"));
                JobList_Response aResponseJobs = aGson.fromJson(getIntent().getStringExtra("aJobslistjson"), JobList_Response.class);
                ArrayList<JobList_Response.JobsList> aResponsejobarray = new ArrayList<>();
                aResponsejobarray = aResponseJobs.getJobsList();
                if (aResponsejobarray.get(aPosition).getServicesList() != null) {
                    for (int a = 0; a < aResponsejobarray.get(aPosition).getServicesList().size(); a++) {
                        Constants.mChecked_Position.put(Integer.valueOf(aResponsejobarray.get(aPosition).getServicesList().get(a).getService_id()), aResponsejobarray.get(aPosition).getServicesList().get(a).getService_id());
                        ServicesList aServiceModel = new ServicesList();
                        aServiceModel.setService_id(aResponsejobarray.get(aPosition).getServicesList().get(a).getService_id());
                        aServiceModel.setService_cost(aResponsejobarray.get(aPosition).getServicesList().get(a).getService_cost());
                        aServiceModel.setService_name(aResponsejobarray.get(aPosition).getServicesList().get(a).getService_name());
                        SHow_Services.mServiceDetailsGlobal.add(aServiceModel);
                    }
                    if (aResponsejobarray.get(aPosition).getServicesList().size() > 0) {
                        mServiceEdt.setText(aResponsejobarray.get(aPosition).getServicesList().size() + " Service Selected");
                    }
                } else {
                    mServiceEdt.setText("Choose Service");
                }
                aTooltitle.setText("Modify Jobs");
                mIsJobs_Editable = true;
                mNameEdt.setText(aJobname);
                mAddressEdt.setText(aAddress);
                mProvinceEdt.setText(aProvince);
                mCityEdt.setText(aCity);
                mMobileEdt.setText(aMobile);
                mHomephoneEdt.setText(aPhone);
                mEmailEdtEdt.setText(aEmail);
                mCallReceivedEdt.setText(aCallreceived);
                mCallPromisedEdt.setText(aCalPromised);
                int aObjectfind = aFindusArray.indexOf(aFindus);
                mFindUsEdt.setSelection(aObjectfind);
                mWorkDescEdt.setText(aWorkdesc);
            }
        }
        mProgress_Loader.show();

        Call<UsersList_Response> aTechnicianApi = Utils_Functions.Get_API_Services().TechnicianList("Technician",PreferenceDetails.GetUserid());
        aTechnicianApi.enqueue(new Callback<UsersList_Response>() {
            @Override
            public void onResponse(Call<UsersList_Response> call, Response<UsersList_Response> response) {
                if (response.code() == 200) {
                    mProgress_Loader.dismiss();
                    UsersList_Response aResponseJson = response.body();
                    if (aResponseJson != null) {
                        if (!TextUtils.isEmpty(aResponseJson.getServerMsg().getMsg())) {
                            if (aResponseJson.getServerMsg().getMsg().equalsIgnoreCase("Success")) {
                                mUserListTechnican = aResponseJson.getUsersList();
                                for (int a = 0; a < aResponseJson.getUsersList().size(); a++) {
                                    mListTechnames.add(aResponseJson.getUsersList().get(a).getUser_empid() + "-" + aResponseJson.getUsersList().get(a).getUser_firstname() + " " + aResponseJson.getUsersList().get(a).getUser_lastname());
                                }
                                if (mListTechnames.size() == 0)
                                    mListTechnames.add("No Technician Available");
                                ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(View_UnAssignedJob_Technician.this,
                                        R.layout.layout_adapter, R.id.viewlabel,
                                        mListTechnames);
                                mTechnicianSpinner.setAdapter(spinnerArrayAdapter);
                            } else {
                                mListTechnames.add("No Technician Available");
                                ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(View_UnAssignedJob_Technician.this,
                                        R.layout.layout_adapter, R.id.viewlabel,
                                        mListTechnames);
                                mTechnicianSpinner.setAdapter(spinnerArrayAdapter);
                            }
                        }
                    }
                } else
                    mProgress_Loader.dismiss();
            }

            @Override
            public void onFailure(Call<UsersList_Response> call, Throwable t) {
                mProgress_Loader.dismiss();
            }
        });
        mTechnicianSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!mTechnicianSpinner.getSelectedItem().toString().equalsIgnoreCase("No Technician Available")) {
                    if (mUserListTechnican != null) {
                        mTechnicianId = mUserListTechnican.get(i).getUser_id();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.service:
                startActivity(new Intent(View_UnAssignedJob_Technician.this, SHow_Services.class));
                break;
            case R.id.backbutton:
                finish();
                break;
            case R.id.parts:
                startActivity(new Intent(View_UnAssignedJob_Technician.this, Partsnames.class));

                break;
            case R.id.savejobs:
                String aCurrentDateTime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                String aNameval = mNameEdt.getText().toString();
                String aAddressval = mAddressEdt.getText().toString();
                String aProvinceval = mProvinceEdt.getText().toString();
                String aCityval = mCityEdt.getText().toString();
                String aMobilval = mMobileEdt.getText().toString();
                String aHomephoneval = mHomephoneEdt.getText().toString();
                String aEmailval = mEmailEdtEdt.getText().toString();
                String aCallReceivedval = mCallReceivedEdt.getText().toString();
                String aCallPromisedval = mCallPromisedEdt.getText().toString();
                String aFindusval = mFindUsEdt.getSelectedItem().toString();
                String aWorkdescval = mWorkDescEdt.getText().toString();

                Modify_JobApiCall(aAddressval, aCityval, aEmailval, aFindusval, aMobilval, aHomephoneval, aWorkdescval, aProvinceval, aNameval, aCurrentDateTime, aCallReceivedval, aCallPromisedval);
                break;
        }
    }

    private void Modify_JobApiCall(String aAddressval, String aCityval, String aEmailval, String aFindusval, String aMobilval, String aHomephoneval, String aWorkdescval, String aProvinceval, String aNameval, String aCurrentDateTime, String aCallReceivedval, String aCallPromisedval) {
        mProgress_Loader.show();
        ModiyJobs_Request aRequest = new ModiyJobs_Request();
        aRequest.setJobid(mJobId_Intent);
        aRequest.setAddress(aAddressval);
        aRequest.setCity(aCityval);
        aRequest.setEmail(aEmailval);
        aRequest.setFindus(aFindusval);
        aRequest.setMobile(aMobilval);
        aRequest.setPhone(aHomephoneval);
        aRequest.setWorkdesc(aWorkdescval);
        aRequest.setProvince(aProvinceval);
        aRequest.setName(aNameval);
        aRequest.setModifieddate(aCurrentDateTime);
        aRequest.setModifiedby(PreferenceDetails.GetUserid());
        aRequest.setTechid(mTechnicianId);
        aRequest.setCallreceived(aCallReceivedval);
        aRequest.setCallpromised(aCallPromisedval);
        aRequest.setServicesList(SHow_Services.mServiceDetailsGlobal);
        aRequest.setPartsList(Partsnames.mPartsListGlobal);
        aRequest.setStatus(mJobStatus_Intent);
        aRequest.setSubmittype("0");

        Call<Server_Msg_Object> aModiyjobApi = Utils_Functions.Get_API_Services().ModifyJobs(aRequest);
        aModiyjobApi.enqueue(new Callback<Server_Msg_Object>() {
            @Override
            public void onResponse(Call<Server_Msg_Object> call, Response<Server_Msg_Object> response) {
                if (response.code() == 200) {
                    Server_Msg_Object aResponseJson = response.body();
                    if (aResponseJson != null) {
                        if (aResponseJson.getServerMsg() != null) {
                            if (!TextUtils.isEmpty(aResponseJson.getServerMsg().getMsg())) {
                                if (aResponseJson.getServerMsg().getMsg().equalsIgnoreCase("Success")) {
                                    mProgress_Loader.dismiss();
                                    Show_Toast(aResponseJson.getServerMsg().getDisplayMsg());
                                    finish();
                                } else {
                                    mProgress_Loader.dismiss();
                                    Show_Toast(aResponseJson.getServerMsg().getDisplayMsg());
                                }
                            }
                        }
                    }
                } else
                    mProgress_Loader.dismiss();
            }

            @Override
            public void onFailure(Call<Server_Msg_Object> call, Throwable t) {
                mProgress_Loader.dismiss();
            }
        });

    }

    private void Show_Toast(String aMsg) {
        Toast.makeText(getApplicationContext(), aMsg, Toast.LENGTH_SHORT).show();
    }
}

