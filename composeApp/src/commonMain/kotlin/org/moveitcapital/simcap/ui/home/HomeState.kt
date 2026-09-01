package org.moveitcapital.simcap.ui.home

import org.moveitcapital.simcap.core.AppError
import org.moveitcapital.simcap.data.remote.response.MenuResponse
import org.moveitcapital.simcap.domain.model.Auth
import org.moveitcapital.simcap.domain.model.MenuModule

data class HomeState(
    val isLoading: Boolean = true,
    val error: AppError? = null,
    val passChangedResponse: String? = null,
    val auth: Auth? = null,
    val updatePassError: String? = null,
    val menu: List<MenuModule>? = null,
    val menuSelected: MenuModule? = null
)