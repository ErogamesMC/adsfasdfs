package com.example.data.model

data class SeasonEvent(
    val day: Int,
    val season: Season,
    val title: String,
    val type: EventType,
    val description: String = ""
)

enum class EventType(val label: String) {
    FESTIVAL("Festival"),
    BIRTHDAY("Cumpleaños"),
    CROP_DEADLINE("Sugerencia de Siembra"),
    USER_NOTE("Nota Personal")
}
