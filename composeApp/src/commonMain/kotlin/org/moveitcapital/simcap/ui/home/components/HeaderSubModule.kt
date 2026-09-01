package org.moveitcapital.simcap.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Lucide

@Composable
fun HeaderSubModule(
    modifier: Modifier,
    title: String,
    onBack: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Companion.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        FilledTonalIconButton(onClick = { onBack() }) {
            Icon(imageVector = Lucide.ArrowLeft, contentDescription = null)
        }

        Text(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .background(
                    color = IconButtonDefaults.filledTonalIconButtonColors().containerColor,
                    shape = CircleShape
                )
                .padding(ButtonDefaults.ContentPadding),
            text = "Módulo: $title",
            style = MaterialTheme.typography.titleMedium
        )
    }
}