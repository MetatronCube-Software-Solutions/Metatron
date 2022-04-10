package com.timedia.metatron.Adapter;

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

import com.timedia.metatron.Adapter_OnclickInterface.Package_Onclick;
import com.timedia.metatron.R;
import com.timedia.metatron.Response_Model.ServicesList;
import com.timedia.metatron.View.SHow_Services;
import com.timedia.metatron.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class Service_Jobs_Adapter extends RecyclerView.Adapter<Service_Jobs_Adapter.MyViewHolder> {

    private ArrayList<ServicesList> mJoblist_Package = new ArrayList<>();
    private HashMap<String, String> aHashMap = new HashMap<>();
    private Package_Onclick mPackage_Onclick;

    public Service_Jobs_Adapter(Context mContext, ArrayList<ServicesList> aJoblist_Package, Package_Onclick aPackage_Onclick) {
        this.mJoblist_Package = aJoblist_Package;
        aHashMap.clear();
        this.mPackage_Onclick = aPackage_Onclick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.servicejob_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        if (!TextUtils.isEmpty(mJoblist_Package.get(position).getService_name())) {
            holder.mServiceName.setText(mJoblist_Package.get(position).getService_name());
        }

        if (!TextUtils.isEmpty(mJoblist_Package.get(position).getService_cost())) {
            holder.mServiceCost.setText("$" + mJoblist_Package.get(position).getService_cost());
        }


     /*   for (int a = 0; a < SHow_Services.mServiceDetailsGlobal.size(); a++) {

            if (mJoblist_Package.get(position).getService_id().equalsIgnoreCase(SHow_Services.mServiceDetailsGlobal.get(a).getService_id())) {
                holder.mCheckmaricon.setVisibility(View.VISIBLE);
            } else {
                holder.mCheckmaricon.setVisibility(View.INVISIBLE);
            }
        }*/

        if (Constants.mChecked_Position.size() > 0) {
            String aSerId = Constants.mChecked_Position.get(Integer.parseInt(mJoblist_Package.get(position).getService_id()));
            if (!TextUtils.isEmpty(aSerId)) {
                if (aSerId.equalsIgnoreCase(mJoblist_Package.get(position).getService_id())) {
                    aHashMap.put(String.valueOf(position), String.valueOf(position));
                    holder.mCheckmaricon.setVisibility(View.VISIBLE);
                }
            }
        }


        holder.mParentLayout.setTag(position);
        holder.mParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int aPosition = (int) view.getTag();
                String aHashmapsk = aHashMap.get(String.valueOf(aPosition));
                if (TextUtils.isEmpty(aHashmapsk)) {
                    ServicesList aServiceModel = new ServicesList();
                    aServiceModel.setService_id(mJoblist_Package.get(aPosition).getService_id());
                    aServiceModel.setService_name(mJoblist_Package.get(aPosition).getService_name());
                    aServiceModel.setService_cost(mJoblist_Package.get(aPosition).getService_cost());
                    SHow_Services.mServiceDetailsGlobal.add(aServiceModel);
                    holder.mCheckmaricon.setVisibility(View.VISIBLE);
                    aHashMap.put(String.valueOf(aPosition), String.valueOf(aPosition));
                } else {
                    if (aHashmapsk.equalsIgnoreCase(String.valueOf(aPosition))) {
                        aHashMap.remove(aHashmapsk);
                        for (int a = 0; a < SHow_Services.mServiceDetailsGlobal.size(); a++) {
                            String aShowId = SHow_Services.mServiceDetailsGlobal.get(a).getService_id();
                            if (mJoblist_Package.get(aPosition).getService_id().equalsIgnoreCase(aShowId)) {
                                SHow_Services.mServiceDetailsGlobal.remove(a);
                                Constants.mChecked_Position.remove(mJoblist_Package.get(aPosition).getService_id());
                            }
                        }
                        holder.mCheckmaricon.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mJoblist_Package.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mParentLayout;
        private TextView mServiceName, mServiceCost;
        private ImageView mCheckmaricon;

        MyViewHolder(View view) {
            super(view);
            mParentLayout = view.findViewById(R.id.parentlayout);
            mServiceName = view.findViewById(R.id.servicename);
            mServiceCost = view.findViewById(R.id.servicecost);
            mCheckmaricon = view.findViewById(R.id.checkmarkicon);
        }
    }
}
