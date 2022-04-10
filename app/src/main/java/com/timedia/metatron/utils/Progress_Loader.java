package com.timedia.metatron.utils;

import android.app.Activity;
import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;

public class Progress_Loader {

    private static KProgressHUD mKProgressHUD;
    private static final String DEFAULT_MSG = "Loading ....";

    public Progress_Loader(Activity aContext) {
      //  mKProgressHUD = new KProgressHUD(aContext);
        mKProgressHUD = KProgressHUD.create(aContext);

    }

    public void Show_Khprogress() {
        mKProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
        mKProgressHUD.show();
    }

    public void IsShowing() {
        if (mKProgressHUD.isShowing()) {
            mKProgressHUD.dismiss();
        }
    }

    public void Show_KhprogressTitle(String aMsg) {
        mKProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(aMsg)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }
}
