package org.moveitcapital.simcap.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.LockKeyhole
import com.composables.icons.lucide.LogOut
import com.composables.icons.lucide.Lucide
import org.moveitcapital.simcap.domain.model.MenuModule
import org.moveitcapital.simcap.ui.home.HomeIntent

@Composable
fun NavigationDrawerBody(
    modifier: Modifier,
    menuSelected: MenuModule?,
    menu: List<MenuModule>?,
    openUpdatePass: () -> Unit,
    intentHandler: (HomeIntent) -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        Text(
            text = "Modulos",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Companion.SemiBold
        )

        menu?.forEach {
            NavigationDrawerItem(
                label = { Text(text = it.name) },
                selected = it == menuSelected,
                onClick = { intentHandler(HomeIntent.OnSelectMenuItem(it)) }
            )
        }

        HorizontalDivider(modifier = Modifier.Companion.padding(vertical = 16.dp))

        Text(
            text = "Acciones",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Companion.SemiBold
        )

        NavigationDrawerItem(
            label = { Text(text = "Cambiar contraseña") },
            selected = false,
            icon = {
                Icon(
                    imageVector = Lucide.LockKeyhole,
                    contentDescription = null
                )
            },
            onClick = { openUpdatePass() }
        )

        NavigationDrawerItem(
            label = { Text(text = "Cerrar sesión") },
            selected = false,
            icon = {
                Icon(
                    imageVector = Lucide.LogOut,
                    contentDescription = null
                )
            },
            onClick = { intentHandler(HomeIntent.OnLogout) }
        )
    }
}