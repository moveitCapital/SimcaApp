package org.moveitcapital.simcap.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.LucideIcon
import com.composables.icons.lucide.Settings
import com.composables.icons.lucide.SquareUserRound
import org.moveitcapital.simcap.domain.model.Auth

@Composable
fun NavigationDrawerHeader(
    modifier: Modifier,
    auth: Auth,
    onAccountClick: () -> Unit
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(12.dp),
        verticalAlignment = Alignment.Companion.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {

            Row(
                verticalAlignment = Alignment.Companion.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                LucideIcon(
                    id = Lucide.SquareUserRound.name,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )

                Text(
                    text = auth.user.username,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Companion.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            auth.userDTO.firstOrNull()?.let {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        FilledTonalIconButton(onClick = onAccountClick) {
            LucideIcon(
                id = Lucide.Settings.name,
                contentDescription = null
            )
        }
    }
}