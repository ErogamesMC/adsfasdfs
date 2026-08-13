package com.example.data.model

data class MonsterDrop(
    val itemName: String,
    val chancePercentage: String
)

data class MonsterEntity(
    val id: String,
    val nameEs: String,
    val nameEn: String,
    val location: String,
    val hp: Int,
    val damage: Int,
    val defense: Int,
    val speed: Int,
    val drops: List<MonsterDrop>,
    val description: String
)

data class MinesFloorRange(
    val rangeName: String,
    val floorLevels: String,
    val theme: String,
    val oresAndMinerals: List<String>,
    val geodesAndContainers: List<String>,
    val specialDrops: List<String>,
    val description: String
)

data class WeaponItem(
    val id: String,
    val nameEs: String,
    val nameEn: String,
    val type: String, // Espada, Daga, Mazo, Tirachinas
    val level: Int,
    val minDamage: Int,
    val maxDamage: Int,
    val speed: Int,
    val critChance: Float,
    val weight: Int,
    val defense: Int = 0,
    val source: String
)

data class EquipmentItem(
    val id: String,
    val nameEs: String,
    val nameEn: String,
    val type: String, // Anillo, Calzado
    val effect: String,
    val defense: Int = 0,
    val immunity: Int = 0,
    val source: String
)

data class NpcGiftInfo(
    val id: String,
    val nameEs: String,
    val nameEn: String,
    val birthday: String,
    val isCandidate: Boolean,
    val lovedGifts: List<String>,
    val likedGifts: List<String>,
    val hatedGifts: List<String>,
    val location: String,
    val description: String,
    val imageUrl: String? = null
)
