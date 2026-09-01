package org.moveitcapital.simcap

import androidx.compose.ui.window.ComposeUIViewController
import org.moveitcapital.simcap.di.initKoin
import org.moveitcapital.simcap.ui.App

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }