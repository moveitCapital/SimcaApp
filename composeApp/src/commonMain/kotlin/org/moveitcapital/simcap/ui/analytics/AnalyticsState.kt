package org.moveitcapital.simcap.ui.analytics

data class AnalyticsState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val analyticsData: String? = null
)