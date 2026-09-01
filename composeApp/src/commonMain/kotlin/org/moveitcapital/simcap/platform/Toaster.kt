package org.moveitcapital.simcap.platform

enum class ToastDuration {
    SHORT, LONG
}

expect fun showToast(
    message: String,
    duration: ToastDuration = ToastDuration.SHORT
)