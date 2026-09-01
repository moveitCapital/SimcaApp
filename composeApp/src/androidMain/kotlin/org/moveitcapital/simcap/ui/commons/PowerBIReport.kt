package org.moveitcapital.simcap.ui.commons

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun PowerBIReport(
    url: String,
    onRender: () -> Unit,
) {
    AndroidView(
        factory = { context ->
            val webView = WebView(context).apply {
                id = android.view.View.generateViewId()
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )

                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    setSupportZoom(true)
                    builtInZoomControls = true
                    displayZoomControls = false
                    allowFileAccess = true
                    allowContentAccess = true
                }

                webViewClient = object : WebViewClient() {
                    override fun onPageCommitVisible(view: WebView?, url: String?) {
                        super.onPageCommitVisible(view, url)
                        onRender()
                    }
                }

                loadUrl(url)
            }

            webView
        },
        modifier = Modifier.fillMaxSize()
    )
}