package org.moveitcapital.simcap.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.composables.icons.lucide.Eye
import com.composables.icons.lucide.EyeOff
import com.composables.icons.lucide.Lucide
import org.moveitcapital.simcap.ui.home.HomeIntent

@Composable
fun UpdatePasswordDialog(
    username: String,
    error: String?,
    onDismiss: () -> Unit,
    onUpdate: (HomeIntent.OnUpdatePassword) -> Unit
) {

    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showCurrentPassword by remember { mutableStateOf(false) }
    var showNewPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { onDismiss }) {

        Column(
            modifier = Modifier.Companion
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Actualizae contraseña",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Companion.Bold,
            )

            Text(
                text = "Usuario: $username",
                style = MaterialTheme.typography.bodyLarge,
            )

            OutlinedTextField(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .background(
                        color = Color.Companion.White,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
                    ),
                value = currentPassword,
                onValueChange = { currentPassword = it },
                placeholder = { Text("Contraseña actual") },
                singleLine = true,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp),
                visualTransformation = if (showCurrentPassword) VisualTransformation.Companion.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Companion.Password),
                trailingIcon = {
                    IconButton(onClick = { showCurrentPassword = !showCurrentPassword }) {
                        Icon(
                            imageVector = if (showCurrentPassword) Lucide.Eye else Lucide.EyeOff,
                            contentDescription = null
                        )
                    }
                }
            )

            OutlinedTextField(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .background(
                        color = Color.Companion.White,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
                    ),
                value = newPassword,
                onValueChange = { newPassword = it },
                placeholder = { Text("Nueva contraseña") },
                singleLine = true,
                isError = !error.isNullOrEmpty(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp),
                visualTransformation = if (showNewPassword) VisualTransformation.Companion.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Companion.Password),
                trailingIcon = {
                    IconButton(onClick = { showNewPassword = !showNewPassword }) {
                        Icon(
                            imageVector = if (showNewPassword) Lucide.Eye else Lucide.EyeOff,
                            contentDescription = null
                        )
                    }
                }
            )

            OutlinedTextField(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .background(
                        color = Color.Companion.White,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
                    ),
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("Confirmar nueva contraseña") },
                singleLine = true,
                isError = !error.isNullOrEmpty(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp),
                visualTransformation = if (showConfirmPassword) VisualTransformation.Companion.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Companion.Password),
                trailingIcon = {
                    IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                        Icon(
                            imageVector = if (showConfirmPassword) Lucide.Eye else Lucide.EyeOff,
                            contentDescription = null
                        )
                    }
                }
            )

            error?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                FilledTonalButton(
                    modifier = Modifier.Companion.weight(1f),
                    onClick = onDismiss,
                ) { Text("Cancelar") }

                Button(
                    modifier = Modifier.Companion.weight(1f),
                    onClick = {
                        onUpdate(
                            HomeIntent.OnUpdatePassword(
                                currentPassword,
                                newPassword,
                                confirmPassword
                            )
                        )
                    },
                    enabled = newPassword.isNotEmpty()
                            && confirmPassword.isNotEmpty()
                            && currentPassword.isNotEmpty()
                ) { Text("Actualizar") }
            }
        }
    }
}