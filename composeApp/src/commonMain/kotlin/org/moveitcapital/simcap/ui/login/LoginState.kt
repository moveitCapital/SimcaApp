package org.moveitcapital.simcap.ui.login

import org.moveitcapital.simcap.core.AppError
import org.moveitcapital.simcap.domain.model.Auth
import org.moveitcapital.simcap.domain.model.Provider

data class LoginState(
    val isLoading: Boolean = true,
    val isRecoverPassLoading: Boolean = false,
    val authError: String? = null,
    val auth: Auth? = null,
    val error: AppError? = null,
    val emailError: String? = null,
    val providersToSelect: List<Provider>? = null
)