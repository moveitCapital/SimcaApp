package org.moveitcapital.simcap.ui.home

import org.moveitcapital.simcap.core.mvi.BaseViewModel
import org.moveitcapital.simcap.domain.model.MenuModule
import org.moveitcapital.simcap.domain.usecase.GetAuthenticationUseCase
import org.moveitcapital.simcap.domain.usecase.GetMenuUseCase
import org.moveitcapital.simcap.domain.usecase.LogoutUseCase
import org.moveitcapital.simcap.domain.usecase.UpdatePasswordUseCase
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class HomeViewModel(
    private val getAuthenticationUseCase: GetAuthenticationUseCase,
    private val getMenuUseCase: GetMenuUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    logoutUseCase: LogoutUseCase
) : BaseViewModel<HomeState, HomeSideEffect, HomeIntent>(
    logoutUseCase = logoutUseCase
) {

    init { openLoading() }

    override fun initState() = HomeState()

    override fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.Init -> loadAuthentication()
            is HomeIntent.OnLogout -> onLogout()
            is HomeIntent.OnSelectMenuItem -> onSelectMenuItem(intent.menuItem)
            is HomeIntent.OnSelectModule -> onSelectModule(intent.module)
            is HomeIntent.OnUpdatePassword -> updatePassword(intent)
            is HomeIntent.OnCloseError -> closeError()
            else -> Unit
        }
    }

    private fun loadAuthentication() = intent {
        getAuthenticationUseCase()?.let { auth ->
            reduce { state.copy(auth = auth) }
            val userId = auth.user.userId
            val companyId = auth.userDTO.firstOrNull()?.id
            companyId?.let { loadMenu(userId, it) }
        } ?: run { onLogout() }
    }

    private fun loadMenu(userId: Int, companyId: Int) = intent {
        handleResult(getMenuUseCase(userId, companyId)) {
            reduce { state.copy(menu = it) }
        }
    }

    private fun onSelectMenuItem(menuItem: MenuModule?) = intent {
        reduce { state.copy(menuSelected = menuItem) }
        postSideEffect(HomeSideEffect.CloseDrawer)
    }

    private fun onSelectModule(module: MenuModule) = intent {
        when (state.menuSelected?.id) {
            21 -> postSideEffect(HomeSideEffect.OpenModuleAnalytics(module))
            else -> Unit
        }
    }

    private fun updatePassword(intent: HomeIntent.OnUpdatePassword) = intent {
        if (intent.newPassword != intent.confirmNewPassword) {
            reduce { state.copy(updatePassError = "Las contraseñas no coinciden") }
        } else {
            handleResult(
                result = updatePasswordUseCase(
                    oldPassword = intent.currentPassword,
                    newPassword = intent.newPassword
                )
            ) { reduce { state.copy(passChangedResponse = it) } }
        }
    }
}