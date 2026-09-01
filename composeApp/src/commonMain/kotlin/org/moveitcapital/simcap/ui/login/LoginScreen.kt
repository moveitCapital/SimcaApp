package org.moveitcapital.simcap.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.moveitcapital.simcap.data.remote.request.LoginRequest
import org.moveitcapital.simcap.platform.showToast
import org.moveitcapital.simcap.ui.commons.AlertDialogError
import org.moveitcapital.simcap.ui.commons.BottomAnimatedContent
import org.moveitcapital.simcap.ui.home.HomeScreen
import org.moveitcapital.simcap.ui.login.LoginIntent.Init
import org.moveitcapital.simcap.ui.login.LoginIntent.OnCloseDialog
import org.moveitcapital.simcap.ui.login.LoginIntent.OnLogin
import org.moveitcapital.simcap.ui.login.LoginIntent.OnRecoverPass
import org.moveitcapital.simcap.ui.login.LoginIntent.OnSelectProvider
import org.moveitcapital.simcap.ui.login.components.LoginBody
import org.moveitcapital.simcap.ui.login.components.PasswordRecoverDialog
import org.moveitcapital.simcap.ui.login.components.ProvidersSelectionDialog
import simcap.composeapp.generated.resources.Res
import simcap.composeapp.generated.resources.ic_moveit_logo

class LoginScreen : Screen {
    
    @OptIn(InternalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: LoginViewModel = koinViewModel()
        val state = viewModel.container.stateFlow.collectAsStateWithLifecycle().value
        val intentHandler: (LoginIntent) -> Unit = { viewModel.onIntent(it) }
        var showRecoverPassword by remember { mutableStateOf(false) }
        
        LaunchedEffect(Unit) {
            intentHandler(Init)
            viewModel.container.sideEffectFlow.collect { effect ->
                when (effect) {
                    is LoginSideEffect.OnLogin -> navigator.replace(HomeScreen)
                    is LoginSideEffect.OnEmailSend -> {
                        showRecoverPassword = false
                        showToast(effect.message)
                    }
                    else -> Unit
                }
            }
        }
        
        AlertDialogError(state.error) { intentHandler(OnCloseDialog) }
        
        if (showRecoverPassword) {
            PasswordRecoverDialog(
                isLoading = state.isRecoverPassLoading,
                emailError = state.emailError,
                onCancel = { showRecoverPassword = false },
                onEmailToRecover = { intentHandler(OnRecoverPass(it)) }
            )
        }
        
        state.providersToSelect?.let { providers ->
            ProvidersSelectionDialog(providers) { intentHandler(OnSelectProvider(it)) }
        }
        
        Scaffold { padding ->
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                
                SplashContent(
                    modifier = Modifier.weight(1f),
                    isLoading = state.isLoading
                )
                
                BottomAnimatedContent(
                    modifier = Modifier.weight(2f),
                    visible = !state.isLoading,
                ) {
                    LoginBody(
                        modifier = Modifier.fillMaxSize(),
                        authError = state.authError,
                        onRecoverPassword = { showRecoverPassword = true },
                    ) { username,password ->
                        val request = LoginRequest(username, password)
                        intentHandler(OnLogin(request))
                    }
                }
            }
        }
    }
}

@Composable
fun SplashContent(
    modifier: Modifier,
    isLoading: Boolean = true,
) {
    Column(
        modifier = modifier.width(IntrinsicSize.Max),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_moveit_logo),
            contentDescription = null
        )

        if (isLoading) LinearProgressIndicator(Modifier.fillMaxWidth(0.9f))
    }
}