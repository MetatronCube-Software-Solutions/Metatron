package com.timedia.metatron.View;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.timedia.metatron.R;
import com.timedia.metatron.utils.RingButton;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Variableinit();
    }

    private void Variableinit() {

        TextView aSignin=findViewById(R.id.loginview);
        TextView aSignup=findViewById(R.id.registerbtn);
        aSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplication(), User_Login.class);
                startActivity(i);
                finish();

            }
        });
        aSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplication(), User_Registration.class);
                startActivity(i);

            }
        });


    }
}
