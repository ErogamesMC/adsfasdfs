package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.WineBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Season

@Composable
fun RecommendationsScreen(
    modifier: Modifier = Modifier
) {
    var selectedSeason by remember { mutableStateOf(Season.SPRING) }
    val seasons = listOf(Season.SPRING, Season.SUMMER, Season.FALL, Season.GREENHOUSE)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .padding(bottom = 80.dp)
    ) {
        // Title
        Text(
            text = "Recomendaciones de Cosecha",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Estrategias óptimas para maximizar oro y producción según la temporada",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Season Selector Tabs
        TabRow(
            selectedTabIndex = seasons.indexOf(selectedSeason),
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            seasons.forEach { season ->
                val isSelected = selectedSeason == season
                val color = Color(season.colorHex)
                Tab(
                    selected = isSelected,
                    onClick = { selectedSeason = season },
                    modifier = Modifier.testTag("reco_tab_${season.name}")
                ) {
                    Text(
                        text = season.displayName,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) color else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        when (selectedSeason) {
            Season.SPRING -> SpringRecommendations()
            Season.SUMMER -> SummerRecommendations()
            Season.FALL -> FallRecommendations()
            Season.GREENHOUSE -> GreenhouseRecommendations()
            else -> SpringRecommendations()
        }
    }
}

@Composable
private fun SpringRecommendations() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        RecommendationCard(
            icon = Icons.Default.MonetizationOn,
            iconTint = Color(0xFF4CAF50),
            title = "Top Ganancias en Oro Directo",
            subtitle = "Los cultivos más lucrativos de Primavera",
            items = listOf(
                "Fresa (Festival del Huevo, 13 Primavera): La reina de la primavera. Comprar en año 1 para sembrar el día 13 (da 2 cosechas), o guardar semillas para sembrar el día 1 del Año 2 (da 5 cosechas).",
                "Ruibarbo (Comprar en Oasis Desierto por 100g): 220g de venta base. El cultivo individual más rentable de primavera.",
                "Coliflor: 175g de venta base. Cosecha a los 12 días. Excelente para Jarras de Mermeladas."
            )
        )

        RecommendationCard(
            icon = Icons.Default.LocalFlorist,
            iconTint = Color(0xFF81C784),
            title = "Mejores Cultivos Múltiples",
            subtitle = "Solo compras la semilla una vez por temporada",
            items = listOf(
                "Fresa: Re-cosecha continua cada 4 días.",
                "Judía verde: Re-cosecha cada 3 días en enrejado. Siémbrala en los bordes para no bloquear el paso."
            )
        )

        RecommendationCard(
            icon = Icons.Default.WineBar,
            iconTint = Color(0xFF8B5CF6),
            title = "Potencial Artesanal (Jarras y Barriles)",
            subtitle = "Aumenta drásticamente el valor de tus cosechas",
            items = listOf(
                "Ruibarbo en Barril (Vino de Ruibarbo): Se vende por 495g.",
                "Coliflor en Jarra de Mermelada: Se vende por 400g."
            )
        )

        RecommendationCard(
            icon = Icons.Default.EmojiEvents,
            iconTint = Color(0xFFFFB300),
            title = "Cultivo Gigante Potencial",
            subtitle = "Crea un bloque 3x3 de Coliflores",
            items = listOf(
                "La Coliflor tiene un 1% de probabilidad por día de fusionarse en un Cultivo Gigante 3x3 al madurar si se mantiene regada. Al romperlo con hacha otorga de 15 a 21 coliflores."
            )
        )

        RecommendationCard(
            icon = Icons.Default.Lightbulb,
            iconTint = Color(0xFF0288D1),
            title = "Consejo Pro para Primavera",
            subtitle = "Inicio eficiente en el Año 1",
            items = listOf(
                "En tus primeros 4 días, siembra la Chirivía de inicio para subir al Nivel 1 de Agricultura y desbloquear el Espantapájaros.",
                "Ahorra todo tu oro para el 13 de Primavera y compra tantas semillas de Fresa como puedas regar."
            )
        )
    }
}

@Composable
private fun SummerRecommendations() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        RecommendationCard(
            icon = Icons.Default.MonetizationOn,
            iconTint = Color(0xFFFF9800),
            title = "Top Ganancias en Oro Directo",
            subtitle = "Verano es la temporada de mayor volumen",
            items = listOf(
                "Carambola (Oasis Desierto): 750g base por fruta. El cultivo con mayor precio individual del juego.",
                "Arándano: Produce 3 arándanos por planta cada 4 días. Rendimiento masivo pasivo.",
                "Melón: 250g base. Madura a los 12 días."
            )
        )

        RecommendationCard(
            icon = Icons.Default.WineBar,
            iconTint = Color(0xFF8B5CF6),
            title = "Máxima Eficiencia Artesanal",
            subtitle = "Lúpulo y Carambola en Barriles",
            items = listOf(
                "Lúpulo en Barril (Cerveza Artesanal Pale Ale): Se fabrica en solo 1.5 días y se vende por 300g. Con 30 plantas de lúpulo y 30 barriles ganarás 9,000g DIARIOS.",
                "Vino de Carambola: Se vende por 2,250g sin curar, y 4,500g si se añeja a calidad Iridio en Tonel de Bodega."
            )
        )

        RecommendationCard(
            icon = Icons.Default.EmojiEvents,
            iconTint = Color(0xFFFFB300),
            title = "Cultivo Gigante de Verano",
            subtitle = "Melones gigantes en la finca",
            items = listOf(
                "Planta Melones en parches de 3x3 o 5x5 y déjalos regados tras madurar para intentar obtener un Melón Gigante."
            )
        )
    }
}

