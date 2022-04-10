package com.timedia.metatron.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.timedia.metatron.Adapter_OnclickInterface.Jobs_Onclick;
import com.timedia.metatron.R;
import com.timedia.metatron.Response_Model.JobList_Response;

import java.util.ArrayList;

public class Assigned_Jobs_Adapter extends RecyclerView.Adapter<Assigned_Jobs_Adapter.ViewHolder> {

    private ArrayList<JobList_Response.JobsList> mJobsList;
    private Context mContext;
    private Jobs_Onclick mJobs_Onclick;

    public Assigned_Jobs_Adapter(Context aContext, ArrayList<JobList_Response.JobsList> aJobsList, Jobs_Onclick aJobs_Onclick) {
        this.mContext = aContext;
        this.mJobsList = aJobsList;
        this.mJobs_Onclick = aJobs_Onclick;
    }

    @NonNull
    @Override
    public Assigned_Jobs_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Assigned_Jobs_Adapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assigned_job_adapter, parent, false));
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull Assigned_Jobs_Adapter.ViewHolder holder, int position) {

        if (!TextUtils.isEmpty(mJobsList.get(position).getJob_customername())) {
            holder.mCustomerName.setText(mJobsList.get(position).getJob_customername());
        }

        if (!TextUtils.isEmpty(mJobsList.get(position).getJob_address())) {
            String aAddress = mJobsList.get(position).getJob_address();
            aAddress = aAddress + "," + mJobsList.get(position).getJob_city() + "," + mJobsList.get(position).getJob_province();
            holder.mAddress.setText(aAddress);
        }

        if (!TextUtils.isEmpty(mJobsList.get(position).getJob_mobile())) {
            holder.mMobile.setText(mJobsList.get(position).getJob_mobile());
        }
        if (!TextUtils.isEmpty(mJobsList.get(position).getJob_email())) {
            holder.mEmail.setText(mJobsList.get(position).getJob_email());
        }
        if (!TextUtils.isEmpty(mJobsList.get(position).getJob_workdesc())) {
            holder.mDescription.setText(mJobsList.get(position).getJob_workdesc());
        }
        if (!TextUtils.isEmpty(mJobsList.get(position).getCost())) {
            holder.mCost.setText("$" + mJobsList.get(position).getCost());
        }

        holder.mParentLayout.setTag(position);
        holder.mEdit_icon.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mJobsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private LinearLayout mParentLayout;
        private TextView mCustomerName, mAddress, mMobile, mEmail, mCost, mDescription;
        private ImageView mEdit_icon;

        ViewHolder(View itemView) {
            super(itemView);
            mParentLayout = itemView.findViewById(R.id.parentlayout);
            mCustomerName = itemView.findViewById(R.id.customername);
            mAddress = itemView.findViewById(R.id.address);
            mMobile = itemView.findViewById(R.id.mobile);
            mEmail = itemView.findViewById(R.id.email);
            mCost = itemView.findViewById(R.id.cost);
            mDescription = itemView.findViewById(R.id.jobdescription);
            mEdit_icon = itemView.findViewById(R.id.editicon);
            mEdit_icon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int aPosition = (int) v.getTag();
            switch (v.getId()) {
                case R.id.editicon:
                    mJobs_Onclick.onJobs_OnclickCallback(aPosition, 1);
                    break;

            }
        }
    }
}
