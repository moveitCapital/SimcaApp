package org.moveitcapital.simcap.core.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.moveitcapital.simcap.core.AppError
import org.moveitcapital.simcap.core.ResultHandler
import org.moveitcapital.simcap.core.toAppError
import org.moveitcapital.simcap.domain.usecase.LogoutUseCase
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.Syntax
import org.orbitmvi.orbit.viewmodel.container

/**
 * Clase base para ViewModels que implementan el patrón MVI (Model-View-Intent).
 * Proporciona manejo de estados de carga, errores y cierre de sesión.
 *
 * @param S Tipo de estado
 * @param E Tipo de efectos secundarios
 * @param I Tipo de intenciones
 * @property logoutUseCase Caso de uso para cerrar sesión
 */
abstract class BaseViewModel<S : Any, E : Any, I : Any>(
    private val logoutUseCase: LogoutUseCase
) : ViewModel(), ContainerHost<S, E> {

    override val container = container<S, E>(initState())

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<AppError?>(null)
    val error = _error.asStateFlow()

    lateinit var logoutListener: OnLogoutListener

    protected fun openError(error: AppError) {
        _error.tryEmit(error)
    }

    fun closeError() {
        _error.tryEmit(null)
    }

    protected fun openLoading() {
        _isLoading.tryEmit(true)
    }

    protected fun closeLoading() {
        _isLoading.tryEmit(false)
    }

    /**
     * Interfaz para manejar el evento de cierre de sesión.
     */
    fun onLogout() = intent {
        runCatching {
            logoutUseCase()
            logoutListener.onLogout()
        }.onFailure {
            openError(it.toAppError())
        }
    }

    /**
     * Interfaz para manejar los eventos.
     */
    abstract fun onIntent(intent: I)

    abstract fun initState(): S

    /**
     * Interfaz para manejar los resultados de consultas al API.
     */
    open suspend fun <T> Syntax<S, E>.handleResult(
        result: ResultHandler<T>,
        onError: suspend (AppError) -> Unit = { _error.emit(it) },
        onSuccess: suspend Syntax<S, E>.(data: T) -> Unit,
    ) {
        runCatching {
            _isLoading.emit(true)
            when (result) {
                is ResultHandler.Success -> onSuccess(result.data)
                is ResultHandler.Failure -> onError(result.error)
            }
            _isLoading.emit(false)
        }.onFailure {
            onError(it.toAppError())
        }
    }
}