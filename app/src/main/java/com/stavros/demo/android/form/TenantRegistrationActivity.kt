package com.stavros.demo.android.form

import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import timber.log.Timber

/**
 * Created by Kigamba (nek.eam@gmail.com) on 15-June-2020
 */
class TenantRegistrationActivity : NeatFormJsonActivity() {
    override fun saveData(formData: HashMap<String, Any?>) {
        val tenantsRef = FirebaseDatabase.getInstance().reference.child("Users").child("Tenants")//.child(id_number)

        val idNo = formData["id_no"] as String?
        if (idNo != null) {
            tenantsRef.child(idNo).setValue(formData, object: DatabaseReference.CompletionListener {
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