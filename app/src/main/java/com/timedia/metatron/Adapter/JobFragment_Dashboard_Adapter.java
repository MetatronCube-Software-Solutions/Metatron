package com.timedia.metatron.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.timedia.metatron.Fragment.All_Jobs_Fragment;
import com.timedia.metatron.Fragment.Assigned_Jobs_Fragment;
import com.timedia.metatron.Fragment.InProgressJob_Fragment;
import com.timedia.metatron.Fragment.Jobs_Fragment;
import com.timedia.metatron.Fragment.UNAssigned_Jobs_Fragment;

import java.util.ArrayList;
import java.util.List;

public class JobFragment_Dashboard_Adapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public JobFragment_Dashboard_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getItemPosition(Object object) {
        return Job_Category_Adapter.POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new All_Jobs_Fragment();
            case 1:
                return new InProgressJob_Fragment();
            case 2:
                return new Jobs_Fragment();
            default:
                return new All_Jobs_Fragment();
        }
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}

