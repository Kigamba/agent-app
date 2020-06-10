package com.stavros.demo.android.form;

import java.io.Serializable

/**
 * Created by Kigamba (nek.eam@gmail.com) on 09-June-2020
 */

data class FormData(
        var formTitle: String,
        var formCategory: String,
        val filePath: String
        ) : Serializable