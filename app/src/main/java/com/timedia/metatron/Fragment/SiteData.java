package com.timedia.metatron.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.timedia.metatron.FragmentManager.ThermoCare_Manager;
import com.timedia.metatron.Interfaces.IPreferenceKeys;
import com.timedia.metatron.R;
import com.timedia.metatron.Request_Model.SiteDataRequest;
import com.timedia.metatron.Response_Model.GetCountryDetails;
import com.timedia.metatron.Response_Model.ServerMessage_Response;
import com.timedia.metatron.View.Dashboard_User;
import com.timedia.metatron.View.Dashboard_technician;
import com.timedia.metatron.shared_data.PreferenceDetails;
import com.timedia.metatron.shared_data.PreferenceManager;
import com.timedia.metatron.utils.Constants;
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

public class SiteData extends Fragment implements View.OnClickListener {

    private KProgressHUD mProgress_Loader;
    private View mView;
    private EditText mEmailid, mWebaddress, mPhone, mAddress, mZipcode, mTollFree, mMobile, mFax,
            mHST;
    private AutoCompleteTextView mCity, mState, mCountry;
    private TextView mSiteUpdate;
    PreferenceManager mPref_Mgr = PreferenceManager.getInstance();
    private String mId, mEncodedString, mCountryId, mStateId;
    private CircleImageView mProfileImage;

