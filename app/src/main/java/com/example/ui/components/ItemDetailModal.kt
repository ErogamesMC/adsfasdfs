package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ItemCategory
import com.example.data.model.ItemEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailModal(
    item: ItemEntity?,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onDismiss: () -> Unit,
    onSelectForCalc: ((ItemEntity) -> Unit)? = null,
    sheetState: SheetState
) {
    if (item == null) return

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Title Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val categoryEmoji = when (item.category.uppercase()) {
                    "CULTIVO", "CROP" -> "🌱"
                    "PESCA", "FISH" -> "🐟"
                    "FORRAJE", "FORAGE" -> "🍄"
                    "MINERAL" -> "💎"
                    "ARTESANAL", "ARTISAN" -> "🍷"
                    "ANIMAL" -> "🥚"
                    "ARTEFACTO", "ARTIFACT" -> "🏺"
                    "COCINA", "COOKING" -> "🍳"
                    "HERRAMIENTA", "TOOL" -> "🪓"
                    "ARMA", "WEAPON" -> "⚔️"
                    "FABRICACION", "CRAFTING" -> "🔨"
                    "MUEBLES", "FURNITURE" -> "🪑"
                    "SOMBRERO", "HAT" -> "🎩"
                    "CALZADO", "BOOTS" -> "🥾"
                    "ANILLO", "RING" -> "💍"
                    "CEBO", "BAIT_TACKLE" -> "🪱"
                    "ARBOL", "TREE" -> "🌳"
                    "FERTILIZANTE", "FERTILIZER" -> "🧪"
                    "LIBRO", "BOOK" -> "📖"
                    "NOTA", "SECRET_NOTE" -> "📜"
                    else -> "📦"
                }

                StardewSprite(
                    nameEn = item.nameEn,
                    defaultEmoji = categoryEmoji,
                    size = 56.dp
                )

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.nameEs,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    val catDisplayName = ItemCategory.fromString(item.category).displayName
                    Text(
                        text = "${item.nameEn} • $catDisplayName",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onToggleFavorite) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (isFavorite) Color(0xFFE53935) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Badges Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SeasonBadge(seasonName = item.season)
                Box(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.secondaryContainer,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = ItemCategory.fromString(item.category).displayName,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                Box(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.tertiaryContainer,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "ID: #${item.id}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Location Section
            DetailSection(
                icon = Icons.Default.LocationOn,
                title = "Ubicación en el Juego",
                content = item.location
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Source Section
            DetailSection(
                icon = Icons.Default.ShoppingBag,
                title = "Forma de Obtención",
                content = item.source
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Crop Stats if applicable
            if (item.isCrop) {
                DetailSection(
                    icon = Icons.Default.Info,
                    title = "Datos de Cultivo y Siembras",
                    content = buildString {
                        append("• Tiempo de Crecimiento: ${item.growthDays} días\n")
                        if (item.regrowthDays > 0) {
                            append("• Re-cosecha continua: Cada ${item.regrowthDays} días\n")
                        } else {
                            append("• Cosecha única (no re-brota)\n")
                        }
                        if (item.seedPrice > 0) {
                            append("• Precio de Semilla: ${item.seedPrice}g\n")
                        }
                        if (item.multiYieldAvg > 1.0f) {
                            append("• Rendimiento promedio por cosecha: ${item.multiYieldAvg} unidades\n")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Prices Section
            Text(
                text = "Precios de Venta por Calidad",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QualityBadge(qualityName = "NORMAL", price = item.sellPriceNormal)
                QualityBadge(qualityName = "SILVER", price = item.sellPriceSilver)
                QualityBadge(qualityName = "GOLD", price = item.sellPriceGold)
                QualityBadge(qualityName = "IRIDIUM", price = item.sellPriceIridium)
            }

            if (item.jarPrice > 0 || item.kegPrice > 0) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Procesamiento Artesanal:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                if (item.jarPrice > 0) {
                    Text(
                        text = "• Jarra de Mermeladas / Encurtidos: ${item.jarPrice}g",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                if (item.kegPrice > 0) {
                    Text(
                        text = "• Barril de Roble (Vino / Cerveza / Jugo): ${item.kegPrice}g",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Description
            if (item.description.isNotBlank()) {
                Text(
                    text = item.description,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Calculator Shortcut button if crop
            if (item.isCrop && onSelectForCalc != null) {
                Button(
                    onClick = {
                        onSelectForCalc(item)
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Calculate,
                        contentDescription = "Calculadora",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Calcular Beneficio de este Cultivo")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun DetailSection(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    content: String
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = content,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 18.sp
        )
    }
}
