package org.moveitcapital.simcap.ui.login.components

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
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.composables.icons.lucide.AtSign
import com.composables.icons.lucide.Lucide

@Composable
fun PasswordRecoverDialog(
    isLoading: Boolean,
    emailError: String?,
    onCancel: () -> Unit,
    onEmailToRecover: (String) -> Unit,
) {

    var email by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onCancel) {
        Column(
            modifier = Modifier.Companion
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Recuperar contraseña",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Companion.Bold,
            )

            OutlinedTextField(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .background(
                        color = Color.Companion.White,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
                    ),
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Dirección de Email") },
                singleLine = true,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Companion.Email),
                isError = emailError != null,
                leadingIcon = {
                    Icon(
                        imageVector = Lucide.AtSign,
                        contentDescription = null
                    )
                }
            )

            emailError?.let {
                Text(
                    modifier = Modifier.Companion.fillMaxWidth(),
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Companion.Bold
                )
            }

            if (isLoading) LinearProgressIndicator(Modifier.fillMaxWidth())

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                FilledTonalButton(
                    modifier = Modifier.Companion.weight(1f),
                    onClick = onCancel,
                ) { Text("Cancelar") }

                Button(
                    modifier = Modifier.Companion.weight(1f),
                    onClick = { onEmailToRecover(email) },
                    enabled = email.isNotBlank() && !isLoading
                ) { Text("Enviar") }
            }
        }
    }
}