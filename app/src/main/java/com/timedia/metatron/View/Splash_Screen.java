package com.timedia.metatron.View;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.timedia.metatron.Interfaces.IPreferenceKeys;
import com.timedia.metatron.R;
import com.timedia.metatron.shared_data.PreferenceManager;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        Variableinit();
    }

    private void Variableinit() {
        PreferenceManager aPref_Mgr = PreferenceManager.getInstance();
        boolean aIsLoggedin = aPref_Mgr.getBoolean(IPreferenceKeys.PreferenceMgr.IsLoggedIn);
        String aIsFirstTimeUser = aPref_Mgr.getString(IPreferenceKeys.UserDetail.FirstTimeUser, IPreferenceKeys.Common.EMPTY);
        String aUserType = aPref_Mgr.getString(IPreferenceKeys.PreferenceMgr.UserType, IPreferenceKeys.Common.EMPTY);
        aIsFirstTimeUser = aIsFirstTimeUser.trim();
        if (TextUtils.isEmpty(aIsFirstTimeUser)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent aIntent = new Intent(Splash_Screen.this, HomeActivity.class);
                    Splash_Screen.this.startActivity(aIntent);
                    Splash_Screen.this.finish();
                }
            }, 2000);

        } else {
            if (aIsLoggedin) {
                if (aUserType.equalsIgnoreCase("User") || aUserType.equalsIgnoreCase("Admin")) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(Splash_Screen.this, Dashboard_User.class));
                            finish();
                        }
                    }, 2000);

                } else if (aUserType.equalsIgnoreCase("Technician")) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(Splash_Screen.this, Dashboard_technician.class));
                            finish();
                        }
                    }, 2000);

                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent aIntent = new Intent(Splash_Screen.this, User_Login.class);
                            Splash_Screen.this.startActivity(aIntent);
                            Splash_Screen.this.finish();
                        }
                    }, 2000);
                }
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent aIntent = new Intent(Splash_Screen.this, User_Login.class);
                        Splash_Screen.this.startActivity(aIntent);
                        Splash_Screen.this.finish();
                    }
                }, 2000);
            }
        }
    }
}
