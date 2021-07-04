package com.myniprojects.compass.ui.main

import android.app.Application
import com.myniprojects.compass.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BaseApp : Application()
{
    override fun onCreate()
    {
        super.onCreate()
        if (BuildConfig.DEBUG)
        {
            Timber.plant(Timber.DebugTree())
        }
    }
}