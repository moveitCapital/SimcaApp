package org.moveitcapital.simcap.ui.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Eye
import com.composables.icons.lucide.EyeOff
import com.composables.icons.lucide.Key
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.User

@Composable
fun LoginBody(
    modifier: Modifier,
    authError: String?,
    onRecoverPassword: () -> Unit,
    onLogin: (String, String) -> Unit
) {

    val username = remember { mutableStateOf("admin.masivo") }
    val password = remember { mutableStateOf("Masivo2023*") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.padding(12.dp),
            text = "Login",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.outline,
            fontWeight = FontWeight.Black,
        )

        LoginTextFields(
            modifier = Modifier.fillMaxWidth(),
            username = username,
            password = password,
            isError = authError != null,
        )

        authError?.let {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = it,
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onLogin(username.value, password.value) },
            enabled = username.value.isNotBlank() && password.value.isNotBlank()
        ) { Text("Acceder") }

        TextButton(onRecoverPassword) {
            Text(
                text = "Recuperar contraseña",
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun LoginTextFields(
    modifier: Modifier,
    username: MutableState<String>,
    password: MutableState<String>,
    isError: Boolean,
) {

    var showPassword by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier.background(
            color = Color.White,
            shape = RoundedCornerShape(10.dp)
        ),
        value = username.value,
        onValueChange = { username.value = it },
        placeholder = { Text("Usuario") },
        isError = isError,
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        leadingIcon = {
            Icon(
                imageVector = Lucide.User,
                contentDescription = null
            )
        }
    )

    OutlinedTextField(
        modifier = modifier.background(
            color = Color.White,
            shape = RoundedCornerShape(10.dp)
        ),
        value = password.value,
        onValueChange = { password.value = it },
        placeholder = { Text("Contraseña") },
        isError = isError,
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        leadingIcon = {
            Icon(
                imageVector = Lucide.Key,
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(onClick = { showPassword = !showPassword }) {
                Icon(
                    imageVector = if (showPassword) Lucide.Eye else Lucide.EyeOff,
                    contentDescription = null
                )
            }
        }
    )
}