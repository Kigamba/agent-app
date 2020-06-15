package com.stavros.demo.android.util

import android.content.Context
import android.content.Intent
import com.nerdstone.neatformcore.domain.builders.FormBuilder
import com.stavros.demo.android.form.FormData

/**
 * Created by Kigamba (nek.eam@gmail.com) on 15-June-2020
 */
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


fun startJsonFormActivity(context: Context, title: String, formFile: String, cls: Class<*>): Intent {
    var formData = FormData(title, FormType.jsonFromEmbeddedDefault, "json.form/$formFile")

    val intent = Intent(context, cls)
    intent.putExtra("formData", formData)

    return intent
}