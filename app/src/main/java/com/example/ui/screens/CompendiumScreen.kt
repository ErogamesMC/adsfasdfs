package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Vaccines
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
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
import com.example.data.database.CompendiumData
import com.example.data.model.EquipmentItem
import com.example.data.model.ItemCategory
import com.example.data.model.MinesFloorRange
import com.example.data.model.MonsterEntity
import com.example.data.model.NpcGiftInfo
import com.example.data.model.WeaponItem
import com.example.ui.components.ItemCard
import com.example.ui.components.ItemDetailModal
import com.example.ui.components.StardewSprite
import com.example.ui.viewmodel.StardewViewModel

import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.ui.text.style.TextOverflow
import com.example.ui.components.SeasonBadge

enum class CompendiumTab(val title: String, val icon: String) {
    MONSTERS("Monstruos", "👾"),
    MINES("La Mina", "⛏️"),
    WEAPONS("Armas", "⚔️"),
    GIFTS("Regalos NPC", "🎁"),
    ALL_ITEMS("Ítems", "📦")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompendiumScreen(
    viewModel: StardewViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(CompendiumTab.MONSTERS) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 12.dp)
    ) {
        // Header
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "Guía & Enciclopedia Stardew",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Monstruos, mina, armas, regalos de aldeanos y directorio completo.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Tabs
        ScrollableTabRow(
            selectedTabIndex = selectedTab.ordinal,
            edgePadding = 16.dp,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        ) {
            CompendiumTab.values().forEach { tab ->
                Tab(
                    selected = selectedTab == tab,
                    onClick = { selectedTab = tab },
                    text = {
                        Text(
                            text = "${tab.icon} ${tab.title}",
                            fontSize = 13.sp,
                            fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    modifier = Modifier.testTag("compendium_tab_${tab.name}")
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Content
        Box(modifier = Modifier.fillMaxSize()) {
            when (selectedTab) {
                CompendiumTab.MONSTERS -> MonstersSection()
                CompendiumTab.MINES -> MinesSection()
                CompendiumTab.WEAPONS -> WeaponsAndEquipmentSection()
                CompendiumTab.GIFTS -> NpcGiftsSection()
                CompendiumTab.ALL_ITEMS -> AllItemsCatalogSection(viewModel = viewModel)
            }
        }
    }
}

// =============================================================================
// 1. MONSTER SECTION
// =============================================================================
@Composable
private fun MonstersSection() {
    var searchQuery by remember { mutableStateOf("") }
    var locationFilter by remember { mutableStateOf<String?>(null) }

    val monsters = remember(searchQuery, locationFilter) {
        CompendiumData.monsters.filter { mon ->
            val matchesQuery = searchQuery.isBlank() ||
                    mon.nameEs.contains(searchQuery, ignoreCase = true) ||
                    mon.nameEn.contains(searchQuery, ignoreCase = true) ||
                    mon.drops.any { drop -> drop.itemName.contains(searchQuery, ignoreCase = true) }

            val matchesLoc = locationFilter == null || mon.location.contains(locationFilter!!, ignoreCase = true)
            matchesQuery && matchesLoc
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Search
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Buscar monstruo u objeto soltado...", fontSize = 13.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = if (searchQuery.isNotEmpty()) {
                {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Close, contentDescription = "Limpiar")
                    }
                }
            } else null,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                focusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Location chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val locations = listOf(
                null to "Todos",
                "Mina" to "⛏️ La Mina",
                "Cueva del Cráneo" to "💀 Cueva del Cráneo",
                "Desierto" to "🌵 Desierto"
            )
            locations.forEach { (loc, label) ->
                FilterChip(
                    selected = locationFilter == loc,
                    onClick = { locationFilter = if (locationFilter == loc) null else loc },
                    label = { Text(label, fontSize = 11.sp) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(monsters, key = { it.id }) { monster ->
                MonsterCard(monster = monster)
            }
        }
    }
}

@Composable
private fun MonsterCard(monster: MonsterEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("monster_card_${monster.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StardewSprite(
                        nameEn = monster.nameEn,
                        defaultEmoji = "👾",
                        size = 42.dp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = monster.nameEs,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = monster.nameEn,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = monster.location,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Stats grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatBadge(icon = Icons.Default.Healing, label = "HP: ${monster.hp}", color = Color(0xFFE53935))
                StatBadge(icon = Icons.Default.MilitaryTech, label = "Daño: ${monster.damage}", color = Color(0xFFFF9800))
                StatBadge(icon = Icons.Default.Shield, label = "Defensa: ${monster.defense}", color = Color(0xFF1E88E5))
                StatBadge(icon = Icons.Default.Speed, label = "Vel: ${monster.speed}", color = Color(0xFF4CAF50))
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Description
            Text(
                text = monster.description,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Drops
            Text(
                text = "🎁 OBJETOS QUE SUELTA (DROPS):",
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                monster.drops.forEach { drop ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "• ${drop.itemName}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = drop.chancePercentage,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StatBadge(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 6.dp, vertical = 3.dp)
    ) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(12.dp))
        Spacer(modifier = Modifier.width(3.dp))
        Text(text = label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = color)
    }
}

// =============================================================================
// 2. MINES SECTION
// =============================================================================
@Composable
private fun MinesSection() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(CompendiumData.mineRanges) { floor ->
            MinesFloorCard(floor = floor)
        }
    }
}

@Composable
private fun MinesFloorCard(floor: MinesFloorRange) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = floor.rangeName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Tema: ${floor.theme}",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = floor.description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)

            Spacer(modifier = Modifier.height(12.dp))

            // Minerales y gemas
            Text(
                text = "💎 MINERALES Y GEMAS:",
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                floor.oresAndMinerals.forEach { item ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(text = item, fontSize = 11.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Geodas y Cajas
            Text(
                text = "📦 GEODAS Y CONTENEDORES:",
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                floor.geodesAndContainers.forEach { geode ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(text = geode, fontSize = 11.sp, color = MaterialTheme.colorScheme.onTertiaryContainer)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Objetos especiales
            Text(
                text = "⭐ TESOROS Y RECOMPENSAS ESPECIALES:",
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                floor.specialDrops.forEach { drop ->
                    Text(text = "• $drop", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    }
}

// =============================================================================
// 3. WEAPONS AND EQUIPMENT SECTION
// =============================================================================
@Composable
private fun WeaponsAndEquipmentSection() {
    var subTab by remember { mutableStateOf(0) } // 0 = Armas, 1 = Anillos y Calzado

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = subTab == 0,
                onClick = { subTab = 0 },
                label = { Text("⚔️ Armas de Combate", fontSize = 12.sp) },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                selected = subTab == 1,
                onClick = { subTab = 1 },
                label = { Text("💍 Anillos y Calzado", fontSize = 12.sp) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (subTab == 0) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(CompendiumData.weapons) { weapon ->
                    WeaponCard(weapon = weapon)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(CompendiumData.equipment) { item ->
                    EquipmentCard(item = item)
                }
            }
        }
    }
}

@Composable
private fun WeaponCard(weapon: WeaponItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val weaponEmoji = when (weapon.type) {
                        "Espada" -> "🗡️"
                        "Daga" -> "🗡️"
                        "Mazo" -> "🔨"
                        else -> "🎯"
                    }
                    StardewSprite(
                        nameEn = weapon.nameEn,
                        defaultEmoji = weaponEmoji,
                        size = 38.dp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = weapon.nameEs,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${weapon.type} (Nivel ${weapon.level})",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${weapon.minDamage}-${weapon.maxDamage} Dmg",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = "Velocidad: ${weapon.speed}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)
                Text(text = "Crítico: ${weapon.critChance}%", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)
                if (weapon.defense > 0) {
                    Text(text = "Defensa: +${weapon.defense}", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "📍 Obtención: ${weapon.source}",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun EquipmentCard(item: EquipmentItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StardewSprite(
                        nameEn = item.nameEn,
                        defaultEmoji = if (item.type == "Anillo") "💍" else "🥾",
                        size = 38.dp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = item.nameEs,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = item.type,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (item.defense > 0 || item.immunity > 0) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Def: +${item.defense} | Inmunidad: +${item.immunity}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = item.effect,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "📍 Obtención: ${item.source}",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// =============================================================================
// 4. NPC GIFTS SECTION
// =============================================================================
@Composable
private fun NpcGiftsSection() {
    var searchQuery by remember { mutableStateOf("") }
    var candidateOnly by remember { mutableStateOf(false) }

    val npcs = remember(searchQuery, candidateOnly) {
        CompendiumData.npcs.filter { npc ->
            val matchesQuery = searchQuery.isBlank() ||
                    npc.nameEs.contains(searchQuery, ignoreCase = true) ||
                    npc.lovedGifts.any { gift -> gift.contains(searchQuery, ignoreCase = true) }
            val matchesCandidate = !candidateOnly || npc.isCandidate
            matchesQuery && matchesCandidate
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Buscar aldeano o regalo...", fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    focusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )

            FilterChip(
                selected = candidateOnly,
                onClick = { candidateOnly = !candidateOnly },
                label = { Text("Solteros ❤️", fontSize = 11.sp) }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(npcs, key = { it.id }) { npc ->
                NpcCard(npc = npc)
            }
        }
    }
}

@Composable
private fun NpcCard(npc: NpcGiftInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StardewSprite(
                        nameEn = npc.nameEn,
                        defaultEmoji = if (npc.isCandidate) "💍" else "👤",
                        size = 46.dp,
                        cornerRadius = 23.dp,
                        customUrl = npc.imageUrl
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = npc.nameEs,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            if (npc.isCandidate) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "❤️ Soltero/a", fontSize = 10.sp, color = MaterialTheme.colorScheme.tertiary)
                            }
                        }
                        Text(
                            text = "🎂 Cumpleaños: ${npc.birthday}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }

                Text(
                    text = npc.location,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Loved gifts
            Text(
                text = "💖 REGALOS AMADOS (+80 PTS DE AMISTAD):",
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.tertiary,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                npc.lovedGifts.forEach { gift ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.4f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "❤️ $gift",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Liked gifts
            Text(
                text = "👍 Le Gustan (+45 Pts): ${npc.likedGifts.joinToString(", ")}",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )

            // Hated gifts
            Text(
                text = "❌ Detesta: ${npc.hatedGifts.joinToString(", ")}",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
            )
        }
    }
}

// =============================================================================
// 5. ALL ITEMS CATALOG SECTION
// =============================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AllItemsCatalogSection(viewModel: StardewViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("TODAS") }

    val allItems by viewModel.filteredItems.collectAsStateWithLifecycle()
    val favoriteIds by viewModel.favoriteIds.collectAsStateWithLifecycle()
    var selectedItem by remember { mutableStateOf<com.example.data.model.ItemEntity?>(null) }
    val sheetState = androidx.compose.material3.rememberModalBottomSheetState()

    val filteredList = remember(allItems, searchQuery, selectedCategory) {
        allItems.filter { item ->
            val matchesQuery = searchQuery.isBlank() ||
                    item.nameEs.contains(searchQuery, ignoreCase = true) ||
                    item.nameEn.contains(searchQuery, ignoreCase = true) ||
                    item.id.contains(searchQuery, ignoreCase = true) ||
                    item.location.contains(searchQuery, ignoreCase = true) ||
                    item.description.contains(searchQuery, ignoreCase = true)
            val matchesCategory = selectedCategory == "TODAS" ||
                    item.category.equals(selectedCategory, ignoreCase = true) ||
                    ItemCategory.fromString(item.category).name.equals(selectedCategory, ignoreCase = true)
            matchesQuery && matchesCategory
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Buscar objeto por nombre o ubicación...", fontSize = 13.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = if (searchQuery.isNotEmpty()) {
                {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Close, contentDescription = "Limpiar")
                    }
                }
            } else null,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                focusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Category Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val categories = listOf(
                "TODAS" to "Todas",
                "CROP" to "Cultivos",
                "FISH" to "Peces",
                "FORAGE" to "Forraje",
                "MINERAL" to "Minerales",
                "ARTISAN" to "Artesanales",
                "COOKING" to "Cocina",
                "TOOL" to "Herramientas",
                "WEAPON" to "Armas",
                "CRAFTING" to "Fabricación",
                "FURNITURE" to "Muebles",
                "HAT" to "Sombreros",
                "BOOTS" to "Calzado",
                "RING" to "Anillos",
                "BAIT_TACKLE" to "Cebo/Aparejos",
                "TREE" to "Árboles",
                "FERTILIZER" to "Fertilizantes",
                "BOOK" to "Libros",
                "SECRET_NOTE" to "Notas Secretas",
                "ARTIFACT" to "Artefactos",
                "ANIMAL" to "Animales"
            )
            categories.forEach { (catKey, catLabel) ->
                FilterChip(
                    selected = selectedCategory == catKey,
                    onClick = { selectedCategory = catKey },
                    label = { Text(catLabel, fontSize = 11.sp) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Mostrando ${filteredList.size} objetos:",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredList, key = { it.id }) { item ->
                CompendiumListItemCard(
                    item = item,
                    isFavorite = favoriteIds.contains(item.id),
                    onToggleFavorite = { viewModel.toggleFavorite(item.id) },
                    onClick = { selectedItem = item }
                )
            }
        }
    }

    if (selectedItem != null) {
        ItemDetailModal(
            item = selectedItem!!,
            isFavorite = favoriteIds.contains(selectedItem!!.id),
            onToggleFavorite = { viewModel.toggleFavorite(selectedItem!!.id) },
            onDismiss = { selectedItem = null },
            sheetState = sheetState
        )
    }
}

@Composable
private fun CompendiumListItemCard(
    item: com.example.data.model.ItemEntity,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("compendium_item_${item.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Sprite Box
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
                size = 46.dp
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Text Info
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = item.nameEs,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    SeasonBadge(seasonName = item.season)
                }

                Text(
                    text = item.nameEn,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = item.location,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Sell Prices and Crop/Artisan details
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "💰 ${item.sellPriceNormal}g",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (item.sellPriceIridium > item.sellPriceNormal) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "⭐ ${item.sellPriceIridium}g",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                    if (item.kegPrice > 0) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.4f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "🍷 ${item.kegPrice}g",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = onToggleFavorite,
                modifier = Modifier
                    .size(36.dp)
                    .testTag("favorite_button_${item.id}")
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorito",
                    tint = if (isFavorite) Color(0xFFE53935) else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
