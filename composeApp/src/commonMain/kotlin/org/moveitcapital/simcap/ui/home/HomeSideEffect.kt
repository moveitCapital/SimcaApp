package org.moveitcapital.simcap.ui.home

import org.moveitcapital.simcap.domain.model.MenuModule

sealed interface HomeSideEffect{
    object CloseDrawer: HomeSideEffect
    data class OpenModuleAnalytics(val module: MenuModule): HomeSideEffect
    data class ShowToast(val text: String): HomeSideEffect
}