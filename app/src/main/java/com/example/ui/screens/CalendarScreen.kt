package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Grass
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.EventType
import com.example.data.model.Season
import com.example.data.model.SeasonEvent
import com.example.ui.viewmodel.StardewViewModel

@Composable
fun CalendarScreen(
    viewModel: StardewViewModel,
    modifier: Modifier = Modifier
) {
    val currentSeason by viewModel.calendarSeason.collectAsStateWithLifecycle()
    val events by viewModel.currentSeasonEvents.collectAsStateWithLifecycle()
    var selectedDay by remember { mutableStateOf(1) }

    val seasons = listOf(Season.SPRING, Season.SUMMER, Season.FALL, Season.WINTER)
    val dayNames = listOf("Lu", "Ma", "Mi", "Ju", "Vi", "Sá", "Do")

    val selectedDayEvents = events.filter { it.day == selectedDay }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .padding(bottom = 80.dp)
    ) {
        // Title
        Text(
            text = "Calendario Agrícola y Eventos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Planifica tus siembras de 28 días, festivales y cumpleaños",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Season Tabs
        TabRow(
            selectedTabIndex = seasons.indexOf(currentSeason),
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            seasons.forEach { season ->
                val isSelected = currentSeason == season
                val color = Color(season.colorHex)
                Tab(
                    selected = isSelected,
                    onClick = {
                        viewModel.calendarSeason.value = season
                        selectedDay = 1
                    },
                    modifier = Modifier.testTag("calendar_tab_${season.name}")
                ) {
                    Text(
                        text = season.displayName,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) color else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Calendar Grid Header (Days of week)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            dayNames.forEach { dayName ->
                Text(
                    text = dayName,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.width(36.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 28 Days Grid (4 weeks x 7 days)
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(4) { week ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    repeat(7) { dayOfWeek ->
                        val dayNumber = (week * 7) + dayOfWeek + 1
                        val isSelected = dayNumber == selectedDay
                        val dayEvents = events.filter { it.day == dayNumber }
                        val hasFestival = dayEvents.any { it.type == EventType.FESTIVAL }
                        val hasBirthday = dayEvents.any { it.type == EventType.BIRTHDAY }
                        val hasDeadline = dayEvents.any { it.type == EventType.CROP_DEADLINE }

                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(
                                    when {
                                        isSelected -> MaterialTheme.colorScheme.primary
                                        hasFestival -> Color(0xFFFFD54F) // Gold
                                        hasBirthday -> Color(0xFFCE93D8) // Purple
                                        hasDeadline -> Color(0xFFA5D6A7) // Green
                                        else -> MaterialTheme.colorScheme.surface
                                    }
                                )
                                .border(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                    shape = CircleShape
                                )
                                .clickable { selectedDay = dayNumber }
                                .testTag("calendar_day_$dayNumber"),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$dayNumber",
                                    fontSize = 14.sp,
                                    fontWeight = if (isSelected || dayEvents.isNotEmpty()) FontWeight.Bold else FontWeight.Normal,
                                    color = when {
                                        isSelected -> MaterialTheme.colorScheme.onPrimary
                                        hasFestival || hasBirthday || hasDeadline -> Color.Black
                                        else -> MaterialTheme.colorScheme.onSurface
                                    }
                                )
                                if (dayEvents.isNotEmpty()) {
                                    Box(
                                        modifier = Modifier
                                            .size(4.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (isSelected) MaterialTheme.colorScheme.onPrimary
                                                else MaterialTheme.colorScheme.primary
                                            )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Selected Day Detail Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Event,
                        contentDescription = "Día",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Día $selectedDay de ${currentSeason.displayName}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (selectedDayEvents.isEmpty()) {
                    Text(
                        text = "Sin eventos especiales en este día. ¡Ideal para trabajar en tus cultivos o explorar las minas!",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    selectedDayEvents.forEach { event ->
                        val (icon, tint) = when (event.type) {
                            EventType.FESTIVAL -> Icons.Default.CalendarMonth to Color(0xFFC78300)
                            EventType.BIRTHDAY -> Icons.Default.Cake to Color(0xFF8B5CF6)
                            EventType.CROP_DEADLINE -> Icons.Default.Grass to MaterialTheme.colorScheme.primary
                            else -> Icons.Default.Event to MaterialTheme.colorScheme.primary
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = event.title,
                                tint = tint,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = event.title,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                if (event.description.isNotBlank()) {
                                    Text(
                                        text = event.description,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // All Season Events Summary
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Todos los Eventos de ${currentSeason.displayName}:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                events.forEach { event ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Día ${event.day}: ${event.title}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}