    private HashMap<String, String> mCountryHash = new HashMap<>();
    private HashMap<String, String> mStateHash = new HashMap<>();
    private HashMap<String, String> mCityHash = new HashMap<>();
    private String mImageURL = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.sitedatalayout, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Variableinit(view);
    }

    private void Variableinit(View view) {
        mProfileImage = view.findViewById(R.id.profile_image);
        mProgress_Loader = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);

        mEmailid = view.findViewById(R.id.siteemail);
        mWebaddress = view.findViewById(R.id.siteWebaddress);
        mPhone = view.findViewById(R.id.sitePhone);
        mAddress = view.findViewById(R.id.siteAddress);
        mCity = view.findViewById(R.id.siteCity);
        mState = view.findViewById(R.id.siteState);
        mCountry = view.findViewById(R.id.siteCountry);
        mZipcode = view.findViewById(R.id.siteZipcode);
        mTollFree = view.findViewById(R.id.siteTollFree);
        mMobile = view.findViewById(R.id.sitemobile);
        mFax = view.findViewById(R.id.siteFax);
        mHST = view.findViewById(R.id.siteHST);
        mSiteUpdate = view.findViewById(R.id.siteupdate);
        mSiteUpdate.setOnClickListener(this);
        mProfileImage.setOnClickListener(this);
        com.timedia.metatron.Response_Model.SiteData aLoginModel = new Gson().fromJson(mPref_Mgr.getString(IPreferenceKeys.UserDetail.LoginResponse, IPreferenceKeys.Common.EMPTY), com.timedia.metatron.Response_Model.SiteData.class);
        if (!TextUtils.isEmpty(PreferenceDetails.GetProfileImage())) {
            mImageURL = Constants.BASE_URL + PreferenceDetails.GetProfileImage();
            Glide.with(getActivity()).load(Constants.BASE_URL + PreferenceDetails.GetProfileImage()).into(mProfileImage);
        } else
            mProfileImage.setImageResource(R.drawable.roundedprofile);
        mId = PreferenceDetails.GetSitedataid();
        mEmailid.setText(aLoginModel.getEmail());
        mWebaddress.setText(aLoginModel.getWebaddress());
        mPhone.setText(aLoginModel.getPhone());
        mAddress.setText(aLoginModel.getAddress());
        mCity.setText(aLoginModel.getCity());
        mState.setText(aLoginModel.getState());
        mCountry.setText(aLoginModel.getCountry());
        mZipcode.setText(aLoginModel.getZipcode());
        mTollFree.setText(aLoginModel.getTollFree());
        mMobile.setText(aLoginModel.getMobile());
        mFax.setText(aLoginModel.getFax());
        if (!TextUtils.isEmpty(aLoginModel.getHST())) {
            if (!aLoginModel.getHST().equalsIgnoreCase("0")) {
                mHST.setText(aLoginModel.getHST());
            }
        }

        mCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Object item = adapterView.getItemAtPosition(position);
                mCountryId = mCountryHash.get(item.toString());
                GetCountryStateCity(mCountryId, null, 2);
            }
        });
        mState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Object item = adapterView.getItemAtPosition(position);
                mStateId = mStateHash.get(item.toString());
                GetCountryStateCity(mCountryId, mStateId, 3);
            }
        });
        GetCountryStateCity(null, null, 1);
        GetCountryStateCity(aLoginModel.getCountryId(), null, 2);
        GetCountryStateCity(aLoginModel.getCountryId(), aLoginModel.getStateId(), 3);
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
                                    ArrayAdapter<String> aCountryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, aCountryList);
                                    mCountry.setAdapter(aCountryAdapter);
                                } else if (aType == 2) {
                                    ArrayList<String> aStateList = new ArrayList<>();
                                    for (int a = 0; a < aResponseJson.getLocationsList().size(); a++) {
                                        mStateHash.put(aResponseJson.getLocationsList().get(a).getState(), aResponseJson.getLocationsList().get(a).getStateId());
                                        aStateList.add(aResponseJson.getLocationsList().get(a).getState());
                                    }
                                    ArrayAdapter<String> aStateAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, aStateList);
                                    mState.setAdapter(aStateAdapter);
                                } else if (aType == 3) {
                                    ArrayList<String> aCityList = new ArrayList<>();
                                    for (int a = 0; a < aResponseJson.getLocationsList().size(); a++) {
                                        mCityHash.put(aResponseJson.getLocationsList().get(a).getCity(), aResponseJson.getLocationsList().get(a).getCityId());
                                        aCityList.add(aResponseJson.getLocationsList().get(a).getCity());
                                    }
                                    ArrayAdapter<String> aCityAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, aCityList);
                                    mCity.setAdapter(aCityAdapter);
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
            case R.id.profile_image:
                String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                Permissions.check(getActivity()/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
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
            case R.id.siteupdate:

                String aEmailidval = mEmailid.getText().toString();
                String aWebaddressval = mWebaddress.getText().toString();
                String aPhoneval = mPhone.getText().toString();
                String aAddressval = mAddress.getText().toString();
                String aCityval = mCity.getText().toString();
                String aStateval = mState.getText().toString();
                String aCountryval = mCountry.getText().toString();
                String aZipcodeval = mZipcode.getText().toString();
                String aTollFreeval = mTollFree.getText().toString();
                String aMobileval = mMobile.getText().toString();
                String aFaxval = mFax.getText().toString();
                String aHSTval = mHST.getText().toString();
                aCountryval = mCountryHash.get(aCountryval);
                aStateval = mStateHash.get(aStateval);
                aCityval = mCityHash.get(aCityval);
                final SiteDataRequest aRequest = new SiteDataRequest();
                aRequest.setId(mId);
                aRequest.setEmail(aEmailidval);
                aRequest.setWebaddress(aWebaddressval);
                aRequest.setPhone(aPhoneval);
                aRequest.setAddress(aAddressval);
                aRequest.setCityId(aCityval);
                aRequest.setStateId(aStateval);
                aRequest.setCountryId(aCountryval);
                aRequest.setCountry(mCountry.getText().toString());
                aRequest.setState(mState.getText().toString());
                aRequest.setCity(mCity.getText().toString());
                aRequest.setZipcode(aZipcodeval);
                aRequest.setTollFree(aTollFreeval);
                aRequest.setMobile(aMobileval);
                aRequest.setFax(aFaxval);
                aRequest.setHST(aHSTval);
                aRequest.setLogo(mEncodedString);
                aRequest.setImageUrl(mImageURL);
                mProgress_Loader.show();
                Call<ServerMessage_Response> aCallSiteData = Utils_Functions.Get_API_Services().UpdateSiteData(aRequest);
                aCallSiteData.enqueue(new Callback<ServerMessage_Response>() {
                    @Override
                    public void onResponse(Call<ServerMessage_Response> call, Response<ServerMessage_Response> response) {
                        if (response.code() == 200) {
                            ServerMessage_Response aResponseJson = response.body();
                            if (aResponseJson != null) {
                                mProgress_Loader.dismiss();
                                if (!TextUtils.isEmpty(aResponseJson.getMsg())) {
                                    if (aResponseJson.getMsg().equalsIgnoreCase("Success")) {
                                        Toast.makeText(getActivity(), aResponseJson.getDisplayMsg(), Toast.LENGTH_SHORT).show();
                                        mPref_Mgr.setString(IPreferenceKeys.UserDetail.UserLogo, aResponseJson.getLogo());
                                        mPref_Mgr.setString(IPreferenceKeys.UserDetail.LoginResponse, new Gson().toJson(aRequest));
                                        Glide.with(getActivity()).load(Constants.BASE_URL + "" + PreferenceDetails.GetProfileImage()).into(Dashboard_User.mProfileLogo);
                                        Dashboard_User.mToolbar.setTitle("Jobs");
                                        new ThermoCare_Manager().UpdateFragment(getActivity(), new Job_Fragment_Dashboard(), null);
                                        // getActivity().finish();
                                    } else
                                        Toast.makeText(getActivity(), aResponseJson.getDisplayMsg(), Toast.LENGTH_SHORT).show();
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Uri aUri = data.getData();
                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(aUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    mEncodedString = encodeImage(selectedImage);
                    mImageURL = null;
                    mProfileImage.setImageURI(aUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
    }
}
