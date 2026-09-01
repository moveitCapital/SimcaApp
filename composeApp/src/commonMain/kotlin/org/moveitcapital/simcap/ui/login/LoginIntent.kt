package org.moveitcapital.simcap.ui.login

import org.moveitcapital.simcap.data.remote.request.LoginRequest
import org.moveitcapital.simcap.domain.model.Provider

sealed interface LoginIntent {
    object Init : LoginIntent
    object OnCloseDialog : LoginIntent
    data class OnRecoverPass(val email: String) : LoginIntent
    data class OnSelectProvider(val provider: Provider) : LoginIntent
    data class OnLogin(val request: LoginRequest) : LoginIntent
}