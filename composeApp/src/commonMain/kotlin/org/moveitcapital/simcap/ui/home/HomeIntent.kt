package org.moveitcapital.simcap.ui.home

import org.moveitcapital.simcap.domain.model.MenuModule

sealed interface HomeIntent {
    object Init : HomeIntent
    object OnLogout : HomeIntent
    object OnCloseError: HomeIntent
    data class OnUpdatePassword(
        val currentPassword: String,
        val newPassword: String,
        val confirmNewPassword: String
    ) : HomeIntent
    data class OnSelectMenuItem(val menuItem: MenuModule?) : HomeIntent
    data class OnSelectModule(val module: MenuModule) : HomeIntent
}