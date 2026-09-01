package org.moveitcapital.simcap.ui.analytics

import org.moveitcapital.simcap.core.mvi.BaseViewModel
import org.moveitcapital.simcap.domain.usecase.LogoutUseCase
import org.orbitmvi.orbit.viewmodel.container

class AnalyticsViewModel(
    logoutUseCase: LogoutUseCase
) : BaseViewModel<AnalyticsState, AnalyticsEffect, AnalyticsIntent>(
    logoutUseCase = logoutUseCase
) {

    override fun initState() = AnalyticsState()

    override fun onIntent(intent: AnalyticsIntent) {
        when (intent) {
            is AnalyticsIntent.Init -> openLoading()
            is AnalyticsIntent.OnRender -> closeLoading()
            else -> Unit
        }
    }
}