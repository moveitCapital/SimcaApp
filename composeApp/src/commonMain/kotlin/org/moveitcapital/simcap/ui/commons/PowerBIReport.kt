package org.moveitcapital.simcap.ui.commons

import androidx.compose.runtime.Composable

@Composable
expect fun PowerBIReport(url: String, onRender: () -> Unit)