package com.timedia.metatron.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timedia.metatron.Adapter.JobFragment_Dashboard_Adapter;
import com.timedia.metatron.R;

public class Job_Fragment_Dashboard extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View aview = inflater.inflate(R.layout.job_category, container, false);
        return aview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Variableinit(view);
    }

    private void Variableinit(final View view) {
        TabLayout aTab_Layout = view.findViewById(R.id.tabs);
        final ViewPager aView_Pager = view.findViewById(R.id.viewpager);
        JobFragment_Dashboard_Adapter aAdapter = new JobFragment_Dashboard_Adapter(getChildFragmentManager());
        aAdapter.addFrag(new Assigned_Jobs_Fragment(), "All");
        aAdapter.addFrag(new UNAssigned_Jobs_Fragment(), "In Progress");
        aAdapter.addFrag(new UNAssigned_Jobs_Fragment(), "Completed");
        aView_Pager.setAdapter(aAdapter);
        aView_Pager.setOffscreenPageLimit(2);
        aTab_Layout.setupWithViewPager(aView_Pager);
        aView_Pager.setCurrentItem(0);
        aTab_Layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                aView_Pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}

