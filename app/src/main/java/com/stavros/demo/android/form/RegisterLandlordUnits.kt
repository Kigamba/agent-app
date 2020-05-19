package com.stavros.demo.android.form

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.stavros.demo.android.R
import java.util.*

class RegisterLandlordUnits : AppCompatActivity() {

    private var uBuildingName: EditText? = null
    private var uBuildingLocation: EditText? = null
    private var uCaretakerPhoneNumber: EditText? = null
    private var uTotalUnits: EditText? = null
    private var uVacantUnits: EditText? = null
    private var uRentInKsh: EditText? = null
    private var uAddUnitType: EditText? = null
    var houseTypesSpinner: Spinner? = null
    private var btn_save: Button? = null
    var landlordUnitsRef: DatabaseReference? = null

    private val progressDialog = ThreadLocal<ProgressDialog>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_landlord_units)

        uBuildingName = findViewById<View>(R.id.buildingName) as EditText
        uBuildingLocation = findViewById<View>(R.id.buildingLocation) as EditText
        uCaretakerPhoneNumber = findViewById<View>(R.id.caretakerPhoneNumber) as EditText
        uTotalUnits = findViewById<View>(R.id.totalUnits) as EditText
        uVacantUnits = findViewById<View>(R.id.vacantUnits) as EditText
        uRentInKsh = findViewById<View>(R.id.rentInKsh) as EditText
        uAddUnitType = findViewById<View>(R.id.addUnitType) as EditText
        houseTypesSpinner = findViewById<View>(R.id.houseTypesSpinner) as Spinner
        btn_save = findViewById<View>(R.id.btn_save) as Button
        progressDialog.set(ProgressDialog(this))
        uAddUnitType?.setOnClickListener { }
        btn_save?.setOnClickListener { saveLandlordUnit() }
    }

    private fun saveLandlordUnit() {
        val buildingName = uBuildingName?.text.toString().trim { it <= ' ' }
        val buildingLocation = uBuildingLocation?.text.toString().trim { it <= ' ' }
        val caretakerPhoneNumber = uCaretakerPhoneNumber?.text.toString().trim { it <= ' ' }
        val totalUnits = uTotalUnits?.text.toString()
        val vacantUnits = uVacantUnits?.text.toString().trim { it <= ' ' }
        val rentInKsh = uRentInKsh?.text.toString().trim { it <= ' ' }
        val houseType = houseTypesSpinner?.selectedItem.toString()

        if (TextUtils.isEmpty(buildingName)) {
            Toast.makeText(this, resources.getString(R.string.Please_enter_buildingName), Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(buildingLocation)) {
            Toast.makeText(this, resources.getString(R.string.Please_enter_buildingLocation), Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(caretakerPhoneNumber)) {
            Toast.makeText(this, resources.getString(R.string.Please_enter_caretakerPhoneNumber), Toast.LENGTH_LONG).show()
            return
        } else if (caretakerPhoneNumber.length != 10) {
            Toast.makeText(this, resources.getString(R.string.Please_enter_caretakerPhoneNumber), Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(totalUnits)) {
            Toast.makeText(this, resources.getString(R.string.Please_enter_totalUnits), Toast.LENGTH_LONG).show()
        }

        if (TextUtils.isEmpty(vacantUnits)) {
            Toast.makeText(this, resources.getString(R.string.Please_enter_vacantUnits), Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(rentInKsh)) {
            Toast.makeText(this, resources.getString(R.string.Please_enter_rentInKsh), Toast.LENGTH_LONG).show()
            return
        }

        val uid = FirebaseDatabase.getInstance().reference.child("Users").child("LandlordUnits").push().key
        landlordUnitsRef = FirebaseDatabase.getInstance().reference.child("Users").child("LandlordUnits").child(uid!!)
        val newPost: MutableMap<String, Any?> = HashMap()
        newPost["buildingName"] = buildingName
        newPost["buildingLocation"] = buildingLocation
        newPost["caretakerPhoneNumber"] = caretakerPhoneNumber
        newPost["caretakerPhoneNumber"] = caretakerPhoneNumber
        newPost["houseType"] = houseType
        newPost["vacantUnits"] = vacantUnits
        newPost["rentInKsh"] = rentInKsh

        progressDialog.get()?.setMessage(resources.getString(R.string.Registering_Please_Wait))
        progressDialog.get()?.show()

        landlordUnitsRef?.setValue(newPost)

        progressDialog.get()?.dismiss()
        Toast.makeText(this@RegisterLandlordUnits, resources.getString(R.string.landlordUnit_added), Toast.LENGTH_LONG).show()
    }
}