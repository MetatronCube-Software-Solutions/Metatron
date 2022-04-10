package com.timedia.metatron.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.timedia.metatron.IPresenters.IPresenters;
import com.timedia.metatron.Interfaces.IPreferenceKeys;
import com.timedia.metatron.Interfaces.SwitchConstantKeys;
import com.timedia.metatron.Presenter_Views.IViews;
import com.timedia.metatron.Presenters.Validation_LoginPresenter;
import com.timedia.metatron.R;
import com.timedia.metatron.Request_Model.UserLogin_Request;
import com.timedia.metatron.Response_Model.UserLogin_Response;
import com.timedia.metatron.shared_data.PreferenceManager;

public class User_Login extends AppCompatActivity implements View.OnClickListener, IViews.GetUserLoginDetails {

    private EditText mUserNameEdt, mPasswordEdt;
    private IPresenters.UserLogin_Presenters mLogin_Presenter;
    private KProgressHUD mProgress_Loader;
    private PreferenceManager mPref_Mgr;
    private String mUsernameval, mPasswordval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__login);
        Variableinit();
    }

    private void Variableinit() {
        TextView aForgotPass = findViewById(R.id.forgotpass);
        TextView aUserregistration = findViewById(R.id.userregistration);
        aUserregistration.setPaintFlags(aUserregistration.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        aForgotPass.setPaintFlags(aForgotPass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        aForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(User_Login.this, ForgotPassword.class));
            }
        });
        mPref_Mgr = PreferenceManager.getInstance();
        mProgress_Loader = KProgressHUD.create(User_Login.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
        mLogin_Presenter = new Validation_LoginPresenter(this);
        mUserNameEdt = findViewById(R.id.usernameedt);
        mPasswordEdt = findViewById(R.id.passwordedt);
       /* mUserNameEdt.setText("user1");
        mPasswordEdt.setText("user1");*/
        /*mUserNameEdt.setText("admin");
        mPasswordEdt.setText("admin");*/
        TextView aLoginView = findViewById(R.id.Loginbtn);
        aLoginView.setOnClickListener(this);
        aUserregistration.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Loginbtn:
                //HideSoftKeyboard(getApplicationContext());
                mUsernameval = mUserNameEdt.getText().toString().trim();
                mPasswordval = mPasswordEdt.getText().toString().trim();
                if (TextUtils.isEmpty(mUsernameval)) {
                    Show_Toast("Please Enter UserName");
                } else if (TextUtils.isEmpty(mPasswordval)) {
                    Show_Toast("Please Enter Password");
                } else {
                    mProgress_Loader.show();
                    UserLogin_Request aRequest = new UserLogin_Request();
                    aRequest.setUsername(mUsernameval);
                    aRequest.setPassword(mPasswordval);
                    mLogin_Presenter.LoginValidation(getApplicationContext(), aRequest);
                }
                break;
            case R.id.userregistration:
                Intent i = new Intent(getApplication(), User_Registration.class);
                startActivity(i);
                break;
            default:
        }
    }

    private void Show_Toast(String aMsg) {
        Toast.makeText(this, aMsg, Toast.LENGTH_SHORT).show();
    }

    public void HideSoftKeyboard(Context context) {
        View view = User_Login.this.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void UserLoginSuccess(int aResultCode, Object aObject) {
        UserLogin_Response aLoginResp = (UserLogin_Response) aObject;
        mProgress_Loader.dismiss();
        if (aResultCode == SwitchConstantKeys.UserLogin.Login_Success) {
            mPref_Mgr.setString(IPreferenceKeys.UserDetail.FirstTimeUser, "1");
            if (aLoginResp.getLoginReturn() != null) {
                if (aLoginResp.getLoginReturn().getUser_type().equalsIgnoreCase("User") || aLoginResp.getLoginReturn().getUser_type().equalsIgnoreCase("Admin")) {
                    mPref_Mgr.setBoolean(IPreferenceKeys.PreferenceMgr.IsLoggedIn, true);
                    mPref_Mgr.setString(IPreferenceKeys.PreferenceMgr.UserType, "User");

                    if (aLoginResp.getSupport() != null) {
                        mPref_Mgr.setString(IPreferenceKeys.Support.Phone, aLoginResp.getSupport().getPhone());
                        mPref_Mgr.setString(IPreferenceKeys.Support.Email, aLoginResp.getSupport().getEmail());
                        mPref_Mgr.setString(IPreferenceKeys.Support.Contact, aLoginResp.getSupport().getContact());
                    }

                    mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserId, aLoginResp.getLoginReturn().getUser_id());
                    mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserType, aLoginResp.getLoginReturn().getUser_type());
                    if (!TextUtils.isEmpty(aLoginResp.getLoginReturn().getUser_mobile())) {
                        mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserMobile, aLoginResp.getLoginReturn().getUser_mobile());
                    } else
                        mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserMobile, aLoginResp.getSiteData().getPhone());

                    mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserFname, aLoginResp.getLoginReturn().getUser_firstname());
                    mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserLname, aLoginResp.getLoginReturn().getUser_lastname());
                    mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserEmail, aLoginResp.getLoginReturn().getUser_email());
                    mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserEmpId, aLoginResp.getLoginReturn().getUser_empid());
                    mPref_Mgr.setString(IPreferenceKeys.UserDetail.Username, aLoginResp.getLoginReturn().getUser_username());
                    mPref_Mgr.setString(IPreferenceKeys.UserDetail.LoginResponse, new Gson().toJson(aLoginResp.getSiteData()));
                    mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserLogo, aLoginResp.getSiteData().getLogo());
                    mPref_Mgr.setString(IPreferenceKeys.SiteData.SiteDataId,aLoginResp.getSiteData().getId());
                    startActivity(new Intent(User_Login.this, Dashboard_User.class));
                    finishAffinity();
                } else if (aLoginResp.getLoginReturn().getUser_type().equalsIgnoreCase("Technician")) {
                    mPref_Mgr.setBoolean(IPreferenceKeys.PreferenceMgr.IsLoggedIn, true);
                    mPref_Mgr.setString(IPreferenceKeys.PreferenceMgr.UserType, "Technician");

                    mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserId, aLoginResp.getLoginReturn().getUser_id());
                    mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserType, aLoginResp.getLoginReturn().getUser_type());
                    mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserMobile, aLoginResp.getLoginReturn().getUser_mobile());
                    mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserFname, aLoginResp.getLoginReturn().getUser_firstname());
                    mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserLname, aLoginResp.getLoginReturn().getUser_lastname());
                    mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserEmail, aLoginResp.getLoginReturn().getUser_email());
                    mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserEmpId, aLoginResp.getLoginReturn().getUser_empid());
                    startActivity(new Intent(User_Login.this, Dashboard_technician.class));
                    finishAffinity();
                }
            }
        }
    }

    @Override
    public void UserError(int aResultCode, Object aObject) {
        if (aObject.toString().contains("Something Went Wrong")) {
            mProgress_Loader.dismiss();
            Show_Toast(aObject.toString());
        } else {
            UserLogin_Response aLoginResp = (UserLogin_Response) aObject;
            mProgress_Loader.dismiss();
            if (aResultCode == SwitchConstantKeys.UserLogin.Login_Failure) {
                Show_Toast(aLoginResp.getServerMsg().getDisplayMsg());
            }
        }

    }
}
