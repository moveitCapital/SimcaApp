package org.moveitcapital.simcap

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.moveitcapital.simcap.di.initKoin

class AndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AndroidApp)
        }
    }
}