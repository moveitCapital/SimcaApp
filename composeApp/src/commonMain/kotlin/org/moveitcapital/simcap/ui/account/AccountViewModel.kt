package org.moveitcapital.simcap.ui.account

import org.moveitcapital.simcap.core.mvi.BaseViewModel
import org.moveitcapital.simcap.domain.usecase.GetAuthenticationUseCase
import org.moveitcapital.simcap.domain.usecase.GetMenuUseCase
import org.moveitcapital.simcap.domain.usecase.GetProfileUseCase
import org.moveitcapital.simcap.domain.usecase.LogoutUseCase
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container

class AccountViewModel(
    private val getAuthenticationUseCase: GetAuthenticationUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    logoutUseCase: LogoutUseCase
) : BaseViewModel<AccountState, AccountSideEffect, AccountIntent>(
    logoutUseCase = logoutUseCase
) {

    init { openLoading() }

    override fun initState() = AccountState()

    override fun onIntent(intent: AccountIntent) {
        when (intent) {
            is AccountIntent.Init -> initData()
            else -> Unit
        }
    }

    private fun initData() = intent {
        openLoading()
        getAuthenticationUseCase()?.let { auth ->
            auth.userDTO.firstOrNull()?.let {
                getProfile(auth.user.profileId, it.id)
            }
        }
        closeLoading()
    }

    fun getProfile(
        userId: Int,
        companyId: Int
    ) = intent{
        handleResult(getProfileUseCase(userId, companyId)) {
            reduce { state.copy(profile = it) }
        }
    }
}