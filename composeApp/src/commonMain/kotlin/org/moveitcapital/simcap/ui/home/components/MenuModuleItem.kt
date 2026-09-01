package org.moveitcapital.simcap.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.moveitcapital.simcap.domain.model.MenuModule
import org.moveitcapital.simcap.ui.commons.CommonNetworkImage

@Composable
fun MenuModuleItem(
    subMenu: MenuModule,
    listMenuSelected: SnapshotStateList<MenuModule>
) {
    subMenu.urlIcon?.let {
        val url = "https://simcap.masivocapital.co/assets/icons/$it"
        CommonNetworkImage(
            url = url,
            messageError = subMenu.name,
            modifier = Modifier.Companion
                .fillMaxWidth()
                .aspectRatio(2f)
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable { listMenuSelected.add(subMenu) }
        )
    } ?: TextButton(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = CircleShape
            ),
        onClick = { listMenuSelected.add(subMenu) }
    ) {
        Text(text = subMenu.name, style = MaterialTheme.typography.bodyLarge)
    }
}