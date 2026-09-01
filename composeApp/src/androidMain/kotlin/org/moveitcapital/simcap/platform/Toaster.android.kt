package org.moveitcapital.simcap.platform

import android.content.Context
import android.widget.Toast

private object ToastHelper {
    var context: Context? = null

    fun initialize(context: Context) {
        this.context = context.applicationContext
    }
}

fun initializeToastHelper(context: Context) {
    ToastHelper.initialize(context)
}

actual fun showToast(
    message: String,
    duration: ToastDuration
) {
    ToastHelper.context?.let { ctx ->
        val toastDuration = when (duration) {
            ToastDuration.SHORT -> Toast.LENGTH_SHORT
            ToastDuration.LONG -> Toast.LENGTH_LONG
        }
        Toast.makeText(ctx, message, toastDuration).show()
    } ?: run {
        println("Toast Error: Context not initialized for ToastHelper")
    }
}