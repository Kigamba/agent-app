package com.mb_nthw.landlord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView uAddLandlord,
              uAddTenat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uAddLandlord = (ImageView) findViewById(R.id.add_landlord);

        uAddTenat = (ImageView) findViewById(R.id.add_tenant);

        uAddLandlord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LandlordRegistration.class);
                startActivity(intent);
            }
        });

        uAddTenat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LandlordRegistration.class);
                startActivity(intent);
            }
        });



    }
}
