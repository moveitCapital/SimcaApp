package org.moveitcapital.simcap.platform

import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication
import platform.UIKit.UIWindow
import platform.darwin.DISPATCH_TIME_NOW
import platform.darwin.NSEC_PER_SEC
import platform.darwin.dispatch_after
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_time

@OptIn(ExperimentalForeignApi::class)
actual fun showToast(message: String, duration: ToastDuration) {
    val toastDurationSeconds = when (duration) {
        ToastDuration.SHORT -> 2.0
        ToastDuration.LONG -> 3.5
    }

    val alertController = UIAlertController.alertControllerWithTitle(
        title = null,
        message = message,
        preferredStyle = UIAlertControllerStyleAlert
    )

    val window = UIApplication.sharedApplication.windows.firstOrNull() as? UIWindow
    var rootViewController = window?.rootViewController

    while (rootViewController?.presentedViewController != null) {
        rootViewController = rootViewController.presentedViewController
    }

    if (rootViewController != null) {
        val viewController = rootViewController
        dispatch_async(dispatch_get_main_queue()) {
            viewController.presentViewController(
                viewControllerToPresent = alertController,
                animated = true,
                completion = null
            )
        }

        val dismissTime = dispatch_time(
            `when` = DISPATCH_TIME_NOW,
            delta =  (toastDurationSeconds * NSEC_PER_SEC.toDouble()).toLong()
        )
        dispatch_after(dismissTime, dispatch_get_main_queue()) {
            alertController.dismissViewControllerAnimated(
                flag = true,
                completion = null
            )
        }
    } else {
        println("iOS Toast Error: Could not find rootViewController to present toast.")
    }
}

