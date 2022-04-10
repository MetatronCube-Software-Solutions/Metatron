package com.timedia.metatron.View;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.timedia.metatron.Interfaces.IPreferenceKeys;
import com.timedia.metatron.R;
import com.timedia.metatron.Request_Model.AddJob_Request;
import com.timedia.metatron.Request_Model.ModiyJobs_Request;
import com.timedia.metatron.Response_Model.AddJobs_Response;
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

public class Add_Jobs extends AppCompatActivity implements View.OnClickListener {

    private EditText mNameEdt, mAddressEdt, mProvinceEdt, mCityEdt, mMobileEdt, mHomephoneEdt, mEmailEdtEdt, mWorkDescEdt;
    private TextView mServiceEdt, mCallReceivedEdt,
            mCallPromisedEdt;
    private KProgressHUD mProgress_Loader;
    private int mClickCalltypes = 0;
    final Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener mDate;
    private String mJobId_Intent, mJobStatus_Intent, mTechnicianId;
    private boolean mIsJobs_Editable = false;
    private Spinner mTechnicianSpinner, mFindUsEdt;
    ArrayList<String> mListTechnames = new ArrayList<>();
    private ArrayList<UsersList_Response.UsersList> mUserListTechnican = new ArrayList<>();
    private String mSelectedDate = null, mSelectedTime = null,mJobTechid=null;
    int mHour;
    int mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__jobs);
        Variableinit();
    }

    private void Variableinit() {
        Constants.mChecked_Position.clear();
        mProgress_Loader = KProgressHUD.create(Add_Jobs.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
        SHow_Services.mServiceDetailsGlobal.clear();
        mTechnicianSpinner = findViewById(R.id.technicianspinner);
        TextView aTooltitle = findViewById(R.id.toolbartitle);
        TextView aAddJobs = findViewById(R.id.addjobs);
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
        mWorkDescEdt = findViewById(R.id.worldesc);
        aBackbutton.setOnClickListener(this);
        aAddJobs.setOnClickListener(this);
        mCallReceivedEdt.setOnClickListener(this);
        mCallPromisedEdt.setOnClickListener(this);
        mServiceEdt.setOnClickListener(this);
        mDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mSelectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                tiemPicker();
            }

        };
        ArrayList<String> aFindusArray = new ArrayList<>();
        aFindusArray.add("How did you hear about us?");
        aFindusArray.add("Word of mouth");
        aFindusArray.add("Google");
        aFindusArray.add("Blog");
        aFindusArray.add("Online Ads");
        aFindusArray.add("Social media");
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(Add_Jobs.this,
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
                mJobTechid=aResponseJobs.getJobsList().get(aPosition).getJob_techid();
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
                aAddJobs.setText("Update Jobs");
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
                                int aPosition=1;
                                for (int a = 0; a < aResponseJson.getUsersList().size(); a++) {
                                    if (aResponseJson.getUsersList().get(a).getUser_id().equalsIgnoreCase(mJobTechid)) {
                                        aPosition=aPosition+a;
                                    }
                                    mListTechnames.add(aResponseJson.getUsersList().get(a).getUser_empid() + "-" + aResponseJson.getUsersList().get(a).getUser_firstname() + " " + aResponseJson.getUsersList().get(a).getUser_lastname());
                                }
                                if (mListTechnames.size() == 0)
                                    mListTechnames.add("No Technician Available");
                                else
                                    mListTechnames.add(0,"Select Technician");
                                ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(Add_Jobs.this,
                                        R.layout.layout_adapter, R.id.viewlabel,
                                        mListTechnames);
                                mTechnicianSpinner.setAdapter(spinnerArrayAdapter);
                                mTechnicianSpinner.setSelection(aPosition);
                            } else {
                                mListTechnames.add("No Technician Available");
                                ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(Add_Jobs.this,
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

                if ((!mTechnicianSpinner.getSelectedItem().toString().equalsIgnoreCase("No Technician Available")) || (!mTechnicianSpinner.getSelectedItem().toString().equalsIgnoreCase("Select Technician"))) {
                    if (mUserListTechnican != null) {
                        if (i!=0) {
                            mTechnicianId = mUserListTechnican.get(i-1).getUser_id();
                        }

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void tiemPicker() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;
                        mSelectedTime = mHour + ":" + mMinute;
                        updateLabel();
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void updateLabel() {
        if (mClickCalltypes == 1) {
            mClickCalltypes = 0;
            String aCurrentDateTime = mSelectedDate + " " + mSelectedTime;
            mCallReceivedEdt.setText(aCurrentDateTime);
        } else if (mClickCalltypes == 2) {
            mClickCalltypes = 0;
            String aCurrentDateTime = mSelectedDate + " " + mSelectedTime;
            ;
            mCallPromisedEdt.setText(aCurrentDateTime);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.service:
                startActivity(new Intent(Add_Jobs.this, SHow_Services.class));
                break;
            case R.id.backbutton:
                finish();
                break;
            case R.id.addjobs:
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

                if (TextUtils.isEmpty(aNameval)) {
                    Show_Toast("Please Enter Name");
                } else if (TextUtils.isEmpty(aAddressval)) {
                    Show_Toast("Please Enter Address");
                } else if (TextUtils.isEmpty(aProvinceval)) {
                    Show_Toast("Please Enter Province");
                } else if (TextUtils.isEmpty(aCityval)) {
                    Show_Toast("Please Enter City");
                } else if (TextUtils.isEmpty(aMobilval)) {
                    Show_Toast("Please Enter Mobile Number");
                } else if (TextUtils.isEmpty(aHomephoneval)) {
                    Show_Toast("Please Enter Home Phone No");
                } else if (TextUtils.isEmpty(aEmailval)) {
                    Show_Toast("Please Enter Email-Id");
                } else if (TextUtils.isEmpty(aCallReceivedval)) {
                    Show_Toast("Please Select Date");
                } else if (TextUtils.isEmpty(aCallPromisedval)) {
                    Show_Toast("Please Select Date");
                } else if (TextUtils.isEmpty(aFindusval)) {
                    Show_Toast("Please Enter Findus");
                } else if (TextUtils.isEmpty(aWorkdescval)) {
                    Show_Toast("Please Enter Work Description");
                } else {

                    if (mIsJobs_Editable) {
                        Modify_JobApiCall(aAddressval, aCityval, aEmailval, aFindusval, aMobilval, aHomephoneval, aWorkdescval, aProvinceval, aNameval, aCurrentDateTime, aCallReceivedval, aCallPromisedval);
                    } else {
                        Add_jobApiCall(aAddressval, aCityval, aEmailval, aFindusval, aMobilval, aHomephoneval, aWorkdescval, aProvinceval, aNameval, aCurrentDateTime, aCallReceivedval, aCallPromisedval);
                    }


                }

                break;
            case R.id.callreceived:
                mClickCalltypes = 1;
                new DatePickerDialog(Add_Jobs.this, mDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.callpromised:
                mClickCalltypes = 2;
                new DatePickerDialog(Add_Jobs.this, mDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
        aRequest.setStatus(mJobStatus_Intent);

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
                                    Constants.mAdd_JobsOnclick = 1;
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

    private void Add_jobApiCall(String aAddressval, String aCityval, String aEmailval, String aFindusval, String aMobilval,
                                String aHomephoneval, String aWorkdescval, String aProvinceval, String aNameval, String aCurrentDateTime, String aCallReceivedval, String aCallPromisedval) {
        mProgress_Loader.show();
        AddJob_Request aRequest = new AddJob_Request();
        aRequest.setAddress(aAddressval);
        aRequest.setCity(aCityval);
        aRequest.setEmail(aEmailval);
        aRequest.setFindus(aFindusval);
        aRequest.setMobile(aMobilval);
        aRequest.setPhone(aHomephoneval);
        aRequest.setWorkdesc(aWorkdescval);
        aRequest.setProvince(aProvinceval);
        aRequest.setName(aNameval);
        aRequest.setCreateddate(aCurrentDateTime);
        aRequest.setCreatedby(PreferenceDetails.GetUserid());
        aRequest.setTechid(mTechnicianId);
        aRequest.setCallreceived(aCallReceivedval);
        aRequest.setCallpromised(aCallPromisedval);
        aRequest.setServicesList(SHow_Services.mServiceDetailsGlobal);
        Call<AddJobs_Response> aAddJobsapi = Utils_Functions.Get_API_Services().AddJobs(aRequest);
        aAddJobsapi.enqueue(new Callback<AddJobs_Response>() {
            @Override
            public void onResponse(Call<AddJobs_Response> call, Response<AddJobs_Response> response) {
                if (response.code() == 200) {
                    mProgress_Loader.dismiss();
                    AddJobs_Response aResponseJson = response.body();
                    if (aResponseJson != null) {
                        if (!TextUtils.isEmpty(aResponseJson.getServerMsg().getMsg())) {
                            if (aResponseJson.getServerMsg().getMsg().equalsIgnoreCase("Success")) {
                                Constants.mAdd_JobsOnclick = 1;
                                Show_Toast(aResponseJson.getServerMsg().getDisplayMsg());
                                finish();
                            } else
                                Show_Toast(aResponseJson.getServerMsg().getDisplayMsg());
                        }
                    }
                } else
                    mProgress_Loader.dismiss();
            }

            @Override
            public void onFailure(Call<AddJobs_Response> call, Throwable t) {
                mProgress_Loader.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Constants.mShowServices == 1) {
            Constants.mShowServices = 0;
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

    private void Show_Toast(String aMsg) {
        Toast.makeText(getApplicationContext(), aMsg, Toast.LENGTH_SHORT).show();
    }
}
