package com.timedia.metatron.View;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.timedia.metatron.IPresenters.IPresenters;
import com.timedia.metatron.Interfaces.IPreferenceKeys;
import com.timedia.metatron.Interfaces.SwitchConstantKeys;
import com.timedia.metatron.Presenter_Views.IViews;
import com.timedia.metatron.Presenters.Validation_ShopregPresenter;
import com.timedia.metatron.R;
import com.timedia.metatron.Response_Model.GetCountryDetails;
import com.timedia.metatron.Response_Model.UserRegister_Request;
import com.timedia.metatron.Response_Model.UserRegistration_Response;
import com.timedia.metatron.shared_data.PreferenceManager;
import com.timedia.metatron.utils.Utils_Functions;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Registration extends AppCompatActivity implements View.OnClickListener, IViews.AddUserShopReg {


    private EditText mUsernameEdt, mPasswordEdt, mNameEdt, mMobileEdt, mEmailEdt, mAddressEdt,
            mZipcode;
    private AutoCompleteTextView mCityEdt, mStateEdt, mCountryEdt;
    private CircleImageView mProfileImage;
    private String mEncodedString, mCountryId, mStateId;
    private IPresenters.UserShopReg_Presenters mShopreg_Presenter;
    private KProgressHUD mProgress_Loader;
    private HashMap<String, String> mCountryHash = new HashMap<>();
    private HashMap<String, String> mStateHash = new HashMap<>();
    private HashMap<String, String> mCityHash = new HashMap<>();
    private PreferenceManager mPref_Mgr=PreferenceManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__registration);
        Variableinit();
    }

    private void Variableinit() {
        mProgress_Loader = KProgressHUD.create(User_Registration.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
        mShopreg_Presenter = new Validation_ShopregPresenter(this);
        TextView aRegisterBtn = findViewById(R.id.registerbtn);
        mProfileImage = findViewById(R.id.profile_image);
        mUsernameEdt = findViewById(R.id.usernameedt);
        mPasswordEdt = findViewById(R.id.passwordedt);
        mNameEdt = findViewById(R.id.nameedt);
        mMobileEdt = findViewById(R.id.mobileedt);
        mEmailEdt = findViewById(R.id.useremailedt);
        mAddressEdt = findViewById(R.id.addressedt);
        mCityEdt = findViewById(R.id.cityedt);
        mStateEdt = findViewById(R.id.stateedt);
        mCountryEdt = findViewById(R.id.countryedt);
        mZipcode = findViewById(R.id.zipcodeedt);
        aRegisterBtn.setOnClickListener(this);
        mProfileImage.setOnClickListener(this);
        GetCountryStateCity(null, null, 1);
        mCountryEdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Object item = adapterView.getItemAtPosition(position);
                mCountryId = mCountryHash.get(item.toString());
                GetCountryStateCity(mCountryId, null, 2);
            }
        });
        mStateEdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Object item = adapterView.getItemAtPosition(position);
                mStateId = mStateHash.get(item.toString());
                GetCountryStateCity(mCountryId, mStateId, 3);
            }
        });
    }

    private void GetCountryStateCity(String aCountryid, String aStateId, final int aType) {
        mProgress_Loader.show();
        Call<GetCountryDetails> aCountryApi = Utils_Functions.Get_API_Services().GetCountryList(aCountryid, aStateId);
        aCountryApi.enqueue(new Callback<GetCountryDetails>() {
            @Override
            public void onResponse(Call<GetCountryDetails> call, Response<GetCountryDetails> response) {
                if (response.code() == 200) {
                    mProgress_Loader.dismiss();
                    GetCountryDetails aResponseJson = response.body();
                    if (aResponseJson.getServerMsg() != null) {
                        if (!TextUtils.isEmpty(aResponseJson.getServerMsg().getMsg())) {
                            if (aResponseJson.getServerMsg().getMsg().equalsIgnoreCase("SUCCESS")) {
                                if (aType == 1) {
                                    ArrayList<String> aCountryList = new ArrayList<>();
                                    for (int a = 0; a < aResponseJson.getLocationsList().size(); a++) {
                                        mCountryHash.put(aResponseJson.getLocationsList().get(a).getCountry(), aResponseJson.getLocationsList().get(a).getCountryId());
                                        aCountryList.add(aResponseJson.getLocationsList().get(a).getCountry());
                                    }
                                    ArrayAdapter<String> aCountryAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, aCountryList);
                                    mCountryEdt.setAdapter(aCountryAdapter);
                                } else if (aType == 2) {
                                    ArrayList<String> aStateList = new ArrayList<>();
                                    for (int a = 0; a < aResponseJson.getLocationsList().size(); a++) {
                                        mStateHash.put(aResponseJson.getLocationsList().get(a).getState(), aResponseJson.getLocationsList().get(a).getStateId());
                                        aStateList.add(aResponseJson.getLocationsList().get(a).getState());
                                    }
                                    ArrayAdapter<String> aStateAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, aStateList);
                                    mStateEdt.setAdapter(aStateAdapter);
                                } else if (aType == 3) {
                                    ArrayList<String> aCityList = new ArrayList<>();
                                    for (int a = 0; a < aResponseJson.getLocationsList().size(); a++) {
                                        mCityHash.put(aResponseJson.getLocationsList().get(a).getCity(), aResponseJson.getLocationsList().get(a).getCityId());
                                        aCityList.add(aResponseJson.getLocationsList().get(a).getCity());
                                    }
                                    ArrayAdapter<String> aCityAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, aCityList);
                                    mCityEdt.setAdapter(aCityAdapter);
                                }

                            }
                        }
                    }
                } else
                    mProgress_Loader.dismiss();
            }

            @Override
            public void onFailure(Call<GetCountryDetails> call, Throwable t) {
                mProgress_Loader.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerbtn:
                String aUsernameval = mUsernameEdt.getText().toString();
                String aPasswordval = mPasswordEdt.getText().toString();
                String aNameval = mNameEdt.getText().toString();
                String aMobileval = mMobileEdt.getText().toString();
                String aEmailval = mEmailEdt.getText().toString();
                String aAddressval = mAddressEdt.getText().toString();
                String aCityval = mCityEdt.getText().toString();
                String aStateval = mStateEdt.getText().toString();
                String aCountryval = mCountryEdt.getText().toString();
                String aZipcodeval = mZipcode.getText().toString();
                if (TextUtils.isEmpty(aUsernameval)) {
                    ShowToast("Please Enter Username");
                } else if (TextUtils.isEmpty(aPasswordval)) {
                    ShowToast("Please Enter Password");
                } else if (TextUtils.isEmpty(aNameval)) {
                    ShowToast("Please Enter Name");
                } else if (TextUtils.isEmpty(aMobileval)) {
                    ShowToast("Please Enter Mobile Number");
                } else if (TextUtils.isEmpty(aEmailval)) {
                    ShowToast("Please Enter Email-id");
                } else if (TextUtils.isEmpty(aAddressval)) {
                    ShowToast("Please Enter Address");
                } else if (TextUtils.isEmpty(aCityval)) {
                    ShowToast("Please Enter City");
                } else if (TextUtils.isEmpty(aStateval)) {
                    ShowToast("Please Enter State");
                } else if (TextUtils.isEmpty(aCountryval)) {
                    ShowToast("Please Enter Country");
                } else if (TextUtils.isEmpty(aZipcodeval)) {
                    ShowToast("Please Enter Zipcode");
                } else {

                    aCountryval = mCountryHash.get(aCountryval);
                    aStateval = mStateHash.get(aStateval);
                    aCityval = mCityHash.get(aCityval);
                    mProgress_Loader.show();
                    UserRegister_Request aRequest = new UserRegister_Request();
                    aRequest.setUsername(aUsernameval);
                    aRequest.setPassword(aPasswordval);
                    aRequest.setName(aNameval);
                    aRequest.setPhone(aMobileval);
                    aRequest.setEmail(aEmailval);
                    aRequest.setAddress(aAddressval);
                    aRequest.setCityId(aCityval);
                    aRequest.setStateId(aStateval);
                    aRequest.setCountryId(aCountryval);
                    aRequest.setCountry(mCountryEdt.getText().toString());
                    aRequest.setState(mStateEdt.getText().toString());
                    aRequest.setCity(mCityEdt.getText().toString());
                    aRequest.setZipcode(aZipcodeval);
                    aRequest.setLogo(mEncodedString);
                    mShopreg_Presenter.RegisterValidation(getApplicationContext(), aRequest);
                }


                break;
            case R.id.profile_image:
                String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                Permissions.check(this/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
                    @Override
                    public void onGranted() {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,
                                "Select Picture"), 1);
                    }
                });

                break;
        }
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Uri aUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(aUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    mEncodedString = encodeImage(selectedImage);
                    mProfileImage.setImageURI(aUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
    }

    public void ShowToast(String aMsg) {
        Toast.makeText(getApplicationContext(), aMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void RegisterShopsuccess(int aResultCode, Object aObject) {
        UserRegistration_Response aResponse = (UserRegistration_Response) aObject;
        if (aResponse != null) {
            mProgress_Loader.dismiss();
            if (aResponse.getServerMsg() != null) {
                if (!TextUtils.isEmpty(aResponse.getServerMsg().getMsg())) {
                    if (aResponse.getServerMsg().getMsg().equalsIgnoreCase("Success")){
                        mPref_Mgr.setString(IPreferenceKeys.UserDetail.FirstTimeUser, "1");
                        ShowToast(aResponse.getServerMsg().getDisplayMsg());
                        if (aResponse.getLoginReturn() != null) {
                            if (aResponse.getLoginReturn().getUser_type().equalsIgnoreCase("User") || aResponse.getLoginReturn().getUser_type().equalsIgnoreCase("Admin")) {
                                mPref_Mgr.setBoolean(IPreferenceKeys.PreferenceMgr.IsLoggedIn, true);
                                mPref_Mgr.setString(IPreferenceKeys.PreferenceMgr.UserType, "User");

                                if (aResponse.getSupport() != null) {
                                    mPref_Mgr.setString(IPreferenceKeys.Support.Phone, aResponse.getSupport().getPhone());
                                    mPref_Mgr.setString(IPreferenceKeys.Support.Email, aResponse.getSupport().getEmail());
                                    mPref_Mgr.setString(IPreferenceKeys.Support.Contact, aResponse.getSupport().getContact());
                                }

                                mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserId, aResponse.getLoginReturn().getUser_id());
                                mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserType, aResponse.getLoginReturn().getUser_type());
                                if (!TextUtils.isEmpty(aResponse.getLoginReturn().getUser_mobile())) {
                                    mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserMobile, aResponse.getLoginReturn().getUser_mobile());
                                } else
                                    mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserMobile, aResponse.getSiteData().getPhone());

                                mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserFname, aResponse.getLoginReturn().getUser_firstname());
                                mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserLname, aResponse.getLoginReturn().getUser_lastname());
                                mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserEmail, aResponse.getLoginReturn().getUser_email());
                                mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserEmpId, aResponse.getLoginReturn().getUser_empid());
                                mPref_Mgr.setString(IPreferenceKeys.UserDetail.Username, aResponse.getLoginReturn().getUser_username());
                                mPref_Mgr.setString(IPreferenceKeys.UserDetail.LoginResponse, new Gson().toJson(aResponse.getSiteData()));
                                mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserLogo, aResponse.getSiteData().getLogo());
                                startActivity(new Intent(User_Registration.this, Dashboard_User.class));
                                finishAffinity();
                            }
                        }

                    }
                    else
                        ShowToast(aResponse.getServerMsg().getDisplayMsg());
                }
            }
        } else
            mProgress_Loader.dismiss();
    }

    @Override
    public void RegisterShopfailure(int aResultCode, Object aObject) {
        if (aObject.toString().contains("Something Went Wrong")) {
            mProgress_Loader.dismiss();
            ShowToast(aObject.toString());
        } else {
            UserRegistration_Response aResponse = (UserRegistration_Response) aObject;
            mProgress_Loader.dismiss();
            if (aResultCode == SwitchConstantKeys.UserLogin.Login_Failure) {
                ShowToast(aResponse.getServerMsg().getDisplayMsg());
            }
        }
    }
}
