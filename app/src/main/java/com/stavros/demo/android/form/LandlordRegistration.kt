package com.stavros.demo.android.form

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.stavros.demo.android.R
import java.util.*

class LandlordRegistration : AppCompatActivity() {

    private var uFirstName: EditText? = null
    private var uLastName: EditText? = null
    private var uPhoneNumber: EditText? = null
    private var uID_number: EditText? = null
    private var uEmail: EditText? = null
    private var radiog_Gender: RadioGroup? = null
    private var uGender: RadioButton? = null
    private var btn_save: Button? = null
    private var landlordRef: DatabaseReference? = null
    private val progressDialog = ThreadLocal<ProgressDialog>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landlord_registration)
        uFirstName = findViewById(R.id.firstName)
        uLastName = findViewById(R.id.lasttName)
        uPhoneNumber = findViewById<View>(R.id.phoneNumber) as EditText
        uID_number = findViewById(R.id.id_number)
        uEmail = findViewById(R.id.email)
        radiog_Gender = findViewById(R.id.gender)
        btn_save = findViewById(R.id.btn_save)
        progressDialog.set(ProgressDialog(this))
        btn_save?.setOnClickListener { saveLandlord() }
    }

    private fun saveLandlord() {
        val firstName = uFirstName?.text.toString().trim { it <= ' ' }
        val lastName = uLastName?.text.toString().trim()
        val phone = uPhoneNumber?.text.toString().trim()
        val genderBtn = radiog_Gender?.checkedRadioButtonId
        var gender = ""
        uGender = findViewById(genderBtn!!)

        val id_number = uID_number?.text.toString().trim()
        val email = uEmail?.text.toString().trim()

        if (firstName.isEmpty()) {
            resources.getString(R.string.Please_enter_first_name).showLongToast()
            return
        }

        if (lastName.isEmpty()) {
            resources.getString(R.string.Please_enter_last_name).showLongToast()
            return
        }

        if (phone.isEmpty()) {
            resources.getString(R.string.Please_enter_phone_number).showLongToast()
            return
        } else if (phone.length != 10) {
            resources.getString(R.string.Please_enter_phone_number).showLongToast()
            return
        }

        gender = if (uGender != null) {
            uGender?.text.toString()
        } else {
            resources.getString(R.string.Please_select_gender).showLongToast()
            return
        }

        if (id_number.isEmpty()) {
            resources.getString(R.string.Please_enter_id_number).showLongToast()
            return
        }

        if (email.isEmpty()) {
            resources.getString(R.string.Please_enter_email).showLongToast()
            return
        }

        landlordRef = FirebaseDatabase.getInstance().reference.child("Users").child("Landlords").child(id_number)
        val newPost: MutableMap<String, Any?> = HashMap()
        newPost["firstName"] = firstName
        newPost["lastName"] = lastName
        newPost["phone"] = phone
        newPost["gender"] = gender
        newPost["id_number"] = id_number
        newPost["email"] = email

        progressDialog.get()?.setMessage(resources.getString(R.string.Registering_Please_Wait))
        progressDialog.get()?.show()

        landlordRef!!.setValue(newPost)
        progressDialog.get()?.dismiss()

        resources.getString(R.string.landlord_added).showLongToast()
        val intent = Intent(this@LandlordRegistration, RegisterLandlordUnits::class.java)
        startActivity(intent)
    }

    private fun String.showLongToast() {
        Toast.makeText(this@LandlordRegistration, this, Toast.LENGTH_LONG).show();
    }
}