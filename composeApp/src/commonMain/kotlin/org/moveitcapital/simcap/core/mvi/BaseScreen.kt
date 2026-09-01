package org.moveitcapital.simcap.core.mvi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.CoroutineScope
import org.moveitcapital.simcap.core.AppError
import org.moveitcapital.simcap.ui.commons.AlertDialogError
import org.moveitcapital.simcap.ui.login.LoginScreen
import org.moveitcapital.simcap.ui.login.SplashContent

/**
 * BaseScreen es una clase abstracta que implementa la interfaz Screen de Voyager y maneja la lógica común para las pantallas
 * que siguen el patrón MVI (Model-View-Intent). Esta clase utiliza un ViewModel genérico que maneja el estado (S), los efectos secundarios (E)
 * y los intents (I) de la pantalla.
 *
 * @param S El tipo de estado que maneja el ViewModel.
 * @param E El tipo de efecto secundario que maneja el ViewModel.
 * @param I El tipo de intent que maneja el ViewModel.
 */
abstract class BaseScreen<S : Any, E : Any, I : Any>() : Screen, OnLogoutListener {

    protected lateinit var navigator: Navigator
    protected lateinit var scope: CoroutineScope
    lateinit var viewModel: BaseViewModel<S, E, I>
        private set

    /**
     * Méthod abstracto que debe ser implementado por las subclases para proporcionar el ViewModel específico de la pantalla.
     *
     * @return El ViewModel asociado a la pantalla.
     */
    @Composable
    abstract fun getViewModel(): BaseViewModel<S, E, I>

    /**
     * Composable que define el contenido de la pantalla. Maneja la configuración del ViewModel, la recolección del estado,
     * la gestión de errores y la visualización de un indicador de carga.
     */
    @Composable
    override fun Content() {
        navigator = LocalNavigator.currentOrThrow
        scope = rememberCoroutineScope()
        val viewModel = getViewModel()
        viewModel.let {
            this.viewModel = it
            val state = it.container.stateFlow.collectAsStateWithLifecycle().value
            val isLoading = it.isLoading.collectAsStateWithLifecycle().value
            val error = it.error.collectAsStateWithLifecycle().value
            val intentHandler: (I) -> Unit = { intent -> it.onIntent(intent) }

            Content(state, intentHandler)

            AlertDialogError(error = error) {
                if (error is AppError.Session) viewModel.onLogout()
                else viewModel.closeError()
            }

            if (isLoading) Dialog({}) {
                SplashContent(
                    modifier = Modifier.background(
                        color = Color.White.copy(alpha = 0.5f),
                        shape = MaterialTheme.shapes.large
                    ).padding(bottom = 12.dp)
                )
            }

            LaunchedEffect(Unit) {
                it.logoutListener = this@BaseScreen
                it.container.sideEffectFlow.collect { sideEffect ->
                    effectHandler(sideEffect)
                }
            }
        }
    }

    /**
     * Maneja la acción de cierre de sesión, reemplazando la pantalla actual con la pantalla de inicio de sesión.
     */
    override fun onLogout() {
        navigator.replaceAll(LoginScreen())
    }

    /**
     * Composable abstracto que debe ser implementado por las subclases para definir el contenido específico de la pantalla.
     *
     * @param state El estado actual de la pantalla.
     * @param intentHandler Una función que maneja los intents generados por la interfaz de usuario.
     */
    @Composable
    abstract fun Content(state: S, intentHandler: (I) -> Unit)

    /**
     * Méthod abstracto que debe ser implementado por las subclases para manejar los efectos secundarios emitidos por el ViewModel.
     *
     * @param sideEffect El efecto secundario a manejar.
     */
    abstract fun effectHandler(sideEffect: E)
}

interface OnLogoutListener {
    fun onLogout()
}