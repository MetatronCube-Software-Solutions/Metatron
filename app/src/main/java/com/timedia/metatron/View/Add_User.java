package com.timedia.metatron.View;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.timedia.metatron.IPresenters.IPresenters;
import com.timedia.metatron.Interfaces.IPreferenceKeys;
import com.timedia.metatron.Interfaces.SwitchConstantKeys;
import com.timedia.metatron.Presenter_Views.IViews;
import com.timedia.metatron.Presenters.Add_UsersPresenter;
import com.timedia.metatron.R;
import com.timedia.metatron.Request_Model.AddUser_Request;
import com.timedia.metatron.Request_Model.ModifyUser_Request;
import com.timedia.metatron.Response_Model.AddUser_Response;
import com.timedia.metatron.Response_Model.IsUniqueUser_Response;
import com.timedia.metatron.Response_Model.Server_Msg_Object;
import com.timedia.metatron.shared_data.PreferenceDetails;
import com.timedia.metatron.shared_data.PreferenceManager;
import com.timedia.metatron.utils.Constants;
import com.timedia.metatron.utils.Utils_Functions;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_User extends AppCompatActivity implements View.OnClickListener, IViews.UserRegistrationDetails, View.OnFocusChangeListener {

    private EditText mFirstNameEdt, mLastNameEdt, mMobileEdt, mEmailEdt, mUserNameEdt, mPasswordEdt, mEmployeeEdt;
    private IPresenters.AddUser_Presenters mAddUser_Presenter;
    private KProgressHUD mProgress_Loader;
    private String mUsername_Intent, mUserPassword_Intent, mUserId_Intent;
    private boolean mIsUserEdit = false;
    private Spinner mUser_TypeSpinner;
    private PreferenceManager mPref_Mgr = PreferenceManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__user);
        Variableinit();
    }

    private void Variableinit() {
        mProgress_Loader = KProgressHUD.create(Add_User.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
        mAddUser_Presenter = new Add_UsersPresenter(this);
        mFirstNameEdt = findViewById(R.id.firstnamedt);
        mLastNameEdt = findViewById(R.id.lastnamedt);
        mMobileEdt = findViewById(R.id.mobileedt);
        mEmailEdt = findViewById(R.id.emailedt);
        mUserNameEdt = findViewById(R.id.usernameedt);
        mPasswordEdt = findViewById(R.id.passwordedt);
        mEmployeeEdt = findViewById(R.id.empdt);
        TextView aAdd_UserView = findViewById(R.id.adduserbtn);
        TextView aHeaderTitle = findViewById(R.id.headertitle);
        ImageView aBackbutton = findViewById(R.id.backbutton);
        mUser_TypeSpinner = findViewById(R.id.usertypespinner);
        ArrayList<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("Select User Type");
        spinnerArray.add("Admin");
        spinnerArray.add("Technician");
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                R.layout.layout_adapter,R.id.viewlabel,
                spinnerArray);
        mUser_TypeSpinner.setAdapter(spinnerArrayAdapter);
        aAdd_UserView.setOnClickListener(this);
        mUserNameEdt.setOnFocusChangeListener(this);
        aBackbutton.setOnClickListener(this);


        if (getIntent() != null) {
            String aEmpId = getIntent().getStringExtra("empid");
            String aFname = getIntent().getStringExtra("fname");
            String aLname = getIntent().getStringExtra("lname");
            String aMobile = getIntent().getStringExtra("mobile");
            String aEmail = getIntent().getStringExtra("email");
            String aUsername = getIntent().getStringExtra("username");
            String aUsertype = getIntent().getStringExtra("usertype");
            mUserPassword_Intent = getIntent().getStringExtra("password");
            mUserId_Intent = getIntent().getStringExtra("userid");
            mUsername_Intent = aUsername;
            if (!TextUtils.isEmpty(mUserId_Intent)) {
                mIsUserEdit = true;
                mEmployeeEdt.setText(aEmpId);
                mFirstNameEdt.setText(aFname);
                mLastNameEdt.setText(aLname);
                mMobileEdt.setText(aMobile);
                mEmailEdt.setText(aEmail);
                mUserNameEdt.setText(aUsername);
                aHeaderTitle.setText("Modify User");
                aAdd_UserView.setText("Update User");
                if (aUsertype.equalsIgnoreCase("User")) {
                    mUser_TypeSpinner.setSelection(1);
                } else{
                    int aPosselec = spinnerArray.indexOf(aUsertype);
                    mUser_TypeSpinner.setSelection(aPosselec);
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
            case R.id.adduserbtn:
                String aCurrentDateTime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
               // HideSoftKeyboard(getApplicationContext());
                String aFirstNameVal = mFirstNameEdt.getText().toString().trim();
                String aLastNameVal = mLastNameEdt.getText().toString().trim();
                String aMobileVal = mMobileEdt.getText().toString().trim();
                String aEmailVal = mEmailEdt.getText().toString().trim();
                String aUserNameVal = mUserNameEdt.getText().toString().trim();
                String aPasswordVal = mPasswordEdt.getText().toString().trim();
                String aEmployeeid = mEmployeeEdt.getText().toString().trim();
                String aUserTypeval = mUser_TypeSpinner.getSelectedItem().toString();
                if (mIsUserEdit) {
                    if (TextUtils.isEmpty(aFirstNameVal)) {
                        Show_Toast("Please Enter First Name");
                    } else if (TextUtils.isEmpty(aLastNameVal)) {
                        Show_Toast("Please Enter Last Name");
                    } else if (TextUtils.isEmpty(aMobileVal)) {
                        Show_Toast("Please Enter Mobile Number");
                    } else if (TextUtils.isEmpty(aEmailVal)) {
                        Show_Toast("Please Enter Email Id");
                    } else if (TextUtils.isEmpty(aUserNameVal)) {
                        Show_Toast("Please Enter Username");
                    } else if (TextUtils.isEmpty(aEmployeeid)) {
                        Show_Toast("Please Enter Employee Id");
                    } else if (!aEmailVal.contains("@")) {
                        Show_Toast("Please Enter Valid Email Id");
                    } else if (aUserTypeval.equalsIgnoreCase("Select User Type")) {
                        Show_Toast("Please Select User Type");
                    } else {
                        String aPass = null;
                        if (TextUtils.isEmpty(aPasswordVal)) {
                            aPass = mUserPassword_Intent;
                        } else
                            aPass = aPasswordVal;

                        ModifyUser_Request aRequest = new ModifyUser_Request();
                        aRequest.setEmpid(aEmployeeid);
                        aRequest.setEmail(aEmailVal);
                        aRequest.setFirstname(aFirstNameVal);
                        aRequest.setLastname(aLastNameVal);
                        aRequest.setMobile(aMobileVal);
                        aRequest.setStatus("1");
                        aRequest.setModifieddate(aCurrentDateTime);
                        aRequest.setModifiedby(PreferenceDetails.GetUserid());
                        aRequest.setPassword(aPass);
                        aRequest.setType(aUserTypeval);
                        aRequest.setUserid(mUserId_Intent);
                        aRequest.setUsername(aUserNameVal);
                        mProgress_Loader.show();
                        Call<Server_Msg_Object> aModiyUserApi = Utils_Functions.Get_API_Services().ModifyUsers(aRequest);
                        aModiyUserApi.enqueue(new Callback<Server_Msg_Object>() {
                            @Override
                            public void onResponse(Call<Server_Msg_Object> call, Response<Server_Msg_Object> response) {
                                if (response.code() == 200) {
                                    mProgress_Loader.dismiss();
                                    Server_Msg_Object aResponseJson = response.body();
                                    if (aResponseJson != null) {
                                        if (aResponseJson.getServerMsg() != null) {
                                            if (!TextUtils.isEmpty(aResponseJson.getServerMsg().getMsg())) {
                                                if (aResponseJson.getServerMsg().getMsg().equalsIgnoreCase("Success")) {
                                                    Show_Toast(aResponseJson.getServerMsg().getDisplayMsg());
                                                    finish();
                                                    Constants.mAdd_UsersOnclick = 1;
                                                } else
                                                    Show_Toast(aResponseJson.getServerMsg().getDisplayMsg());
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
                } else {
                    if (TextUtils.isEmpty(aFirstNameVal)) {
                        Show_Toast("Please Enter First Name");
                    } else if (TextUtils.isEmpty(aLastNameVal)) {
                        Show_Toast("Please Enter Last Name");
                    } else if (TextUtils.isEmpty(aMobileVal)) {
                        Show_Toast("Please Enter Mobile Number");
                    } else if (TextUtils.isEmpty(aEmailVal)) {
                        Show_Toast("Please Enter Email Id");
                    } else if (TextUtils.isEmpty(aUserNameVal)) {
                        Show_Toast("Please Enter Username");
                    } else if (TextUtils.isEmpty(aPasswordVal)) {
                        Show_Toast("Please Enter Password");
                    } else if (TextUtils.isEmpty(aEmployeeid)) {
                        Show_Toast("Please Enter Employee Id");
                    } else if (!aEmailVal.contains("@")) {
                        Show_Toast("Please Enter Valid Email Id");
                    } else if (aUserTypeval.equalsIgnoreCase("Select User Type")) {
                        Show_Toast("Please Select User Type");
                    } else {
                        mProgress_Loader.show();
                        AddUser_Request aRequest = new AddUser_Request();
                        aRequest.setFirstname(aFirstNameVal);
                        aRequest.setLastname(aLastNameVal);
                        aRequest.setEmail(aEmailVal);
                        aRequest.setMobile(aMobileVal);
                        aRequest.setUsername(aUserNameVal);
                        aRequest.setPassword(aPasswordVal);
                        aRequest.setEmpid(aEmployeeid);
                        aRequest.setType(aUserTypeval);
                        aRequest.setCreatedby(PreferenceDetails.GetUserid());
                        aRequest.setCreateddate(aCurrentDateTime);
                        mAddUser_Presenter.AddUser_Registration(getApplicationContext(), aRequest);
                    }
                }
                break;
        }
    }

    private void Show_Toast(String aMsg) {
        Toast.makeText(this, aMsg, Toast.LENGTH_SHORT).show();
    }

    public void HideSoftKeyboard(Context context) {
        View view = Add_User.this.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void AddUserSuccess(int aResultCode, Object aObject) {
        mProgress_Loader.dismiss();
        if (aResultCode == SwitchConstantKeys.AddUsers.AddUser_Success) {
            AddUser_Response aResponse = (AddUser_Response) aObject;
            Show_Toast(aResponse.getServerMsg().getDisplayMsg());
            Constants.mAdd_UsersOnclick = 1;
            finish();
        }

    }

    @Override
    public void AddUserError(int aResultCode, Object aObject) {
        mProgress_Loader.dismiss();
        if (aResultCode == SwitchConstantKeys.AddUsers.AddUser_Failure) {
            AddUser_Response aResponse = (AddUser_Response) aObject;
            Show_Toast(aResponse.getServerMsg().getDisplayMsg());
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (!b) {
            if (mIsUserEdit) {
                String aUserName = mUserNameEdt.getText().toString();
                if (!TextUtils.isEmpty(aUserName)) {
                    if (!aUserName.equalsIgnoreCase(mUsername_Intent)) {
                        CheckUsername_Unique(aUserName);
                    }
                }

            } else {
                String aUserName = mUserNameEdt.getText().toString();
                if (!TextUtils.isEmpty(aUserName)) {
                    CheckUsername_Unique(aUserName);
                }
            }
        }
    }

    public void CheckUsername_Unique(String aUname) {
        mProgress_Loader.show();

        Call<IsUniqueUser_Response> IsUniqueUserApi = Utils_Functions.Get_API_Services().IsUniqueUsername(aUname,PreferenceDetails.GetUserid());
        IsUniqueUserApi.enqueue(new Callback<IsUniqueUser_Response>() {
            @Override
            public void onResponse(Call<IsUniqueUser_Response> call, Response<IsUniqueUser_Response> response) {
                if (response.code() == 200) {
                    mProgress_Loader.dismiss();
                    IsUniqueUser_Response aIsUniqueUser = response.body();
                    if (aIsUniqueUser.isStatus()) {
                        Show_Toast("Unique UserName");
                    } else {
                        Show_Toast("UserName Already Exists");
                        mUserNameEdt.setText("");
                        mUserNameEdt.setFocusable(true);
                    }
                } else
                    mProgress_Loader.dismiss();
            }

            @Override
            public void onFailure(Call<IsUniqueUser_Response> call, Throwable t) {
                mProgress_Loader.dismiss();
            }
        });
    }
}
