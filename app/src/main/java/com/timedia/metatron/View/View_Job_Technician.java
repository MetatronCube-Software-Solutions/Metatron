package com.timedia.metatron.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.timedia.metatron.R;
import com.timedia.metatron.Request_Model.ModiyJobs_Request;
import com.timedia.metatron.Response_Model.JobList_Response;
import com.timedia.metatron.Response_Model.PartsList;
import com.timedia.metatron.Response_Model.Server_Msg_Object;
import com.timedia.metatron.Response_Model.ServicesList;
import com.timedia.metatron.Response_Model.UsersList_Response;
import com.timedia.metatron.shared_data.PreferenceDetails;
import com.timedia.metatron.utils.CameraPermission;
import com.timedia.metatron.utils.Constants;
import com.timedia.metatron.utils.Utils_Functions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class View_Job_Technician extends AppCompatActivity implements View.OnClickListener {

    private EditText mMobileEdt, mHomephoneEdt, mEmailEdtEdt, mOtherPayEdt;
    private TextView mServiceEdt, mCallReceivedEdt,
            mCallPromisedEdt, mPartsEDT, mNameEdt, mAddressEdt, mProvinceEdt, mCityEdt, mWorkDescEdt;
    private KProgressHUD mProgress_Loader;
    private DatePickerDialog.OnDateSetListener mDate;
    private String mJobId_Intent, mJobStatus_Intent, mTechnicianId, mFindus;
    private boolean mIsJobs_Editable = false;
    // private Spinner mFindUsEdt;
    private JobList_Response mResponseJobs;
    private SignaturePad mSignaturePad, mTechSignaturePad;
    private RadioButton mDebit_Radio, mCash_Radio, mCheque_Radio, mMoney_Radio, mMaster_Radio, mOther_Radio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__job__technician);
        Variableinit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Constants.mShowServicesAssigned == 1) {
            Constants.mShowServicesAssigned = 0;
            if (SHow_Services.mServiceDetailsGlobal.size() > 0) {
                Constants.mChecked_Position.clear();
                for (int a = 0; a < SHow_Services.mServiceDetailsGlobal.size(); a++) {
                    Constants.mChecked_Position.put(Integer.valueOf(SHow_Services.mServiceDetailsGlobal.get(a).getService_id()), SHow_Services.mServiceDetailsGlobal.get(a).getService_id());
                }
                mServiceEdt.setText(SHow_Services.mServiceDetailsGlobal.size() + " Service Selected");
            } else {
                mServiceEdt.setText("Choose Service");
                Constants.mChecked_Position.clear();
            }
        }
        if (Constants.mShowParts == 1) {
            Constants.mShowParts = 0;

            ArrayList<PartsList> aListDuplicate = new ArrayList<>();
            aListDuplicate.addAll(Partsnames.mPartsListGlobal);

            ArrayList<String> aPartId = new ArrayList<>();

            Partsnames.mPartsListGlobal.clear();
            for (int i = 0; i < aListDuplicate.size(); i++) {
                if (Partsnames.mPartsListGlobal.size() == 0) {
                    aPartId.add(aListDuplicate.get(i).getPart_id());
                    Partsnames.mPartsListGlobal.add(aListDuplicate.get(i));
                } else {
                    if (!aPartId.contains(aListDuplicate.get(i).getPart_id())) {
                        Partsnames.mPartsListGlobal.add(aListDuplicate.get(i));
                    }
                }
            }
            if (Partsnames.mPartsListGlobal.size() > 0) {
                Constants.mChecked_Position_Parts.clear();
                for (int a = 0; a < Partsnames.mPartsListGlobal.size(); a++) {
                    Constants.mChecked_Position_Parts.put(Integer.valueOf(Partsnames.mPartsListGlobal.get(a).getPart_id()), Partsnames.mPartsListGlobal.get(a).getPart_id());
                }
                mPartsEDT.setText(Partsnames.mPartsListGlobal.size() + " Parts Selected");
            } else {
                mPartsEDT.setText("Choose Parts");
                Constants.mChecked_Position_Parts.clear();
            }
        }
    }

    private void Variableinit() {
        if (Build.VERSION.SDK_INT > 22) {
            CameraPermission marshMallowPermission = new CameraPermission(this);
            if (!marshMallowPermission.checkPermissionForCamera()) {
                marshMallowPermission.requestPermissionForCamera();
            }
            if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                marshMallowPermission.requestPermissionForExternalStorage();
            }
        }
        TextView aTooltitle = findViewById(R.id.toolbartitle);
        TextView aSaveJobs = findViewById(R.id.savejobs);
        final TextView aGenerateInvoice = findViewById(R.id.generateinvoice);
        ImageView aBackbutton = findViewById(R.id.backbutton);
        mDebit_Radio = findViewById(R.id.debitpay);
        mCash_Radio = findViewById(R.id.cashpay);
        mCheque_Radio = findViewById(R.id.chequepay);
        mMoney_Radio = findViewById(R.id.moneyorderpay);
        mMaster_Radio = findViewById(R.id.masterpay);
        mOther_Radio = findViewById(R.id.otherpay);
        mOtherPayEdt = findViewById(R.id.othertext);
        mOtherPayEdt.setVisibility(View.GONE);
        mOther_Radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mOtherPayEdt.setVisibility(View.VISIBLE);
                } else {
                    mOtherPayEdt.setVisibility(View.GONE);
                }
            }
        });
        final CheckBox aSignatureView = findViewById(R.id.signaturecheckbox);
        final LinearLayout aSignatureLayout = findViewById(R.id.linearsignaturelayout);
        final LinearLayout aPaymentOptions = findViewById(R.id.paymentmethods);
        aSignatureLayout.setVisibility(View.GONE);
        aPaymentOptions.setVisibility(View.GONE);
        aGenerateInvoice.setVisibility(View.GONE);
        aSignatureView.setText("Signature Disabled");
        aSignatureView.setChecked(false);
        aSignatureView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    aSignatureView.setText("Signature Enabled");
                    aSignatureLayout.setVisibility(View.VISIBLE);
                    aPaymentOptions.setVisibility(View.VISIBLE);
                    aGenerateInvoice.setVisibility(View.VISIBLE);
                } else {
                    aSignatureView.setText("Signature Disabled");
                    aSignatureLayout.setVisibility(View.GONE);
                    aPaymentOptions.setVisibility(View.GONE);
                    aGenerateInvoice.setVisibility(View.GONE);
                }
            }
        });
        mSignaturePad = findViewById(R.id.signature_pad);
        mTechSignaturePad = findViewById(R.id.customersignature_pad);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                Toast.makeText(View_Job_Technician.this, "OnStartSigning", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
            }

            @Override
            public void onClear() {
            }
        });
        mTechSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                Toast.makeText(View_Job_Technician.this, "OnStartSigning", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
            }

            @Override
            public void onClear() {
            }
        });
        Constants.mChecked_Position.clear();
        Constants.mChecked_Position_Parts.clear();
        mProgress_Loader = KProgressHUD.create(View_Job_Technician.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
        SHow_Services.mServiceDetailsGlobal.clear();

        mNameEdt = findViewById(R.id.name);
        mAddressEdt = findViewById(R.id.address);
        mProvinceEdt = findViewById(R.id.province);
        mCityEdt = findViewById(R.id.city);
        mMobileEdt = findViewById(R.id.mobile);
        mHomephoneEdt = findViewById(R.id.phone);
        mEmailEdtEdt = findViewById(R.id.email);
        mCallReceivedEdt = findViewById(R.id.callreceived);
        mCallPromisedEdt = findViewById(R.id.callpromised);
        //  mFindUsEdt = findViewById(R.id.findus);
        mServiceEdt = findViewById(R.id.service);
        mPartsEDT = findViewById(R.id.parts);
        mWorkDescEdt = findViewById(R.id.worldesc);
        aBackbutton.setOnClickListener(this);
        aSaveJobs.setOnClickListener(this);
        aGenerateInvoice.setOnClickListener(this);
        mServiceEdt.setOnClickListener(this);
        mPartsEDT.setOnClickListener(this);
        ArrayList<String> aFindusArray = new ArrayList<>();
        aFindusArray.add("How did you hear about us?");
        aFindusArray.add("Word of mouth");
        aFindusArray.add("Google");
        aFindusArray.add("Blog");
        aFindusArray.add("Online Ads");
        aFindusArray.add("Social media");
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(View_Job_Technician.this,
                R.layout.layout_adapter, R.id.viewlabel,
                aFindusArray);
        //  mFindUsEdt.setAdapter(spinnerArrayAdapter);

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
            mFindus = getIntent().getStringExtra("findus");
            String aWorkdesc = getIntent().getStringExtra("workdesc");
            if (!TextUtils.isEmpty(mJobId_Intent)) {
                SHow_Services.mServiceDetailsGlobal.clear();
                int aPosition = Integer.parseInt(getIntent().getStringExtra("pos"));
                mResponseJobs = aGson.fromJson(getIntent().getStringExtra("aJobslistjson"), JobList_Response.class);
                mTechnicianId = mResponseJobs.getJobsList().get(aPosition).getJob_techid();
                ArrayList<JobList_Response.JobsList> aResponsejobarray = new ArrayList<>();
                aResponsejobarray = mResponseJobs.getJobsList();
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

                if (aResponsejobarray.get(aPosition).getPartsList() != null) {
                    for (int a = 0; a < aResponsejobarray.get(aPosition).getPartsList().size(); a++) {
                        Constants.mChecked_Position_Parts.put(Integer.valueOf(aResponsejobarray.get(aPosition).getPartsList().get(a).getPart_id()), aResponsejobarray.get(aPosition).getPartsList().get(a).getPart_id());
                        PartsList aPartsModel = new PartsList();
                        aPartsModel.setPart_id(aResponsejobarray.get(aPosition).getPartsList().get(a).getPart_id());
                        aPartsModel.setPart_name(aResponsejobarray.get(aPosition).getPartsList().get(a).getPart_name());
                        aPartsModel.setPart_cost(aResponsejobarray.get(aPosition).getPartsList().get(a).getPart_cost());
                        Partsnames.mPartsListGlobal.add(aPartsModel);
                    }
                    if (aResponsejobarray.get(aPosition).getPartsList().size() > 0) {
                        mPartsEDT.setText(aResponsejobarray.get(aPosition).getPartsList().size() + " Parts Selected");
                    }
                } else {
                    mPartsEDT.setText("Choose Parts");
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
                mWorkDescEdt.setText(aWorkdesc);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.service:
                startActivity(new Intent(View_Job_Technician.this, SHow_Services.class));
                break;
            case R.id.backbutton:
                finish();
                break;
            case R.id.parts:
                Intent aIntent = new Intent(View_Job_Technician.this, Partsnames.class);
                aIntent.putExtra("jobid", mJobId_Intent);
                startActivity(aIntent);

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
                String aFindusval = mFindus;
                String aWorkdescval = mWorkDescEdt.getText().toString();

                Modify_JobApiCall(aAddressval, aCityval, aEmailval, aFindusval, aMobilval, aHomephoneval, aWorkdescval, aProvinceval, aNameval, aCurrentDateTime, aCallReceivedval, aCallPromisedval, 0, null, null);
                break;
            case R.id.generateinvoice:
                Bitmap signatureBitmapCustomer = mSignaturePad.getSignatureBitmap();
                ByteArrayOutputStream byteArrayOutputStreamCust = new ByteArrayOutputStream();
                signatureBitmapCustomer.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStreamCust);
                byte[] byteArray = byteArrayOutputStreamCust.toByteArray();
                String encodedCustomer = Base64.encodeToString(byteArray, Base64.DEFAULT);

                Bitmap signatureBitmapTech = mTechSignaturePad.getSignatureBitmap();
                ByteArrayOutputStream byteArrayOutputStreamTech = new ByteArrayOutputStream();
                signatureBitmapTech.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStreamTech);
                byte[] byteArrayTech = byteArrayOutputStreamTech.toByteArray();
                String encodedTech = Base64.encodeToString(byteArrayTech, Base64.DEFAULT);

                String aCurrentDateTime_ = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                String aNameval_ = mNameEdt.getText().toString();
                String aAddressval_ = mAddressEdt.getText().toString();
                String aProvinceval_ = mProvinceEdt.getText().toString();
                String aCityval_ = mCityEdt.getText().toString();
                String aMobilval_ = mMobileEdt.getText().toString();
                String aHomephoneval_ = mHomephoneEdt.getText().toString();
                String aEmailval_ = mEmailEdtEdt.getText().toString();
                String aCallReceivedval_ = mCallReceivedEdt.getText().toString();
                String aCallPromisedval_ = mCallPromisedEdt.getText().toString();
                String aFindusval_ = mFindus;
                String aWorkdescval_ = mWorkDescEdt.getText().toString();
                Modify_JobApiCall(aAddressval_, aCityval_, aEmailval_, aFindusval_, aMobilval_, aHomephoneval_, aWorkdescval_, aProvinceval_, aNameval_, aCurrentDateTime_, aCallReceivedval_, aCallPromisedval_, 1, encodedCustomer, encodedTech);

                break;

        }
    }

    private void Modify_JobApiCall(String aAddressval, String aCityval, String aEmailval, String aFindusval, String aMobilval, String aHomephoneval, String aWorkdescval, String aProvinceval, String aNameval, String aCurrentDateTime, String aCallReceivedval, String aCallPromisedval, final int aSubmitType, String aEncodeCUst, String aEncodeTech) {
        mProgress_Loader.show();
        String aPaymentOPtions = null;
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
        if (aSubmitType == 0) {
            aRequest.setSubmittype("0");
        } else {
            if (mOther_Radio.isChecked()) {
                aPaymentOPtions = mOtherPayEdt.getText().toString();
            } else {
                if (mDebit_Radio.isChecked()) {
                    aPaymentOPtions = "Debit";
                } else if (mCash_Radio.isChecked()) {
                    aPaymentOPtions = "Cash";
                } else if (mCheque_Radio.isChecked()) {
                    aPaymentOPtions = "Cheque";
                } else if (mMoney_Radio.isChecked()) {
                    aPaymentOPtions = "Money";
                } else if (mMaster_Radio.isChecked()) {
                    aPaymentOPtions = "Master";
                }
            }
            aRequest.setSubmittype("1");
            aRequest.setPaymentmode(aPaymentOPtions);
            aRequest.setCustomersign(aEncodeCUst);
            aRequest.setTechsign(aEncodeTech);
        }

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
                                    if (aSubmitType == 1) {
                                        finish();
                                    }
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
