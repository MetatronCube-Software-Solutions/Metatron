package com.timedia.metatron.View;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;
import com.timedia.metatron.Adapter.Part_Names_Adapter;
import com.timedia.metatron.Interfaces.IPreferenceKeys;
import com.timedia.metatron.R;
import com.timedia.metatron.Response_Model.ListPartsNames_Response;
import com.timedia.metatron.Response_Model.PartsDetail_Response;
import com.timedia.metatron.Response_Model.PartsList;
import com.timedia.metatron.shared_data.PreferenceDetails;
import com.timedia.metatron.shared_data.PreferenceManager;
import com.timedia.metatron.utils.CameraPermission;
import com.timedia.metatron.utils.Constants;
import com.timedia.metatron.utils.Utils_Functions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Partsnames extends AppCompatActivity implements View.OnClickListener {

    private KProgressHUD mProgress_Loader;
    private RecyclerView mListService_Recyclerview;
    private Part_Names_Adapter mPart_Names_Adapter;
    private ArrayList<PartsList> mPartsList_Array = new ArrayList<>();
    public static ArrayList<PartsList> mPartsListGlobal = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partsnames);
        Variableinit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Constants.mShowParts = 1;
    }

    private void Variableinit() {
        mProgress_Loader = KProgressHUD.create(Partsnames.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);

        ImageView aBackbutton = findViewById(R.id.backbutton);
        FloatingActionButton aFabAddParts = findViewById(R.id.addparts);
        aFabAddParts.setOnClickListener(this);
        mListService_Recyclerview = findViewById(R.id.servicelist);
        mListService_Recyclerview.setHasFixedSize(false);
        mListService_Recyclerview.setItemViewCacheSize(20);
        mListService_Recyclerview.setDrawingCacheEnabled(true);
        mListService_Recyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mListService_Recyclerview.setLayoutManager(mLayoutManager);
        aBackbutton.setOnClickListener(this);
        mProgress_Loader.show();
        Call<ListPartsNames_Response> aPartsNames = Utils_Functions.Get_API_Services().ListPartsNames(getIntent().getStringExtra("jobid"),PreferenceDetails.GetUserid());
        aPartsNames.enqueue(new Callback<ListPartsNames_Response>() {
            @Override
            public void onResponse(Call<ListPartsNames_Response> call, Response<ListPartsNames_Response> response) {
                if (response.code() == 200) {
                    mProgress_Loader.dismiss();
                    ListPartsNames_Response aResponseJson = response.body();
                    if (aResponseJson != null) {
                        if (!TextUtils.isEmpty(aResponseJson.getServerMsg().getMsg())) {
                            if (aResponseJson.getServerMsg().getMsg().equalsIgnoreCase("Success")) {
                                mPartsList_Array = aResponseJson.getPartsList();
                                mPart_Names_Adapter = new Part_Names_Adapter(getApplicationContext(), mPartsList_Array);
                                mListService_Recyclerview.setAdapter(mPart_Names_Adapter);
                            }
                        }
                    }
                } else
                    mProgress_Loader.dismiss();
            }

            @Override
            public void onFailure(Call<ListPartsNames_Response> call, Throwable t) {
                mProgress_Loader.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backbutton:
                finish();
                break;
            case R.id.addparts:
                AlertDialog.Builder aDialogBuilder = new AlertDialog.Builder(Partsnames.this);
                View aView = getLayoutInflater().inflate(R.layout.add_parts, null);
                aDialogBuilder.setView(aView);
                final EditText aSerialKey = aView.findViewById(R.id.ed_product_no);
                TextView aCancel = aView.findViewById(R.id.cancelbutton);
                TextView aOk = aView.findViewById(R.id.okbutton);
                ImageView aPIckSerial = aView.findViewById(R.id.pickimage);
                final AlertDialog aDialogView = aDialogBuilder.create();
                aDialogView.show();
                aCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        aDialogView.dismiss();
                    }
                });
                aOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!TextUtils.isEmpty(aSerialKey.getText().toString())) {

                            Call<PartsDetail_Response> aPartDetailApi = Utils_Functions.Get_API_Services().GetPartsDetail(aSerialKey.getText().toString(), PreferenceDetails.GetUserid());
                            aPartDetailApi.enqueue(new Callback<PartsDetail_Response>() {
                                @Override
                                public void onResponse(Call<PartsDetail_Response> call, Response<PartsDetail_Response> response) {
                                    if (response.code() == 200) {
                                        PartsDetail_Response aResponse = response.body();
                                        if (aResponse != null) {
                                            if (aResponse.getServerMsg() != null) {
                                                if (!TextUtils.isEmpty(aResponse.getServerMsg().getMsg())) {
                                                    if (aResponse.getServerMsg().getMsg().equalsIgnoreCase("Success")) {
                                                        if (aResponse.getPartsData() != null) {
                                                            Constants.mChecked_Position_Parts.put(Integer.valueOf(aResponse.getPartsData().getPart_id()), aResponse.getPartsData().getPart_id());
                                                            PartsList aPartsModel = new PartsList();
                                                            aPartsModel.setPart_id(aResponse.getPartsData().getPart_id());
                                                            aPartsModel.setPart_name(aResponse.getPartsData().getPart_name());
                                                            aPartsModel.setPart_cost(aResponse.getPartsData().getPart_cost());
                                                            Partsnames.mPartsListGlobal.add(aPartsModel);
                                                            mPart_Names_Adapter.notifyDataSetChanged();
                                                            aDialogView.dismiss();
                                                        }
                                                    } else
                                                        aDialogView.dismiss();
                                                }
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<PartsDetail_Response> call, Throwable t) {

                                }
                            });
                        }

                    }
                });
                aPIckSerial.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        aSerialKey.setText("");
                        if (Build.VERSION.SDK_INT > 22) {
                            CameraPermission marshMallowPermission = new CameraPermission(Partsnames.this);
                            if (!marshMallowPermission.checkPermissionForCamera()) {
                                marshMallowPermission.requestPermissionForCamera();
                            } else {
                                if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                                    marshMallowPermission.requestPermissionForExternalStorage();
                                } else {
                                    final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                                            .withActivity(Partsnames.this)
                                            .withEnableAutoFocus(true)
                                            .withBleepEnabled(true)
                                            .withBackfacingCamera()
                                            .withText("Scanning....")
                                            .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                                                @Override
                                                public void onResult(com.google.android.gms.vision.barcode.Barcode barcode) {
                                                    String aVinval = barcode.rawValue.trim();
                                                    aVinval = aVinval.replace(" ", "");
                                                    aSerialKey.setText(aVinval);
                                                }
                                            })
                                            .build();
                                    materialBarcodeScanner.startScan();
                                }
                            }
                        } else {
                            final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                                    .withActivity(Partsnames.this)
                                    .withEnableAutoFocus(true)
                                    .withBleepEnabled(true)
                                    .withBackfacingCamera()
                                    .withText("Scanning....")
                                    .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                                        @Override
                                        public void onResult(com.google.android.gms.vision.barcode.Barcode barcode) {
                                            String aVinval = barcode.rawValue.trim();
                                            aVinval = aVinval.replace(" ", "");
                                            aSerialKey.setText(aVinval);
                                        }
                                    })
                                    .build();
                            materialBarcodeScanner.startScan();
                        }
                    }
                });
                break;
        }
    }
}
