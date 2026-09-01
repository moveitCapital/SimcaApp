package org.moveitcapital.simcap.ui.login

import org.moveitcapital.simcap.domain.model.Provider

sealed interface LoginSideEffect {
    data class OnLogin(val provider: Provider?) : LoginSideEffect
    data class OnEmailSend(val message: String) : LoginSideEffect
}