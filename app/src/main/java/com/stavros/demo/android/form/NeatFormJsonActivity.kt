package com.stavros.demo.android.form

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nerdstone.neatformcore.domain.builders.FormBuilder
import com.nerdstone.neatformcore.form.common.FormErrorDialog
import com.nerdstone.neatformcore.form.json.JsonFormBuilder
import com.nerdstone.neatformcore.form.json.JsonFormEmbedded
import com.stavros.demo.android.R
import timber.log.Timber


/**
 * Created by Kigamba (nek.eam@gmail.com) on 09-June-2020
 */
class NeatFormJsonActivity : AppCompatActivity() {

    private lateinit var formLayout: LinearLayout
    private lateinit var mainLayout: LinearLayout
    private lateinit var sampleToolBar: Toolbar
    private lateinit var pageTitleTextView: TextView
    private lateinit var exitFormImageView: ImageView
    private lateinit var completeButton: ImageView
    private var formBuilder: FormBuilder? = null

    private var progressDialog: AlertDialog? = null
    private var currentShowingToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_activity)

        mainLayout = findViewById(R.id.mainLayout)
        formLayout = findViewById(R.id.formLayout)
        sampleToolBar = findViewById(R.id.sampleToolBar)
        pageTitleTextView = findViewById(R.id.pageTitleTextView)
        exitFormImageView = findViewById(R.id.exitFormImageView)
        completeButton = findViewById(R.id.completeButton)


        if (intent.extras != null) {
            val formData = intent?.extras?.getSerializable("formData") as FormData
            pageTitleTextView.text = formData.formTitle
            exitFormImageView.setOnClickListener {
                if (it.id == R.id.exitFormImageView) {
                    finish()
                }
            }

            completeButton.setOnClickListener {
                if (it.id == R.id.completeButton) {

                    if (formBuilder?.formValidator?.invalidFields?.isEmpty()!! && formBuilder?.formValidator?.requiredFields?.isEmpty()!!) {
                        val regData = formBuilder?.getFormDataAsKeyValues()

                        // Save the data
                        if (regData != null) {
                            saveData(regData)
                        }

                        showToast("Completed entire step")
                    } else {
                        FormErrorDialog(this).show()
                    }
                }
            }

            /*val views = listOf<View>(
                    layoutInflater.inflate(R.layout.sample_one_form_custom_layout, null)
            )*/
            when (formData.formCategory) {
                FormType.jsonFromEmbeddedDefault -> {
                    formBuilder = JsonFormBuilder(this, formData.filePath)
                    formBuilder?.also {
                        //it.registeredViews["custom_image"] = CustomImageView::class
                        /*it.withFormData(
                                Constants.PREVIOUS_DATA, mutableSetOf(
                                "dob", "time", "email_subscription", "country",
                                "no_prev_pregnancies", "choose_language", "wiki_contribution"
                        )
                        )*/
                        JsonFormEmbedded(formBuilder as JsonFormBuilder, formLayout).buildForm()
                    }
                }
                /*FormType.jsonFormEmbeddedCustomized -> {
                    formBuilder = JsonFormBuilder(this, formData.filePath).also {
                        it.registeredViews["custom_image"] = CustomImageView::class
                    }
                    JsonFormEmbedded(formBuilder as JsonFormBuilder, formLayout).buildForm(views)
                }
                FormType.jsonFormStepperDefault -> {
                    startStepperActivity(formData.filePath, true)
                }
                FormType.jsonFormStepperCustomized -> {
                    startStepperActivity(formData.filePath)
                }*/
                else -> Toast.makeText(
                        this, "Please provide the right form type",
                        Toast.LENGTH_LONG
                ).show()
            }
        }
    }

   /* private fun startStepperActivity(filePath: String, preFilled: Boolean = false) {
        finish()
        startActivity(Intent(this, StepperActivity::class.java).apply {
            putExtra(FILE_PATH, filePath)
            putExtra(PRE_FILLED, preFilled)
        })
    }*/

    private fun saveData(landlordRegData: HashMap<String, Any?>) {
        val landlordsRef = FirebaseDatabase.getInstance().reference.child("Users").child("Landlords")//.child(id_number)

        /*progressDialog.get()?.setMessage(resources.getString(R.string.Registering_Please_Wait))
        progressDialog.get()?.show()*/

        val idNo = landlordRegData["id_no"] as String?
        if (idNo != null) {
            landlordsRef.child(idNo).setValue(landlordRegData, object: DatabaseReference.CompletionListener {
                override fun onComplete(databaseError: DatabaseError?, databaseReference: DatabaseReference) {
                    if (databaseError == null) {
                        showToast()
                    } else {

                    }
                }
            })
        }
    }

    private fun showToast(toastText: String) {
        currentShowingToast?.cancel()
        currentShowingToast = Toast.makeText(this, toastText, Toast.LENGTH_LONG)
        currentShowingToast?.show()
    }
}


object FormType {
    const val jsonFromEmbeddedDefault = "JsonFormEmbedded - default"
}

fun FormBuilder.getFormDataAsKeyValues (): HashMap<String, Any?> {
    val formData = this.getFormData();
    var keyValues : HashMap<String, Any?> = HashMap()

    formData.keys.forEach {
        keyValues[it] = formData.get(it)?.value
    }

    return keyValues
}