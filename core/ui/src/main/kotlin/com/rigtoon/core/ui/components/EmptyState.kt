package com.rigtoon.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import com.rigtoon.core.ui.theme.RigToonDimensions

/** حالت خالی؛ متن‌ها توسط فراخواننده از منابع بومی‌سازی‌شده دریافت می‌شوند. */
@Composable
fun EmptyState(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
): Unit {
    Column(
        modifier = modifier
            .widthIn(max = RigToonDimensions.emptyStateMaxWidth)
            .padding(
                horizontal = RigToonDimensions.contentPadding,
                vertical = RigToonDimensions.tabletPadding,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(RigToonDimensions.compactSpacing),
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .semantics { heading() },
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            text = description,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
