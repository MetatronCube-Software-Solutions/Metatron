package com.timedia.metatron.View;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.timedia.metatron.IPresenters.IPresenters;
import com.timedia.metatron.Interfaces.SwitchConstantKeys;
import com.timedia.metatron.Presenter_Views.IViews;
import com.timedia.metatron.Presenters.Parts_Presenter;
import com.timedia.metatron.R;
import com.timedia.metatron.Request_Model.AddParts_Request;
import com.timedia.metatron.Request_Model.EditParts_Request;
import com.timedia.metatron.Response_Model.Add_Parts_Response;
import com.timedia.metatron.shared_data.PreferenceDetails;
import com.timedia.metatron.utils.CameraPermission;
import com.timedia.metatron.utils.Constants;

import java.util.Calendar;

public class Add_Parts extends AppCompatActivity implements View.OnClickListener, IViews.PartsManagementDetails {


    private EditText mPartsName, mProductCost, mProductSerial, mProductType, mProductMake, mProductModel, mProductWarranty;
    private KProgressHUD mProgress_Loader;
    private IPresenters.UserParts_Presenters mParts_Presenter;
    private boolean mModify_Parts = false;
    private String mPart_IdIntent, mPartNameIntent, mPartsCostIntent, mPartsModifyIntent, mPartStatusIntent,
            mPartSerial_Intent, mPartType_Intent, mPartMake_Intent, mPartModel_Intent, mPartWarranty_Intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__parts);
        Variableinit();
    }

    private void Variableinit() {
        if (Build.VERSION.SDK_INT > 22) {
            CameraPermission marshMallowPermission = new CameraPermission(this);
            if (!marshMallowPermission.checkPermissionForCamera()) {
                marshMallowPermission.requestPermissionForCamera();
            }
            if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                marshMallowPermission.requestPermissionForExternalStorage();
            }
        }
        mParts_Presenter = new Parts_Presenter(Add_Parts.this);
        mProgress_Loader = KProgressHUD.create(Add_Parts.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
        mProductType = findViewById(R.id.producttypeedt);
        mProductMake = findViewById(R.id.productmakeedt);
        mProductModel = findViewById(R.id.productmodeledt);
        mProductWarranty = findViewById(R.id.productwarrantyedt);
        mPartsName = findViewById(R.id.productedt);
        mProductCost = findViewById(R.id.productcostedt);
        mProductSerial = findViewById(R.id.ed_product_no);
        TextView aAddProductView = findViewById(R.id.productaddbtn);
        TextView aToolbarTitle = findViewById(R.id.toolbartitle);
        ImageView aBackbutton = findViewById(R.id.backbutton);
        ImageView aPickNumber = findViewById(R.id.pickimage);
        aAddProductView.setOnClickListener(this);
        aBackbutton.setOnClickListener(this);
        aPickNumber.setOnClickListener(this);

        if (getIntent() != null) {
            mPart_IdIntent = getIntent().getStringExtra("partid");
            mPartNameIntent = getIntent().getStringExtra("name");
            mPartsCostIntent = getIntent().getStringExtra("cost");
            mPartsModifyIntent = getIntent().getStringExtra("modifyby");
            mPartStatusIntent = getIntent().getStringExtra("status");
            mPartSerial_Intent = getIntent().getStringExtra("serial");
            mPartType_Intent = getIntent().getStringExtra("type");
            mPartMake_Intent = getIntent().getStringExtra("make");
            mPartModel_Intent = getIntent().getStringExtra("model");
            mPartWarranty_Intent = getIntent().getStringExtra("warranty");
            if (!TextUtils.isEmpty(mPart_IdIntent)) {
                mModify_Parts = true;
                aAddProductView.setText("Update Product");
                aToolbarTitle.setText("Modify Product");
            }
            if (!TextUtils.isEmpty(mPartNameIntent)) {
                mPartsName.setText(mPartNameIntent);
            }
            if (!TextUtils.isEmpty(mPartsCostIntent)) {
                mProductCost.setText(mPartsCostIntent);
            }
            if (!TextUtils.isEmpty(mPartSerial_Intent)) {
                mProductSerial.setText(mPartSerial_Intent);
            }
            if (!TextUtils.isEmpty(mPartType_Intent)) {
                mProductType.setText(mPartType_Intent);
            }
            if (!TextUtils.isEmpty(mPartMake_Intent)) {
                mProductMake.setText(mPartMake_Intent);
            }
            if (!TextUtils.isEmpty(mPartModel_Intent)) {
                mProductModel.setText(mPartModel_Intent);
            }
            if (!TextUtils.isEmpty(mPartWarranty_Intent)) {
                mProductWarranty.setText(mPartWarranty_Intent);
            }

        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pickimage:
                mProductSerial.setText("");
                if (Build.VERSION.SDK_INT > 22) {
                    CameraPermission marshMallowPermission = new CameraPermission(Add_Parts.this);
                    if (!marshMallowPermission.checkPermissionForCamera()) {
                        marshMallowPermission.requestPermissionForCamera();
                    } else {
                        if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                            marshMallowPermission.requestPermissionForExternalStorage();
                        } else {
                            final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                                    .withActivity(Add_Parts.this)
                                    .withEnableAutoFocus(true)
                                    .withBleepEnabled(true)
                                    .withBackfacingCamera()
                                    .withText("Scanning....")
                                    .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                                        @Override
                                        public void onResult(com.google.android.gms.vision.barcode.Barcode barcode) {
                                            String aVinval = barcode.rawValue.trim();
                                            aVinval = aVinval.replace(" ", "");
                                            mProductSerial.setText(aVinval);
                                        }
                                    })
                                    .build();
                            materialBarcodeScanner.startScan();
                        }
                    }
                } else {
                    final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                            .withActivity(Add_Parts.this)
                            .withEnableAutoFocus(true)
                            .withBleepEnabled(true)
                            .withBackfacingCamera()
                            .withText("Scanning....")
                            .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                                @Override
                                public void onResult(com.google.android.gms.vision.barcode.Barcode barcode) {
                                    String aVinval = barcode.rawValue.trim();
                                    aVinval = aVinval.replace(" ", "");
                                    mProductSerial.setText(aVinval);
                                }
                            })
                            .build();
                    materialBarcodeScanner.startScan();
                }
                break;
            case R.id.productaddbtn:
                //HideSoftKeyboard(getApplicationContext());
                String aCurrentDateTime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                String aProductNameVal = mPartsName.getText().toString().trim();
                String aProductCostVal = mProductCost.getText().toString().trim();
                String aProductTypeVal = mProductType.getText().toString();
                String aProductMakeVal = mProductMake.getText().toString();
                String aProductModelVal = mProductModel.getText().toString();
                String aProductWarrantyVal = mProductWarranty.getText().toString();
                String aProductSerialVal = mProductSerial.getText().toString();
                if (TextUtils.isEmpty(aProductNameVal)) {
                    Show_Toast("Please Enter the Product name");
                } else if (TextUtils.isEmpty(aProductCostVal)) {
                    Show_Toast("Please Enter the Product Cost");
                } else if (TextUtils.isEmpty(aProductTypeVal)) {
                    Show_Toast("Please Enter the Product Type");
                } else if (TextUtils.isEmpty(aProductMakeVal)) {
                    Show_Toast("Please Enter the Product Make");
                } else if (TextUtils.isEmpty(aProductModelVal)) {
                    Show_Toast("Please Enter the Product Model");
                } else if (TextUtils.isEmpty(aProductWarrantyVal)) {
                    Show_Toast("Please Enter the Product Warranty");
                } else {
                    mProgress_Loader.show();

                    if (mModify_Parts) {
                        EditParts_Request aRequest = new EditParts_Request();
                        aRequest.setName(aProductNameVal);
                        aRequest.setCost(aProductCostVal);
                        aRequest.setModifieddate(aCurrentDateTime);
                        aRequest.setModifiedby(mPartsModifyIntent);
                        aRequest.setStatus(mPartStatusIntent);
                        aRequest.setPartid(mPart_IdIntent);
                        aRequest.setSerial(aProductSerialVal);
                        aRequest.setType(aProductTypeVal);
                        aRequest.setWarranty(aProductWarrantyVal);
                        aRequest.setMake(aProductMakeVal);
                        aRequest.setModel(aProductModelVal);
                        mParts_Presenter.ModifyParts(getApplicationContext(), aRequest);
                    } else {
                        AddParts_Request aRequest = new AddParts_Request();
                        aRequest.setName(aProductNameVal);
                        aRequest.setCost(aProductCostVal);
                        aRequest.setCreateddate(aCurrentDateTime);
                        aRequest.setCreatedby(PreferenceDetails.GetUserid());
                        aRequest.setSerial(aProductSerialVal);
                        aRequest.setType(aProductTypeVal);
                        aRequest.setWarranty(aProductWarrantyVal);
                        aRequest.setMake(aProductMakeVal);
                        aRequest.setModel(aProductModelVal);
                        mParts_Presenter.AddParts(getApplicationContext(), aRequest);
                    }

                }
                break;
            case R.id.backbutton:
                finish();
                break;
        }
    }

    public void HideSoftKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(Add_Parts.this.getCurrentFocus().getWindowToken(), 0);
    }

    private void Show_Toast(String aMsg) {
        Toast.makeText(getApplicationContext(), aMsg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void SuccessResult(int aResultCode, Object aObject) {
        mProgress_Loader.dismiss();
        if (aResultCode == SwitchConstantKeys.Parts_Mgmt.AddParts_Success) {
            Add_Parts_Response aResponse = (Add_Parts_Response) aObject;
            Show_Toast(aResponse.getServerMsg().getDisplayMsg());
            Constants.mAdd_PartsOnclick = 1;
            finish();

        }
    }

    @Override
    public void FailureResult(int aResultCode, Object aObject) {
        mProgress_Loader.dismiss();
        if (aObject.toString().equalsIgnoreCase("Something Went Wrong")) {
            Show_Toast("Something Went Wrong");
        } else if (aResultCode == SwitchConstantKeys.Parts_Mgmt.AddParts_failure) {
            Add_Parts_Response aResponse = (Add_Parts_Response) aObject;
            Show_Toast(aResponse.getServerMsg().getDisplayMsg());
        }
    }
}
