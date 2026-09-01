package org.moveitcapital.simcap.ui.analytics

sealed interface AnalyticsEffect {
    data class ShowError(val error: Throwable): AnalyticsEffect
}