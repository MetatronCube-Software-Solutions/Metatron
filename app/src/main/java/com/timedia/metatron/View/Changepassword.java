package com.timedia.metatron.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.timedia.metatron.R;
import com.timedia.metatron.Request_Model.Changepassword_Request;
import com.timedia.metatron.Response_Model.ServerMessage_Response;
import com.timedia.metatron.shared_data.PreferenceDetails;
import com.timedia.metatron.utils.Utils_Functions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Changepassword extends AppCompatActivity {

    private KProgressHUD mProgress_Loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        Variableinit();
    }

    private void Variableinit() {
        ImageView aBackbutton = findViewById(R.id.backbutton);
        aBackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mProgress_Loader = KProgressHUD.create(Changepassword.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
        final EditText aOldpassword = findViewById(R.id.oldpasswordedt);
        final EditText aNewpassword = findViewById(R.id.oldpasswordedt);
        final EditText acnfrmpassword = findViewById(R.id.oldpasswordedt);
        TextView aChangepassword = findViewById(R.id.changepassword);
        aChangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aOldpassval = aOldpassword.getText().toString();
                String aNewpassval = aNewpassword.getText().toString();
                String aCnfrmpassval = acnfrmpassword.getText().toString();
                if (TextUtils.isEmpty(aOldpassval)) {
                    Show_Toast("Please Enter Old Password");
                } else if (TextUtils.isEmpty(aNewpassval)) {
                    Show_Toast("Please Enter New Password");
                } else if (TextUtils.isEmpty(aCnfrmpassval)) {
                    Show_Toast("Please Enter Confirm Password");
                } else if (!aNewpassval.equalsIgnoreCase(aCnfrmpassval)) {
                    Show_Toast("Sorry! Password Doesn't Match");
                } else {
                    mProgress_Loader.show();
                    Changepassword_Request aRequest = new Changepassword_Request();
                    aRequest.setNewpassword(aCnfrmpassval);
                    aRequest.setOldpassword(aOldpassval);
                    aRequest.setUserid(PreferenceDetails.GetUserid());
                    Call<ServerMessage_Response> aChangepasswordApi = Utils_Functions.Get_API_Services().ChangePassword(aRequest);
                    aChangepasswordApi.enqueue(new Callback<ServerMessage_Response>() {
                        @Override
                        public void onResponse(Call<ServerMessage_Response> call, Response<ServerMessage_Response> response) {
                            if (response.code() == 200) {
                                ServerMessage_Response aResponseJson = response.body();
                                if (aResponseJson != null) {
                                    mProgress_Loader.dismiss();
                                    if (aResponseJson != null) {
                                        if (!TextUtils.isEmpty(aResponseJson.getMsg())) {
                                            if (aResponseJson.getMsg().equalsIgnoreCase("Success")) {
                                                Show_Toast(aResponseJson.getDisplayMsg());
                                                finish();
                                            } else
                                                Show_Toast(aResponseJson.getDisplayMsg());
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
            }
        });
    }

    private void Show_Toast(String aMsg) {
        Toast.makeText(getApplicationContext(), aMsg, Toast.LENGTH_SHORT).show();
    }

}
