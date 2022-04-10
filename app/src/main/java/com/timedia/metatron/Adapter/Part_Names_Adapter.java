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

import com.timedia.metatron.R;
import com.timedia.metatron.Response_Model.PartsList;
import com.timedia.metatron.View.Partsnames;
import com.timedia.metatron.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class Part_Names_Adapter  extends RecyclerView.Adapter<Part_Names_Adapter.MyViewHolder> {

    private ArrayList<PartsList> mPartsListArray = new ArrayList<>();
    private HashMap<String, String> aHashMap = new HashMap<>();

    public Part_Names_Adapter(Context mContext, ArrayList<PartsList> aPartsListArray) {
        this.mPartsListArray = aPartsListArray;
        aHashMap.clear();
    }

    @NonNull
    @Override
    public Part_Names_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.servicejob_adapter, parent, false);
        return new Part_Names_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Part_Names_Adapter.MyViewHolder holder, final int position) {

        if (!TextUtils.isEmpty(mPartsListArray.get(position).getPart_name())) {
            holder.mServiceName.setText(mPartsListArray.get(position).getPart_name());
        }

        if (!TextUtils.isEmpty(mPartsListArray.get(position).getPart_cost())) {
            holder.mServiceCost.setText("$" + mPartsListArray.get(position).getPart_cost());
        }


        if (Constants.mChecked_Position_Parts.size() > 0) {
            String aSerId = Constants.mChecked_Position_Parts.get(Integer.parseInt(mPartsListArray.get(position).getPart_id()));
            if (!TextUtils.isEmpty(aSerId)) {
                if (aSerId.equalsIgnoreCase(mPartsListArray.get(position).getPart_id())) {
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
                    PartsList aPartsListModel = new PartsList();
                    aPartsListModel.setPart_id(mPartsListArray.get(aPosition).getPart_id());
                    aPartsListModel.setPart_name(mPartsListArray.get(aPosition).getPart_name());
                    aPartsListModel.setPart_cost(mPartsListArray.get(aPosition).getPart_cost());
                    Partsnames.mPartsListGlobal.add(aPartsListModel);
                    holder.mCheckmaricon.setVisibility(View.VISIBLE);
                    aHashMap.put(String.valueOf(aPosition), String.valueOf(aPosition));
                } else {
                    if (aHashmapsk.equalsIgnoreCase(String.valueOf(aPosition))) {
                        aHashMap.remove(aHashmapsk);
                        for (int a = 0; a < Partsnames.mPartsListGlobal.size(); a++) {
                            String aShowId = Partsnames.mPartsListGlobal.get(a).getPart_id();
                            if (mPartsListArray.get(aPosition).getPart_id().equalsIgnoreCase(aShowId)) {
                                Partsnames.mPartsListGlobal.remove(a);
                                Constants.mChecked_Position_Parts.remove(mPartsListArray.get(aPosition).getPart_id());
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
        return mPartsListArray.size();
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
