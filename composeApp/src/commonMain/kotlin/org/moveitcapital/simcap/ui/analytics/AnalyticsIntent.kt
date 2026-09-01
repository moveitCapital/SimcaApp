package org.moveitcapital.simcap.ui.analytics

sealed interface AnalyticsIntent {
    object Init: AnalyticsIntent
    object OnRender: AnalyticsIntent
}