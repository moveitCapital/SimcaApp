package org.moveitcapital.simcap.ui.analytics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Lucide
import org.koin.compose.viewmodel.koinViewModel
import org.moveitcapital.simcap.domain.model.MenuModule
import org.moveitcapital.simcap.ui.commons.PowerBIReport
import org.moveitcapital.simcap.core.mvi.BaseScreen

class AnalyticsScreen(
    private val item: MenuModule
): BaseScreen<AnalyticsState, AnalyticsEffect, AnalyticsIntent>() {

    @Composable
    override fun getViewModel() = koinViewModel<AnalyticsViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(
        state: AnalyticsState,
        intentHandler: (AnalyticsIntent) -> Unit
    ) {

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            intentHandler(AnalyticsIntent.Init)
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Analitica: ${item.name}") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                imageVector = Lucide.ArrowLeft,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                item.urlAnalytics?.let {
                    PowerBIReport(url = it) {
                        intentHandler(AnalyticsIntent.OnRender)
                    }
                }
            }
        }
    }

    override fun effectHandler(sideEffect: AnalyticsEffect) {
        TODO("Not yet implemented")
    }
}

