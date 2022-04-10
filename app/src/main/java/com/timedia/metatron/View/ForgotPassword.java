package com.timedia.metatron.View;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.timedia.metatron.IPresenters.IPresenters;
import com.timedia.metatron.Presenters.Validation_LoginPresenter;
import com.timedia.metatron.R;
import com.timedia.metatron.Request_Model.ForgotPassword_Request;
import com.timedia.metatron.Response_Model.ServerMessage_Response;
import com.timedia.metatron.shared_data.PreferenceManager;
import com.timedia.metatron.utils.Utils_Functions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private EditText mUserNameEdt, mEmailEdt;
    private KProgressHUD mProgress_Loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Variableinit();
    }

    private void Variableinit() {
        mProgress_Loader = KProgressHUD.create(ForgotPassword.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
        mUserNameEdt = findViewById(R.id.usernameedt);
        mEmailEdt = findViewById(R.id.passwordedt);
        TextView aLoginView = findViewById(R.id.Loginbtn);
        aLoginView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Loginbtn:
                String aUsernameval = mUserNameEdt.getText().toString().trim();
                String aEmailIdVal = mEmailEdt.getText().toString().trim();
                if (TextUtils.isEmpty(aUsernameval)) {
                    Show_Toast("Please Enter UserName");
                } else if (TextUtils.isEmpty(aEmailIdVal)) {
                    Show_Toast("Please Enter Email Id");
                } else {
                    ForgotPassword_Request aRequest = new ForgotPassword_Request();
                    aRequest.setUsername(aUsernameval);
                    aRequest.setEmail(aEmailIdVal);
                    mProgress_Loader.show();
                    Call<ServerMessage_Response> aForgotApi = Utils_Functions.Get_API_Services().ForgotPassword(aRequest);
                    aForgotApi.enqueue(new Callback<ServerMessage_Response>() {
                        @Override
                        public void onResponse(Call<ServerMessage_Response> call, Response<ServerMessage_Response> response) {
                            if (response.code() == 200) {
                                mProgress_Loader.dismiss();
                                ServerMessage_Response aResponseJson = response.body();
                                if (aResponseJson != null) {
                                    if (!TextUtils.isEmpty(aResponseJson.getMsg())) {
                                        if (aResponseJson.getMsg().equalsIgnoreCase("Success")) {
                                            Show_Toast(aResponseJson.getDisplayMsg());
                                        } else
                                            Show_Toast(aResponseJson.getDisplayMsg());
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
                break;

        }
    }

    private void Show_Toast(String aMsg) {
        Toast.makeText(this, aMsg, Toast.LENGTH_SHORT).show();
    }

}
