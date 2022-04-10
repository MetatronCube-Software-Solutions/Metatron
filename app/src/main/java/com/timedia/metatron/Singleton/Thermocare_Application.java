package com.timedia.metatron.Singleton;


import android.app.Application;
import android.content.Context;

import com.timedia.metatron.shared_data.PreferenceManager;

public class Thermocare_Application extends Application {

    static Thermocare_Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.init(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static Thermocare_Application getInstance() {
        return instance;
    }
}
