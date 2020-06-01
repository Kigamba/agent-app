package com.stavros.demo.android

import android.app.Application
import timber.log.Timber

/**
 * Created by Kigamba (nek.eam@gmail.com) on 19-May-2020
 */
class AgentApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}