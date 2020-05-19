package com.stavros.demo.android.form

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.stavros.demo.android.R
import java.util.*

class TenantRegistration : AppCompatActivity() {

    private var uFirstName: EditText? = null
    private var uLastName: EditText? = null
    private var uPhoneNumber: EditText? = null
    private var uID_number: EditText? = null
    private var uEmail: EditText? = null
    private var uPreferredHouseLocation: EditText? = null
    private var houseTypesSpinner: Spinner? = null
    private var radiog_Gender: RadioGroup? = null
    private var uGender: RadioButton? = null
    private var btn_save: Button? = null
    private var tenantRef: DatabaseReference? = null
    private val progressDialog = ThreadLocal<ProgressDialog>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tenant_registration)

        uFirstName = findViewById(R.id.firstName)
        uLastName = findViewById(R.id.lasttName)
        uPhoneNumber = findViewById(R.id.phoneNumber)
        uID_number = findViewById(R.id.id_number)
        uEmail = findViewById(R.id.email)
        uPreferredHouseLocation = findViewById(R.id.preferredHouseLocation)
        radiog_Gender = findViewById(R.id.gender)
        houseTypesSpinner = findViewById(R.id.houseTypesSpinner)
        btn_save = findViewById(R.id.btn_save)

        progressDialog.set(ProgressDialog(this))
        btn_save?.setOnClickListener { saveTenant() }
    }

    private fun saveTenant() {
        val firstName = uFirstName?.text.toString().trim()
        val lastName = uLastName?.text.toString().trim()
        val phone = uPhoneNumber?.text.toString().trim()
        val genderBtn = radiog_Gender?.checkedRadioButtonId
        var gender = ""

        uGender = findViewById(genderBtn!!)

        val id_number = uID_number?.text.toString().trim()
        val email = uEmail?.text.toString().trim()

        val houseType = houseTypesSpinner!!.selectedItem.toString()
        val preferredHouseLocation = uPreferredHouseLocation!!.text.toString().trim()

        if (firstName.isEmpty()) {
            Toast.makeText(this, resources.getString(R.string.Please_enter_first_name), Toast.LENGTH_LONG).show()
            return
        }

        if (lastName.isEmpty()) {
            Toast.makeText(this, resources.getString(R.string.Please_enter_last_name), Toast.LENGTH_LONG).show()
            return
        }

        if (phone.isEmpty()) {
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

        if (id_number.isEmpty()) {
            Toast.makeText(this, resources.getString(R.string.Please_enter_id_number), Toast.LENGTH_LONG).show()
            return
        }

        if (email.isEmpty()) {
            Toast.makeText(this, resources.getString(R.string.Please_enter_email), Toast.LENGTH_LONG).show()
            return
        }

        if (preferredHouseLocation.isEmpty()) {
            Toast.makeText(this, resources.getString(R.string.Please_enter_preferredHouseLocation), Toast.LENGTH_LONG).show()
            return
        }

        tenantRef = FirebaseDatabase.getInstance().reference.child("Users").child("Tenants").child(id_number)
        val newPost: MutableMap<String, Any?> = HashMap()
        newPost["firstName"] = firstName
        newPost["lastName"] = lastName
        newPost["phone"] = phone
        newPost["gender"] = gender
        newPost["id_number"] = id_number
        newPost["email"] = email
        newPost["houseTypes"] = houseType
        newPost["preferredHouseLocation"] = preferredHouseLocation

        progressDialog.get()?.setMessage(resources.getString(R.string.Registering_Please_Wait))
        progressDialog.get()?.show()

        tenantRef?.setValue(newPost)

        progressDialog.get()?.dismiss()
        Toast.makeText(this@TenantRegistration, resources.getString(R.string.tenant_added), Toast.LENGTH_LONG).show()
    }
}