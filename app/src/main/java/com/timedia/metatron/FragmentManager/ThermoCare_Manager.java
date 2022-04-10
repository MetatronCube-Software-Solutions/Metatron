package com.timedia.metatron.FragmentManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.timedia.metatron.R;

public class ThermoCare_Manager {
    public void UpdateFragment(FragmentActivity aContext, Fragment aFragment, Bundle aBundle) {
        FragmentManager aFragmentMgr = aContext.getSupportFragmentManager();
        FragmentTransaction aFtransaction = aFragmentMgr.beginTransaction();
        aFtransaction.replace(R.id.container, aFragment);
        aFtransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        aFtransaction.commit();
    }
}
