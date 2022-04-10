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

import com.timedia.metatron.Adapter_OnclickInterface.Parts_Onclick;
import com.timedia.metatron.R;
import com.timedia.metatron.Response_Model.PartsList;
import com.timedia.metatron.Response_Model.ServicesList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListJobService_Adapter extends RecyclerView.Adapter<ListJobService_Adapter.ViewHolder> {

    private ArrayList<ServicesList> mServicesList;
    private Context mContext;
    private Parts_Onclick mParts_Onclick;

    public ListJobService_Adapter(Context aContext, ArrayList<ServicesList> aServicesList, Parts_Onclick aParts_Onclick) {
        this.mContext = aContext;
        this.mServicesList = aServicesList;
        this.mParts_Onclick = aParts_Onclick;
    }

    @NonNull
    @Override
    public ListJobService_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListJobService_Adapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_view, parent, false));
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
    public void onBindViewHolder(@NonNull ListJobService_Adapter.ViewHolder holder, int position) {

        if (!TextUtils.isEmpty(mServicesList.get(position).getService_name())) {
            holder.mPartsName.setText(mServicesList.get(position).getService_name());
        }

        if (!TextUtils.isEmpty(mServicesList.get(position).getService_timing())) {
            holder.mTiming.setText("Timing: " + mServicesList.get(position).getService_timing());
        }


        if (!TextUtils.isEmpty(mServicesList.get(position).getService_cost())) {
            holder.mPartsCost.setText("$" + mServicesList.get(position).getService_cost());
        }

        if (!TextUtils.isEmpty(mServicesList.get(position).getService_createddate())) {
            try {
                Date d = (new SimpleDateFormat("yyyy-mm-dd hh:mm:ss")).parse(mServicesList.get(position).getService_createddate());
                String s2 = (new SimpleDateFormat("MMM dd yyyy hh:mm a")).format(d);

                holder.mPartsCreatedDate.setText(s2.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        holder.mParentLayout.setTag(position);
        holder.mEdit_icon.setTag(position);
        holder.mDelete_icon.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mServicesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private LinearLayout mParentLayout;
        private TextView mPartsName, mPartsCost, mPartsCreatedDate, mTiming;
        private ImageView mEdit_icon, mDelete_icon;

        ViewHolder(View itemView) {
            super(itemView);
            mParentLayout = itemView.findViewById(R.id.parentlayout);
            mPartsName = itemView.findViewById(R.id.partsname);
            mPartsCost = itemView.findViewById(R.id.partscost);
            mPartsCreatedDate = itemView.findViewById(R.id.createddate);
            mEdit_icon = itemView.findViewById(R.id.editicon);
            mDelete_icon = itemView.findViewById(R.id.deleteicon);
            mTiming = itemView.findViewById(R.id.timing);
            mEdit_icon.setOnClickListener(this);
            mDelete_icon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int aPosition = (int) v.getTag();
            switch (v.getId()) {
                case R.id.parentlayout:
                    mParts_Onclick.onParts_OnclickCallback(aPosition, 0);
                    break;
                case R.id.editicon:
                    mParts_Onclick.onParts_OnclickCallback(aPosition, 1);
                    break;
                case R.id.deleteicon:
                    mParts_Onclick.onParts_OnclickCallback(aPosition, 2);
                    break;
            }
        }
    }
}

