package com.timedia.metatron.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timedia.metatron.R;
import com.timedia.metatron.View.Changepassword;
import com.timedia.metatron.shared_data.PreferenceDetails;

public class Profile_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View aview = inflater.inflate(R.layout.profile_fragment, container, false);
        return aview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Variableinit(view);
    }

    private void Variableinit(View view) {

        TextView aEmpid = view.findViewById(R.id.accountempid);
        TextView aAccountName = view.findViewById(R.id.accountname);
        TextView aMobno = view.findViewById(R.id.accountmobno);
        TextView aEmail = view.findViewById(R.id.accountemail);
        TextView aUName = view.findViewById(R.id.username);
        TextView aChpassword = view.findViewById(R.id.chpassword);

        if (!TextUtils.isEmpty(PreferenceDetails.GetUserEmployeeId())) {
            aEmpid.setText(PreferenceDetails.GetUserEmployeeId());
        } else
            aEmpid.setVisibility(View.GONE);

        String aNamedetail = PreferenceDetails.GetUserFname() + " " + PreferenceDetails.GetUserLname();
        if (!TextUtils.isEmpty(aNamedetail)) {
            aAccountName.setText(aNamedetail);
        } else
            aAccountName.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(PreferenceDetails.GetUserMobile())) {
            aMobno.setText(PreferenceDetails.GetUserMobile());
        } else
            aMobno.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(PreferenceDetails.GetUserEmail())) {
            aEmail.setText(PreferenceDetails.GetUserEmail());
        } else
            aEmail.setVisibility(View.GONE);
        aUName.setText("UserName: " + PreferenceDetails.GetUsername());

        aChpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Changepassword.class));
            }
        });
    }
}
