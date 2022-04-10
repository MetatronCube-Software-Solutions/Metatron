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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;
import com.timedia.metatron.Adapter.PartsList_Adapter;
import com.timedia.metatron.Adapter_OnclickInterface.Parts_Onclick;
import com.timedia.metatron.IPresenters.IPresenters;
import com.timedia.metatron.Interfaces.SwitchConstantKeys;
import com.timedia.metatron.Presenter_Views.IViews;
import com.timedia.metatron.Presenters.Parts_Presenter;
import com.timedia.metatron.R;
import com.timedia.metatron.Response_Model.PartsList;
import com.timedia.metatron.Response_Model.Parts_List_Response;
import com.timedia.metatron.Response_Model.ServerMessage_Response;
import com.timedia.metatron.View.Add_Parts;
import com.timedia.metatron.shared_data.PreferenceManager;
import com.timedia.metatron.utils.Constants;

import java.util.ArrayList;

public class Parts_Fragment extends Fragment implements IViews.PartsManagementDetails, Parts_Onclick, View.OnClickListener {

    private RecyclerView mAdd_PartsRecyclerview;
    private IPresenters.UserParts_Presenters mParts_Presenter;
    private KProgressHUD mProgress_Loader;
    private PreferenceManager mPref_Mgr;
    private ArrayList<PartsList> mParts_ListArray = new ArrayList<>();
    private FloatingActionButton mAddParts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View aview = inflater.inflate(R.layout.parts_fragment, container, false);
        return aview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Variableinit(view);
    }

    private void Variableinit(View view) {
        mPref_Mgr = PreferenceManager.getInstance();
        mProgress_Loader = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
        mAddParts = view.findViewById(R.id.addjobfab);
        mAddParts.setOnClickListener(this);
        mParts_Presenter = new Parts_Presenter(Parts_Fragment.this);
        mAdd_PartsRecyclerview = view.findViewById(R.id.joblist);
        mAdd_PartsRecyclerview.setHasFixedSize(false);
        mAdd_PartsRecyclerview.setItemViewCacheSize(20);
        mAdd_PartsRecyclerview.setDrawingCacheEnabled(true);
        mAdd_PartsRecyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mAdd_PartsRecyclerview.setLayoutManager(mLayoutManager);
        mProgress_Loader.show();
        mParts_Presenter.ListParts(getActivity());

    }

    @Override
    public void SuccessResult(int aResultCode, Object aObject) {
        if (aResultCode == SwitchConstantKeys.Parts_Mgmt.ListParts_Success) {
            Parts_List_Response aResponse = (Parts_List_Response) aObject;
            if (aResponse.getPartsList() != null) {
                mParts_ListArray.clear();
                mParts_ListArray.addAll(aResponse.getPartsList());
                if (mParts_ListArray != null) {
                    if (mParts_ListArray.size() > 0) {
                        mProgress_Loader.dismiss();
                        mAdd_PartsRecyclerview.setAdapter(new PartsList_Adapter(getActivity(), mParts_ListArray, Parts_Fragment.this));
                    }
                }
            }
            mProgress_Loader.dismiss();
        } else if (aResultCode == SwitchConstantKeys.Parts_Mgmt.PartsRemove_Success) {
            ServerMessage_Response aResponse = (ServerMessage_Response) aObject;
            Show_Toast(aResponse.getDisplayMsg());
            mProgress_Loader.show();
            mParts_Presenter.ListParts(getActivity());
        } else {
            mProgress_Loader.dismiss();
        }
    }

    @Override
    public void FailureResult(int aResultCode, Object aObject) {
        mProgress_Loader.dismiss();

        if (aResultCode == SwitchConstantKeys.Parts_Mgmt.ListParts_Failure) {
            if (aObject.toString().contains("Something Went Wrong")) {
                mProgress_Loader.dismiss();
                Show_Toast(aObject.toString());
            } else {
                Parts_List_Response aResponse = (Parts_List_Response) aObject;
                Show_Toast(aResponse.getServerMsg().getDisplayMsg());
                mParts_ListArray.clear();
                mAdd_PartsRecyclerview.setAdapter(new PartsList_Adapter(getActivity(), mParts_ListArray, Parts_Fragment.this));
                mAdd_PartsRecyclerview.removeAllViews();
            }

        }
    }

    private void Show_Toast(String aMsg) {
        Toast.makeText(getContext(), aMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onParts_OnclickCallback(final int position, int type) {
        if (type == 1) {
            Intent aIntent = new Intent(getActivity(), Add_Parts.class);
            aIntent.putExtra("partid", mParts_ListArray.get(position).getPart_id());
            aIntent.putExtra("name", mParts_ListArray.get(position).getPart_name());
            aIntent.putExtra("cost", mParts_ListArray.get(position).getPart_cost());
            aIntent.putExtra("modifyby", mParts_ListArray.get(position).getPart_modifiedby());
            aIntent.putExtra("status", mParts_ListArray.get(position).getPart_status());
            aIntent.putExtra("serial", mParts_ListArray.get(position).getPart_serial());
            aIntent.putExtra("type", mParts_ListArray.get(position).getPart_type());
            aIntent.putExtra("make", mParts_ListArray.get(position).getPart_make());
            aIntent.putExtra("model", mParts_ListArray.get(position).getPart_model());
            aIntent.putExtra("warranty", mParts_ListArray.get(position).getPart_warranty());
            startActivity(aIntent);
        } else if (type == 2) {
            AlertDialog.Builder aBuilderDialog = new AlertDialog.Builder(getActivity());
            aBuilderDialog.setTitle("Confirmation");
            aBuilderDialog.setMessage("Do You wish to Delete the Parts?");
            aBuilderDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String aPartId = mParts_ListArray.get(position).getPart_id();
                    mProgress_Loader.show();
                    mParts_Presenter.RemoveParts(getActivity(), aPartId);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addjobfab:
                startActivity(new Intent(getActivity(), Add_Parts.class));
                break;
            default:
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Constants.mAdd_PartsOnclick == 1) {
            Constants.mAdd_PartsOnclick = 0;
            mProgress_Loader.show();
            mParts_Presenter.ListParts(getActivity());
        }
    }
}
