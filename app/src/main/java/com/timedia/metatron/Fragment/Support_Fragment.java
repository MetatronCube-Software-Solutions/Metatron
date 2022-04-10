package com.timedia.metatron.Fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timedia.metatron.Interfaces.IPreferenceKeys;
import com.timedia.metatron.R;
import com.timedia.metatron.shared_data.PreferenceManager;

public class Support_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View aview = inflater.inflate(R.layout.support, container, false);
        return aview;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Variableinit(view);

    }

    private void Variableinit(View view) {
        final PreferenceManager aPrefMgr = PreferenceManager.getInstance();
        TextView aContactEmail = view.findViewById(R.id.accountemail);
        TextView aContactphone = view.findViewById(R.id.accountphone);
        TextView aContactus = view.findViewById(R.id.contact);
        aContactus.setPaintFlags(aContactus.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        aContactEmail.setText(aPrefMgr.getString(IPreferenceKeys.Support.Email, ""));
        aContactphone.setText(aPrefMgr.getString(IPreferenceKeys.Support.Phone, ""));
        aContactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = aPrefMgr.getString(IPreferenceKeys.Support.Contact, "");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}