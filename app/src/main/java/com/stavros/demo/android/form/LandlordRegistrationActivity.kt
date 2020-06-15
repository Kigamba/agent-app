package com.stavros.demo.android.form

import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.stavros.demo.android.form.NeatFormJsonActivity
import timber.log.Timber

/**
 * Created by Kigamba (nek.eam@gmail.com) on 15-June-2020
 */
class LandlordRegistrationActivity : NeatFormJsonActivity() {

    override fun saveData(formData: HashMap<String, Any?>) {
        val landlordsRef = FirebaseDatabase.getInstance().reference.child("Users").child("Landlords")//.child(id_number)

        /*progressDialog.get()?.setMessage(resources.getString(R.string.Registering_Please_Wait))
        progressDialog.get()?.show()*/

        val idNo = formData["id_no"] as String?
        if (idNo != null) {
            landlordsRef.child(idNo).setValue(formData, object: DatabaseReference.CompletionListener {
                override fun onComplete(databaseError: DatabaseError?, databaseReference: DatabaseReference) {
                    currentShowingToast?.cancel()
                    if (databaseError == null) {
                        showDatabaseSaveSuccessMessage()
                    } else {
                        Timber.e(Throwable(databaseError.message))
                        showDatabaseSaveErrorMessage()
                    }
                }
            })
        } else {
            showDatabaseSaveErrorMessage()
        }
    }
}