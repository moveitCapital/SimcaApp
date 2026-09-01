package org.moveitcapital.simcap.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.moveitcapital.simcap.ui.login.LoginScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(LoginScreen()){
            SlideTransition(navigator = it)
        }
    }
}