package org.moveitcapital.simcap.ui.commons

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.TriangleAlert
import org.moveitcapital.simcap.core.AppError

@Composable
fun AlertDialogError(
    error: AppError?,
    onDismiss: () -> Unit,
) {
    error?.let {
        AlertDialog(
            onDismissRequest = onDismiss,
            icon = { Icon(Lucide.TriangleAlert, null) },
            title = {
                Text(
                    text = when (it) {
                        is AppError.Network -> "Error de red"
                        is AppError.Timeout -> "Tiempo de espera agotado"
                        is AppError.Http -> "Error HTTP ${it.response.code}"
                        is AppError.Unknown -> "Error desconocido"
                        is AppError.Custom -> "Error"
                        is AppError.Session -> "Session Error"
                    }
                )
            },
            text = {
                Text(
                    text = when (it) {
                        is AppError.Network -> it.message
                        is AppError.Timeout -> it.message
                        is AppError.Http -> it.response.message
                        is AppError.Unknown -> it.error?.message ?: "Error desconocido"
                        is AppError.Custom -> it.message
                        is AppError.Session -> it.message
                    }
                )
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text(if (it is AppError.Session) "Cerrar sesión" else "Aceptar")
                }
            }
        )
    }
}