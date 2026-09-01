package org.moveitcapital.simcap.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import org.moveitcapital.simcap.core.AppError
import org.moveitcapital.simcap.core.Exeptions
import org.moveitcapital.simcap.core.ResultHandler
import org.moveitcapital.simcap.core.util.EmailValidator
import org.moveitcapital.simcap.data.remote.request.LoginRequest
import org.moveitcapital.simcap.domain.model.Provider
import org.moveitcapital.simcap.domain.usecase.RecoverPasswordUseCase
import org.moveitcapital.simcap.domain.usecase.LoadAndGetSessionUseCase
import org.moveitcapital.simcap.domain.usecase.LoginUseCase
import org.moveitcapital.simcap.domain.usecase.SaveSessionUseCase
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class LoginViewModel(
    private val loadAndGetSessionUseCase: LoadAndGetSessionUseCase,
    private val loginUseCase: LoginUseCase,
    private val recoverPasswordUseCase: RecoverPasswordUseCase,
    private val saveSessionUseCase: SaveSessionUseCase,
) : ViewModel(), ContainerHost<LoginState, LoginSideEffect> {

    override val container = container<LoginState, LoginSideEffect>(LoginState())

    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.Init -> loadAuthentication()
            is LoginIntent.OnCloseDialog -> closeDialog()
            is LoginIntent.OnLogin -> login(intent.request)
            is LoginIntent.OnRecoverPass -> recoverPassword(intent.email)
            is LoginIntent.OnSelectProvider -> selectProvider(intent.provider)
            else -> Unit
        }
    }

    private fun closeDialog() = intent {
        reduce { state.copy(error = null) }
    }

    private fun login(request: LoginRequest) = intent {
        reduce { state.copy(isLoading = true) }
        loginUseCase(request).let {
            when (it) {
                is ResultHandler.Success -> {
                    reduce { state.copy(auth = it.data) }
                    choseProvider(it.data.providers)
                }
                is ResultHandler.Failure -> reduce {
                    if (it.error is AppError.Http && it.error.response.code == 207) {
                        state.copy(authError = it.error.response.message)
                    } else state.copy(error = it.error)
                }
            }
        }
        reduce { state.copy(isLoading = false) }
    }

    private fun choseProvider(providers: List<Provider>?) = intent {
        //when (providers?.size ?: 0) {
        //0 -> selectProvider(null)
        //1 -> selectProvider(providers?.first()) TODO: Select provider
        //else -> reduce { state.copy(providersToSelect = providers) }
        //}
        selectProvider(null)
    }

    private fun selectProvider(provider: Provider?) = intent {
        state.auth?.let {
            saveSessionUseCase(it, provider)
            postSideEffect(LoginSideEffect.OnLogin(provider))
        } ?: run {
            reduce { state.copy(error = AppError.Custom(Exeptions.NO_TOKEN)) }
        }
    }

    private fun recoverPassword(email: String) = intent {
        if (EmailValidator.isValidEmail(email)) {
            reduce { state.copy(isRecoverPassLoading = true, emailError = null) }
            recoverPasswordUseCase(email).let {
                if (it is ResultHandler.Success) {
                    postSideEffect(LoginSideEffect.OnEmailSend(it.data))
                } else if (it is ResultHandler.Failure) {
                    if (it.error is AppError.Http && it.error.response.code == 404) {
                        reduce { state.copy(emailError = it.error.response.message) }
                    } else reduce { state.copy(error = it.error) }
                }
                reduce { state.copy(isRecoverPassLoading = false) }
            }
        } else {
            reduce { state.copy(emailError = "Direccion de mail no válido") }
        }
    }

    private fun loadAuthentication() = intent {
        reduce { state.copy(isLoading = true) }
        delay(2000)
        loadAndGetSessionUseCase().let { response ->
            val authentication = response.first
            authentication?.let { auth ->
                auth.providers.find { it.companyId == response.second }.let {
                    postSideEffect(LoginSideEffect.OnLogin(it))
                }
            }
        }
        reduce { state.copy(isLoading = false) }
    }
}