package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

fun getStardewSpriteUrl(nameEn: String, customUrl: String? = null): String {
    if (!customUrl.isNullOrBlank()) {
        return customUrl
    }
    // Special handling for edge cases or wiki filenames
    val formattedName = nameEn.trim()
        .replace(" ", "_")
        .replace("'", "%27")
    return "https://es.stardewvalleywiki.com/Especial:FilePath/$formattedName.png"
}

@Composable
fun StardewSprite(
    nameEn: String,
    defaultEmoji: String = "📦",
    size: Dp = 48.dp,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
    cornerRadius: Dp = 12.dp,
    customUrl: String? = null
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(getStardewSpriteUrl(nameEn, customUrl))
                .crossfade(true)
                .build(),
            contentDescription = nameEn,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            loading = {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(text = defaultEmoji, fontSize = (size.value * 0.45).sp)
                }
            },
            error = {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(text = defaultEmoji, fontSize = (size.value * 0.45).sp)
                }
            }
        )
    }
}
