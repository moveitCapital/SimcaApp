package org.moveitcapital.simcap.ui.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.composables.icons.lucide.ArrowRight
import com.composables.icons.lucide.Lucide
import org.moveitcapital.simcap.domain.model.Provider

@Composable
fun ProvidersSelectionDialog(
    providers: List<Provider>,
    onSelect: (Provider) -> Unit,
) {
    Dialog(onDismissRequest = {}) {
        Column(
            modifier = Modifier.Companion
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Text(
                text = "Seleccionar empresa",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Companion.Bold,
            )

            providers.forEach {
                TextButton(onClick = { onSelect(it) }) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = it.companyName.orEmpty(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Companion.Bold,
                        textDecoration = TextDecoration.Companion.Underline
                    )

                    Icon(
                        imageVector = Lucide.ArrowRight,
                        contentDescription = null
                    )
                }
            }
        }
    }
}