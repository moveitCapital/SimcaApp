package org.moveitcapital.simcap.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.moveitcapital.simcap.ui.analytics.AnalyticsViewModel
import org.moveitcapital.simcap.ui.account.AccountViewModel
import org.moveitcapital.simcap.ui.home.HomeViewModel
import org.moveitcapital.simcap.ui.login.LoginViewModel

val viewModelsModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::AccountViewModel)
    viewModelOf(::AnalyticsViewModel)
}