package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GoldQualityColor
import com.example.ui.theme.IridiumQualityColor
import com.example.ui.theme.SilverQualityColor

@Composable
fun QualityBadge(
    qualityName: String,
    price: Int,
    modifier: Modifier = Modifier
) {
    val (color, starCount) = when (qualityName.uppercase()) {
        "SILVER" -> SilverQualityColor to 1
        "GOLD" -> GoldQualityColor to 1
        "IRIDIUM" -> IridiumQualityColor to 2
        else -> MaterialTheme.colorScheme.onSurfaceVariant to 0
    }

    Box(
        modifier = modifier
            .background(
                color = color.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            if (starCount > 0) {
                repeat(starCount) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Calidad",
                        tint = color,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
            Text(
                text = "${price}g",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (starCount > 0) color else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = if (starCount > 0) 2.dp else 0.dp)
            )
        }
    }
}
