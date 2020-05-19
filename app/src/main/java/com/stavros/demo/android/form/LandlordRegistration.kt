package com.stavros.demo.android.form

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
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
        uFirstName = findViewById<View>(R.id.firstName) as EditText
        uLastName = findViewById<View>(R.id.lasttName) as EditText
        uPhoneNumber = findViewById<View>(R.id.phoneNumber) as EditText
        uID_number = findViewById<View>(R.id.id_number) as EditText
        uEmail = findViewById<View>(R.id.email) as EditText
        radiog_Gender = findViewById<View>(R.id.gender) as RadioGroup
        btn_save = findViewById<View>(R.id.btn_save) as Button
        progressDialog.set(ProgressDialog(this))
        btn_save!!.setOnClickListener { saveLandlord() }
    }

    private fun saveLandlord() {
        val firstName = uFirstName!!.text.toString().trim { it <= ' ' }
        val lastName = uLastName!!.text.toString().trim { it <= ' ' }
        val phone = uPhoneNumber!!.text.toString().trim { it <= ' ' }
        val genderBtn = radiog_Gender!!.checkedRadioButtonId
        var gender = ""
        uGender = findViewById<View>(genderBtn) as RadioButton

        val id_number = uID_number!!.text.toString().trim { it <= ' ' }
        val email = uEmail!!.text.toString().trim { it <= ' ' }

        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(this, resources.getString(R.string.Please_enter_first_name), Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(this, resources.getString(R.string.Please_enter_last_name), Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, resources.getString(R.string.Please_enter_phone_number), Toast.LENGTH_LONG).show()
            return
        } else if (phone.length != 10) {
            Toast.makeText(this, resources.getString(R.string.Please_enter_phone_number), Toast.LENGTH_LONG).show()
            return
        }

        gender = if (uGender != null) {
            uGender!!.text.toString()
        } else {
            Toast.makeText(this, resources.getString(R.string.Please_select_gender), Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(id_number)) {
            Toast.makeText(this, resources.getString(R.string.Please_enter_id_number), Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, resources.getString(R.string.Please_enter_email), Toast.LENGTH_LONG).show()
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

        Toast.makeText(this@LandlordRegistration, resources.getString(R.string.landlord_added), Toast.LENGTH_LONG).show()
        val intent = Intent(this@LandlordRegistration, RegisterLandlordUnits::class.java)
        startActivity(intent)
    }
}