package com.example.data.model

enum class Season(val displayName: String, val colorHex: Long) {
    SPRING("Primavera", 0xFF68B04D),
    SUMMER("Verano", 0xFFE08D3C),
    FALL("Otoño", 0xFFC25D38),
    WINTER("Invierno", 0xFF5B92E5),
    ALL("Todas", 0xFF8A64D6),
    GREENHOUSE("Invernadero", 0xFF319B72);

    companion object {
        fun fromString(value: String): Season {
            return values().firstOrNull { it.name.equals(value, ignoreCase = true) } ?: SPRING
        }
    }
}
