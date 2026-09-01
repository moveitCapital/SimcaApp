package org.moveitcapital.simcap.ui.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil3.compose.SubcomposeAsyncImage
import com.composables.icons.lucide.ImageOff
import com.composables.icons.lucide.Lucide

@Composable
fun CommonNetworkImage(
    modifier: Modifier = Modifier.Companion,
    url: String,
    messageError: String = "Error al cargar",
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    contentScale: ContentScale = ContentScale.Companion.Crop,
) {
    SubcomposeAsyncImage(
        modifier = modifier.background(backgroundColor),
        model = url,
        contentDescription = null,
        contentScale = contentScale,
        loading = {
            Box(
                modifier = Modifier.Companion.fillMaxSize(),
                contentAlignment = Alignment.Companion.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.Companion.fillMaxHeight(0.8f)
                )
            }
        },
        error = {
            Column(
                modifier = Modifier.Companion.fillMaxSize(),
                horizontalAlignment = Alignment.Companion.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Lucide.ImageOff,
                    contentDescription = null,
                )
                Text(
                    text = messageError,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Companion.SemiBold
                )
            }
        }
    )
}