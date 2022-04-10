package com.timedia.metatron.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;
import com.timedia.metatron.Adapter.UserList_Adapter;
import com.timedia.metatron.Adapter_OnclickInterface.Users_Onclick;
import com.timedia.metatron.Interfaces.IPreferenceKeys;
import com.timedia.metatron.R;
import com.timedia.metatron.Response_Model.ServerMessage_Response;
import com.timedia.metatron.Response_Model.UserLogin_Response;
import com.timedia.metatron.Response_Model.UsersList_Response;
import com.timedia.metatron.View.Add_User;
import com.timedia.metatron.shared_data.PreferenceDetails;
import com.timedia.metatron.shared_data.PreferenceManager;
import com.timedia.metatron.utils.Constants;
import com.timedia.metatron.utils.Utils_Functions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserMgmt_Fragment extends Fragment implements View.OnClickListener, Users_Onclick {

    private KProgressHUD mProgress_Loader;
    private FloatingActionButton mAddJobs;
    private RecyclerView mUserslist_Recyclerview;
    private ArrayList<UsersList_Response.UsersList> mUserList_Array = new ArrayList<>();
    private PreferenceManager mPref_Mgr = PreferenceManager.getInstance();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Variableinit(view);
    }

    private void Variableinit(View view) {
        mProgress_Loader = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
        mAddJobs = view.findViewById(R.id.addjobfab);
        mAddJobs.setOnClickListener(this);
        mUserslist_Recyclerview = view.findViewById(R.id.joblist);
        mUserslist_Recyclerview.setHasFixedSize(false);
        mUserslist_Recyclerview.setItemViewCacheSize(20);
        mUserslist_Recyclerview.setDrawingCacheEnabled(true);
        mUserslist_Recyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mUserslist_Recyclerview.setLayoutManager(mLayoutManager);
        Load_UsersList();
    }

    private void Load_UsersList() {
        mProgress_Loader.show();
        Call<UsersList_Response> aListUsers_Api = Utils_Functions.Get_API_Services().ListUsers(PreferenceDetails.GetUserid());
        aListUsers_Api.enqueue(new Callback<UsersList_Response>() {
            @Override
            public void onResponse(Call<UsersList_Response> call, Response<UsersList_Response> response) {
                if (response.code() == 200) {
                    mProgress_Loader.dismiss();
                    UsersList_Response aResponseJson = response.body();
                    if (aResponseJson != null) {
                        if (!TextUtils.isEmpty(aResponseJson.getServerMsg().getMsg())) {
                            if (aResponseJson.getServerMsg().getMsg().equalsIgnoreCase("Success")) {
                                mUserList_Array = aResponseJson.getUsersList();
                                if (mUserList_Array.size() > 0) {
                                    mUserslist_Recyclerview.setAdapter(new UserList_Adapter(getActivity(), mUserList_Array, UserMgmt_Fragment.this));
                                } else {
                                    mUserList_Array = new ArrayList<>();
                                    mUserslist_Recyclerview.setAdapter(new UserList_Adapter(getActivity(), mUserList_Array, UserMgmt_Fragment.this));
                                }
                            }
                        }
                    }
                } else
                    mProgress_Loader.dismiss();
            }

            @Override
            public void onFailure(Call<UsersList_Response> call, Throwable t) {
                mProgress_Loader.dismiss();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View aview = inflater.inflate(R.layout.parts_fragment, container, false);
        return aview;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addjobfab:
                startActivity(new Intent(getActivity(), Add_User.class));
                break;
        }
    }

    @Override
    public void onUsers_OnclickCallback(final int position, int type) {
        if (type == 0) {

            Intent aIntent = new Intent(getActivity(), Add_User.class);
            aIntent.putExtra("empid", mUserList_Array.get(position).getUser_empid());
            aIntent.putExtra("userid", mUserList_Array.get(position).getUser_id());
            aIntent.putExtra("fname", mUserList_Array.get(position).getUser_firstname());
            aIntent.putExtra("lname", mUserList_Array.get(position).getUser_lastname());
            aIntent.putExtra("mobile", mUserList_Array.get(position).getUser_mobile());
            aIntent.putExtra("email", mUserList_Array.get(position).getUser_email());
            aIntent.putExtra("username", mUserList_Array.get(position).getUser_username());
            aIntent.putExtra("password", mUserList_Array.get(position).getUser_password());
            aIntent.putExtra("usertype", mUserList_Array.get(position).getUser_type());
            startActivity(aIntent);

        } else if (type == 1) {


            AlertDialog.Builder aBuilderDialog = new AlertDialog.Builder(getActivity());
            aBuilderDialog.setTitle("Confirmation");
            aBuilderDialog.setMessage("Do You wish to Delete the User?");
            aBuilderDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mProgress_Loader.show();
                    Call<ServerMessage_Response> aRemoveUserApi = Utils_Functions.Get_API_Services().RemoveUsers(mUserList_Array.get(position).getUser_id());
                    aRemoveUserApi.enqueue(new Callback<ServerMessage_Response>() {
                        @Override
                        public void onResponse(Call<ServerMessage_Response> call, Response<ServerMessage_Response> response) {
                            if (response.code() == 200) {
                                mProgress_Loader.dismiss();
                                ServerMessage_Response aResponseJson = response.body();
                                if (aResponseJson != null) {
                                    if (!TextUtils.isEmpty(aResponseJson.getMsg())) {
                                        if (aResponseJson.getMsg().equalsIgnoreCase("Success")) {
                                            Load_UsersList();
                                        }
                                    }
                                }
                            } else
                                mProgress_Loader.dismiss();

                        }

                        @Override
                        public void onFailure(Call<ServerMessage_Response> call, Throwable t) {
                            mProgress_Loader.dismiss();
                        }
                    });
                }
            });
            aBuilderDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog aDialogwindow = aBuilderDialog.create();
            aDialogwindow.show();


        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Constants.mAdd_UsersOnclick == 1) {
            Constants.mAdd_UsersOnclick = 0;
            Load_UsersList();
        }
    }
}
