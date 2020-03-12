package com.mb_nthw.landlord;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterLandlordUnits extends AppCompatActivity {

    private EditText uBuildingName,
                     uBuildingLocation,
                     uCaretakerPhoneNumber,
                     uTotalUnits,
                     uVacantUnits,
                     uRentInKsh,
                     uAddUnitType;

    Spinner houseTypesSpinner;



    private Button btn_save;

    DatabaseReference landlordUnitsRef;

    private final ThreadLocal<ProgressDialog> progressDialog = new ThreadLocal<ProgressDialog>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_landlord_units);



        uBuildingName = (EditText) findViewById(R.id.buildingName);
        uBuildingLocation = (EditText) findViewById(R.id.buildingLocation);
        uCaretakerPhoneNumber = (EditText) findViewById(R.id.caretakerPhoneNumber);
        uTotalUnits = (EditText) findViewById(R.id.totalUnits);
        uVacantUnits = (EditText) findViewById(R.id.vacantUnits);
        uRentInKsh = (EditText) findViewById(R.id.rentInKsh);
        uAddUnitType = (EditText) findViewById(R.id.addUnitType);



        houseTypesSpinner = (Spinner) findViewById(R.id.houseTypesSpinner);

        btn_save = (Button) findViewById(R.id.btn_save);

        progressDialog.set(new ProgressDialog(this));

        uAddUnitType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveLandlordUnit();

            }
        });
    }

    private void saveLandlordUnit() {


        final String buildingName = uBuildingName.getText().toString().trim();
        final String buildingLocation = uBuildingLocation.getText().toString().trim();
        final String caretakerPhoneNumber = uCaretakerPhoneNumber.getText().toString().trim();
        final String totalUnits = uTotalUnits.getText().toString();
        final String vacantUnits = uVacantUnits.getText().toString().trim();
        final String rentInKsh = uRentInKsh.getText().toString().trim();

        final String houseType = houseTypesSpinner.getSelectedItem().toString();



        if(TextUtils.isEmpty(buildingName)){
            Toast.makeText(this,getResources().getString(R.string.Please_enter_buildingName),Toast.LENGTH_LONG).show();
            return;

        }
        if(TextUtils.isEmpty(buildingLocation)){
            Toast.makeText(this,getResources().getString(R.string.Please_enter_buildingLocation),Toast.LENGTH_LONG).show();
            return;

        }
        if(TextUtils.isEmpty(caretakerPhoneNumber)){
            Toast.makeText(this,getResources().getString(R.string.Please_enter_caretakerPhoneNumber),Toast.LENGTH_LONG).show();
            return;

        }else if(caretakerPhoneNumber.length() != 10 ){

            Toast.makeText(this,getResources().getString(R.string.Please_enter_caretakerPhoneNumber),Toast.LENGTH_LONG).show();
            return;


        }
        if(TextUtils.isEmpty(totalUnits)){
            Toast.makeText(this,getResources().getString(R.string.Please_enter_totalUnits),Toast.LENGTH_LONG).show();

        }
        if(TextUtils.isEmpty(vacantUnits)){
            Toast.makeText(this,getResources().getString(R.string.Please_enter_vacantUnits),Toast.LENGTH_LONG).show();
            return;

        }
        if(TextUtils.isEmpty(rentInKsh)){
            Toast.makeText(this,getResources().getString(R.string.Please_enter_rentInKsh),Toast.LENGTH_LONG).show();
            return;

        }


        String uid = FirebaseDatabase.getInstance().getReference().child("Users").child("LandlordUnits").push().getKey();

        landlordUnitsRef =  FirebaseDatabase.getInstance().getReference().child("Users").child("LandlordUnits").child(uid);




        Map newPost = new HashMap();
        newPost.put("buildingName", buildingName);
        newPost.put("buildingLocation", buildingLocation);
        newPost.put("caretakerPhoneNumber", caretakerPhoneNumber);
        newPost.put("caretakerPhoneNumber", caretakerPhoneNumber);
        newPost.put("houseType", houseType);
        newPost.put("vacantUnits", vacantUnits);
        newPost.put("rentInKsh", rentInKsh);




        progressDialog.get().setMessage(getResources().getString(R.string.Registering_Please_Wait));
        progressDialog.get().show();


        landlordUnitsRef.setValue(newPost);

        progressDialog.get().dismiss();

        Toast.makeText(RegisterLandlordUnits.this,getResources().getString(R.string.landlordUnit_added),Toast.LENGTH_LONG).show();



    }
}
