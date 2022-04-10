package com.timedia.metatron.shared_data;


import android.content.Context;
import android.content.SharedPreferences;


public class PreferenceManager {
    private static final String SHARED_PREFS = "App_Sh_Prefs";
    private static SharedPreferences.Editor mEditor;
    private static SharedPreferences mSharedPrefs;
    private static PreferenceManager sInstance;

    public static synchronized PreferenceManager getInstance() {
        PreferenceManager preferenceManager;
        synchronized (PreferenceManager.class) {
            if (sInstance == null) {
                sInstance = new PreferenceManager();
            }
            preferenceManager = sInstance;
        }
        return preferenceManager;
    }

    public static void init(Context ctx) {
        mSharedPrefs = ctx.getSharedPreferences(SHARED_PREFS, 0);
        mEditor = mSharedPrefs.edit();
    }

    public String getString(String key, String defString) {
        return mSharedPrefs.getString(key, defString);
    }

    public void setString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public boolean getBoolean(String key) {
        return mSharedPrefs.getBoolean(key, false);
    }

    public void setBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }



}
