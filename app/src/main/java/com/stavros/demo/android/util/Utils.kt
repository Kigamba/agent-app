package com.stavros.demo.android.util

import com.nerdstone.neatformcore.domain.builders.FormBuilder

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