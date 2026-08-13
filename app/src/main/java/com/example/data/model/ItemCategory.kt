package com.example.data.model

enum class ItemCategory(val displayName: String, val iconName: String) {
    CROP("Cultivos", "grass"),
    FISH("Peces", "phishing"),
    FORAGE("Forraje", "nature"),
    MINERAL("Minerales", "diamond"),
    ARTISAN("Artesanales", "wine_bar"),
    ANIMAL("Animales", "egg"),
    ARTIFACT("Artefactos", "auto_awesome"),
    COOKING("Cocina", "restaurant"),
    TOOL("Herramientas", "construction"),
    WEAPON("Armas", "swords"),
    CRAFTING("Fabricación", "handyman"),
    HAT("Sombreros", "face"),
    BOOTS("Calzado", "hiking"),
    RING("Anillos", "circle"),
    BAIT_TACKLE("Cebo y Aparejos", "phishing"),
    TREE("Árboles", "forest"),
    FERTILIZER("Fertilizantes", "eco"),
    FURNITURE("Muebles", "chair"),
    BOOK("Libros", "menu_book"),
    SECRET_NOTE("Notas Secretas", "description");

    companion object {
        fun fromString(value: String): ItemCategory {
            return values().firstOrNull { 
                it.name.equals(value, ignoreCase = true) || 
                it.displayName.equals(value, ignoreCase = true) 
            } ?: CROP
        }
    }
}
