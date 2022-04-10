package com.timedia.metatron.View;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Window;
import android.widget.TextView;

import com.timedia.metatron.Fragment.Job_Category;
import com.timedia.metatron.Fragment.Profile_Fragment;
import com.timedia.metatron.FragmentManager.ThermoCare_Manager;
import com.timedia.metatron.Interfaces.IPreferenceKeys;
import com.timedia.metatron.R;
import com.timedia.metatron.Sliding_menu.DrawerAdapter;
import com.timedia.metatron.Sliding_menu.DrawerItem;
import com.timedia.metatron.Sliding_menu.SimpleItem;
import com.timedia.metatron.shared_data.PreferenceDetails;
import com.timedia.metatron.shared_data.PreferenceManager;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class Dashboard_technician extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_JObPick = 0;
    private static final int POS_Profile = 1;
    private static final int POS_LOGOUT = 2;
    private String[] screenTitles;
    private Drawable[] screenIcons;
    private SlidingRootNav slidingRootNav;
    public static Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_technician);


        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Jobs");
        setSupportActionBar(mToolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(mToolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_JObPick).setChecked(true),
                createItemFor(POS_Profile),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        TextView aUserName = findViewById(R.id.Username);
        TextView aUserType = findViewById(R.id.usertype);

        String aUserDetails = PreferenceDetails.GetUserFname() + " " + PreferenceDetails.GetUserLname();
        if (!TextUtils.isEmpty(aUserDetails)) {
            aUserName.setText(aUserDetails);
        }

        String aUserTypeDetails = PreferenceDetails.GetUserType();
        if (!TextUtils.isEmpty(aUserTypeDetails)) {
            aUserType.setText(aUserTypeDetails);
        }

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        adapter.setSelected(POS_JObPick);

        new ThermoCare_Manager().UpdateFragment(this, new Job_Category(), null);
    }

    @Override
    public void onBackPressed() {
        try {
            final Dialog mDialog = new Dialog(Dashboard_technician.this);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.exitlayout);
            TextView mYEs = mDialog.findViewById(R.id.yes);
            TextView mNo = mDialog.findViewById(R.id.no);
            mYEs.setOnClickListener(v -> {
                mDialog.dismiss();
                Dashboard_technician.this.finishAffinity();
            });
            mNo.setOnClickListener(v -> mDialog.dismiss());
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.usermenustechnician);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIconstechnician);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorPrimaryDark))
                .withSelectedTextTint(color(R.color.colorPrimaryDark));
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    public void onItemSelected(int position) {

        if (position == 0) {
            mToolbar.setTitle("Jobs");
            new ThermoCare_Manager().UpdateFragment(this, new Job_Category(), null);
        }  else if (position == 1) {
            mToolbar.setTitle("Profile");
            new ThermoCare_Manager().UpdateFragment(this, new Profile_Fragment(), null);
        } else if (position == POS_LOGOUT) {
            PreferenceManager aPref_Mgr=PreferenceManager.getInstance();
            aPref_Mgr.setBoolean(IPreferenceKeys.PreferenceMgr.IsLoggedIn, false);
            Intent aIntent = new Intent(Dashboard_technician.this, User_Login.class);
            Dashboard_technician.this.startActivity(aIntent);
            Dashboard_technician.this.finish();
        }
        slidingRootNav.closeMenu();
    }
}
