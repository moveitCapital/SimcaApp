package org.moveitcapital.simcap.ui.commons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun BottomAnimatedContent(
    modifier: Modifier,
    visible: Boolean,
    content: @Composable ColumnScope.() -> Unit,
){
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 500),
            initialOffsetY = { it }
        ),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = 500),
            targetOffsetY = { it }
        ),
    ) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(MaterialTheme.colorScheme.inversePrimary)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Companion.CenterHorizontally,
            content = content
        )
    }
}