@Composable
private fun FallRecommendations() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        RecommendationCard(
            icon = Icons.Default.MonetizationOn,
            iconTint = Color(0xFFE65100),
            title = "Top Ganancias de Otoño",
            subtitle = "Cierra el año con cosechas doradas",
            items = listOf(
                "Arándano rojo / agrio (Cranberries): Produce 2 arándanos cada 5 días. Excelente retorno de inversión sin procesar.",
                "Calabaza: 320g base por unidad. Alta densidad de ganancias.",
                "Fruto Antiguo: Cosecha continuada durante todo el otoño."
            )
        )

        RecommendationCard(
            icon = Icons.Default.AutoAwesome,
            iconTint = Color(0xFFE91E63),
            title = "Miel de Hada Rosa Especial",
            subtitle = "Aprovecha las flores cerca de colmenas",
            items = listOf(
                "Planta una Hada Rosa en el centro de un grupo de Colmenas. Toda la miel producida cambiará a Miel de Hada Rosa por valor de 680g cada 4 días."
            )
        )
    }
}

@Composable
private fun GreenhouseRecommendations() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        RecommendationCard(
            icon = Icons.Default.AutoAwesome,
            iconTint = Color(0xFF009688),
            title = "Estrategia Definitiva de Invernadero",
            subtitle = "Cosecha perpetua sin cambio de temporada",
            items = listOf(
                "Fruto Antiguo: Llena el 100% del Invernadero con Fruto Antiguo + Aspersores de Iridio en los bordes. Re-cosecha 116 unidades cada semana durante todo el año.",
                "Carambola con Acelerador Deluxe: Si no tienes semillas de Fruto Antiguo, siembra Carambola de forma continua.",
                "Árboles Frutales en los bordes: Puedes plantar 18 árboles frutales en el suelo de baldosas alrededor del estanque para obtener fruta diaria ilimitada."
            )
        )
    }
}

@Composable
private fun RecommendationCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    title: String,
    subtitle: String,
    items: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(iconTint.copy(alpha = 0.15f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = iconTint,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            items.forEach { bullet ->
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text(
                        text = "• ",
                        fontWeight = FontWeight.Bold,
                        color = iconTint
                    )
                    Text(
                        text = bullet,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}
