package com.stavros.demo.android.form;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stavros.demo.android.R;

import java.util.HashMap;
import java.util.Map;

public class LandlordRegistration extends AppCompatActivity {

    private EditText uFirstName,
            uLastName,
            uPhoneNumber,
            uID_number,
            uEmail;

    private RadioGroup radiog_Gender;
    private RadioButton uGender;
    private Button btn_save;
    private DatabaseReference landlordRef;
    private final ThreadLocal<ProgressDialog> progressDialog = new ThreadLocal<ProgressDialog>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_registration);

        uFirstName = (EditText) findViewById(R.id.firstName);
        uLastName = (EditText) findViewById(R.id.lasttName);
        uPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        uID_number = (EditText) findViewById(R.id.id_number);
        uEmail = (EditText) findViewById(R.id.email);
        radiog_Gender = (RadioGroup) findViewById(R.id.gender);
        btn_save = (Button) findViewById(R.id.btn_save);

        progressDialog.set(new ProgressDialog(this));
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLandlord();
            }
        });
    }

    private void saveLandlord() {
        final String firstName = uFirstName.getText().toString().trim();
        final String lastName = uLastName.getText().toString().trim();
        final String phone = uPhoneNumber.getText().toString().trim();

        int genderBtn = radiog_Gender.getCheckedRadioButtonId();

        String gender = "";
        uGender = (RadioButton) findViewById(genderBtn);

        final String id_number = uID_number.getText().toString().trim();
        final String email = uEmail.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(this, getResources().getString(R.string.Please_enter_first_name), Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(this, getResources().getString(R.string.Please_enter_last_name), Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, getResources().getString(R.string.Please_enter_phone_number), Toast.LENGTH_LONG).show();
            return;
        } else if (phone.length() != 10) {
            Toast.makeText(this, getResources().getString(R.string.Please_enter_phone_number), Toast.LENGTH_LONG).show();
            return;
        }

        if (uGender != null) {
            gender = uGender.getText().toString();
        } else {
            Toast.makeText(this, getResources().getString(R.string.Please_select_gender), Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(id_number)) {
            Toast.makeText(this, getResources().getString(R.string.Please_enter_id_number), Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, getResources().getString(R.string.Please_enter_email), Toast.LENGTH_LONG).show();
            return;
        }

        landlordRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Landlords").child(id_number);

        Map newPost = new HashMap();
        newPost.put("firstName", firstName);
        newPost.put("lastName", lastName);
        newPost.put("phone", phone);
        newPost.put("gender", gender);
        newPost.put("id_number", id_number);
        newPost.put("email", email);

        progressDialog.get().setMessage(getResources().getString(R.string.Registering_Please_Wait));
        progressDialog.get().show();
        landlordRef.setValue(newPost);
        progressDialog.get().dismiss();

        Toast.makeText(LandlordRegistration.this, getResources().getString(R.string.landlord_added), Toast.LENGTH_LONG).show();

        Intent intent = new Intent(LandlordRegistration.this, RegisterLandlordUnits.class);
        startActivity(intent);
    }
}
