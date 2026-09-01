package org.moveitcapital.simcap.di

import com.russhwolf.settings.Settings
import org.koin.dsl.module
import org.moveitcapital.simcap.data.local.cache.SessionCache
import org.moveitcapital.simcap.data.local.database.AppDatabase
import org.moveitcapital.simcap.data.local.preferences.PreferencesDataStore
import org.moveitcapital.simcap.data.remote.service.SessionService

val dataModule = module {
    single<Settings> { Settings() }
    single { PreferencesDataStore(get()) }
    single { SessionCache() }
    single { SessionService(get()) }
    single { get<AppDatabase>().authDao() }
}