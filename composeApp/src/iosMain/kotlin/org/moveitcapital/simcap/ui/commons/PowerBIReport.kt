package org.moveitcapital.simcap.ui.commons

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun PowerBIReport(
    url: String,
    onRender: () -> Unit,
) {
    UIKitView(
        factory = {
            val config = WKWebViewConfiguration()
            val webView = WKWebView(frame = CGRectZero.readValue(), configuration = config)
            val nsUrl = NSURL.URLWithString(url)!!
            val request = NSURLRequest.requestWithURL(nsUrl)
            webView.loadRequest(request)
            webView
        },
        update = { webView ->
            val nsUrl = NSURL.URLWithString(url)!!
            val request = NSURLRequest.requestWithURL(nsUrl)
            webView.loadRequest(request)
            onRender() //TODO: Review
        }
    )
}