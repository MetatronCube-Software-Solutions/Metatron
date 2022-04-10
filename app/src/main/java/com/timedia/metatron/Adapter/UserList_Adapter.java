package com.timedia.metatron.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.timedia.metatron.Adapter_OnclickInterface.Users_Onclick;
import com.timedia.metatron.R;
import com.timedia.metatron.Response_Model.UsersList_Response;

import java.util.ArrayList;

public class UserList_Adapter extends RecyclerView.Adapter<UserList_Adapter.MyViewHolder> {

    private ArrayList<UsersList_Response.UsersList> mUsersList_array = new ArrayList<>();
    private Users_Onclick mUsers_Onclick;

    public UserList_Adapter(Context mContext, ArrayList<UsersList_Response.UsersList> aUsersList_array, Users_Onclick aUsers_Onclick) {
        this.mUsersList_array = aUsersList_array;
        this.mUsers_Onclick = aUsers_Onclick;
    }

    @NonNull
    @Override
    public UserList_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.userlist_adapter, parent, false);
        return new UserList_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserList_Adapter.MyViewHolder holder, final int position) {

        if (!TextUtils.isEmpty(mUsersList_array.get(position).getUser_empid())) {
            holder.mEmployeeId.setText("Emp Id: "+mUsersList_array.get(position).getUser_empid());
        }
        if (!TextUtils.isEmpty(mUsersList_array.get(position).getUser_email())) {
            holder.mEmployeeEmail.setText("Email: "+mUsersList_array.get(position).getUser_email());
        }
        if (!TextUtils.isEmpty(mUsersList_array.get(position).getUser_firstname())) {
            String aName = mUsersList_array.get(position).getUser_firstname();
            if (!TextUtils.isEmpty(aName)) {
                if (!TextUtils.isEmpty(mUsersList_array.get(position).getUser_lastname())) {
                    aName = aName + " " + mUsersList_array.get(position).getUser_lastname();
                    holder.mEmployeeName.setText("Name: "+aName);
                }
            }
        } else
            holder.mEmployeeName.setText("Name: "+mUsersList_array.get(position).getUser_lastname());

        if (!TextUtils.isEmpty(mUsersList_array.get(position).getUser_mobile())) {
            holder.mEmployeeMobile.setText("Mobile: "+mUsersList_array.get(position).getUser_mobile());
        }
        if (!TextUtils.isEmpty(mUsersList_array.get(position).getUser_username())) {
            holder.mEmployeeUsername.setText("Username: "+mUsersList_array.get(position).getUser_username());
        }
        if (!TextUtils.isEmpty(mUsersList_array.get(position).getUser_type())) {
            if (mUsersList_array.get(position).getUser_type().equalsIgnoreCase("User")) {
                holder.mUser_Type.setText("Admin");
            } else
            holder.mUser_Type.setText(mUsersList_array.get(position).getUser_type());
        }

        holder.mEdit_Icon.setTag(position);
        holder.mDelete_Icon.setTag(position);

    }

    @Override
    public int getItemCount() {
        return mUsersList_array.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mUser_Type, mEmployeeId, mEmployeeUsername, mEmployeeName, mEmployeeMobile, mEmployeeEmail;
        private ImageView mEdit_Icon, mDelete_Icon;

        MyViewHolder(View view) {
            super(view);
            mUser_Type = view.findViewById(R.id.headerusertype);
            mEmployeeId = view.findViewById(R.id.employeeid);
            mEmployeeUsername = view.findViewById(R.id.employeeusername);
            mEmployeeName = view.findViewById(R.id.employeename);
            mEmployeeMobile = view.findViewById(R.id.employeemobile);
            mEmployeeEmail = view.findViewById(R.id.employeeemail);
            mEdit_Icon = view.findViewById(R.id.editicon);
            mDelete_Icon = view.findViewById(R.id.deleteicon);
            mEdit_Icon.setOnClickListener(this);
            mDelete_Icon.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int aPos=(int)view.getTag();
            switch (view.getId())
            {
                case R.id.editicon:
                    mUsers_Onclick.onUsers_OnclickCallback(aPos,0);
                    break;
                case R.id.deleteicon:
                    mUsers_Onclick.onUsers_OnclickCallback(aPos,1);
                    break;
            }
        }
    }
}

