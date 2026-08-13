package com.example.data.database

import com.example.data.model.EquipmentItem
import com.example.data.model.MinesFloorRange
import com.example.data.model.MonsterDrop
import com.example.data.model.MonsterEntity
import com.example.data.model.NpcGiftInfo
import com.example.data.model.WeaponItem

object CompendiumData {

    // =========================================================================
    // 1. MONSTRUOS Y DROPEOS
    // =========================================================================
    val monsters = listOf(
        MonsterEntity(
            id = "mon_slime_green",
            nameEs = "Slime Verde",
            nameEn = "Green Slime",
            location = "Mina (Pisos 1 - 40)",
            hp = 24,
            damage = 6,
            defense = 0,
            speed = 2,
            drops = listOf(
                MonsterDrop("Babas (Sap)", "75%"),
                MonsterDrop("Gelatina de Alga (Algae)", "15%"),
                MonsterDrop("Huevo de Slime Verde", "1%"),
                MonsterDrop("Cuarzo Básico", "0.5%"),
                MonsterDrop("Bota de Cuero", "0.1%")
            ),
            description = "El monstruo más común. Salta hacia ti cuando se acerca. Proporciona babas para la prensa de slimes."
        ),
        MonsterEntity(
            id = "mon_slime_frost",
            nameEs = "Slime Azul (Helado)",
            nameEn = "Frost Jelly",
            location = "Mina (Pisos 40 - 80)",
            hp = 106,
            damage = 15,
            defense = 2,
            speed = 2,
            drops = listOf(
                MonsterDrop("Babas", "75%"),
                MonsterDrop("Mineral de Hierro", "10%"),
                MonsterDrop("Huevo de Slime Azul", "1%"),
                MonsterDrop("Cuarzo Congelado", "2%"),
                MonsterDrop("Lágrima Helada", "0.5%")
            ),
            description = "Slime más resistente de los pisos helados. Puede causar estado de ralentización."
        ),
        MonsterEntity(
            id = "mon_slime_red",
            nameEs = "Slime Rojo (Lava)",
            nameEn = "Red Slime",
            location = "Mina (Pisos 80 - 120)",
            hp = 205,
            damage = 22,
            defense = 4,
            speed = 3,
            drops = listOf(
                MonsterDrop("Babas", "75%"),
                MonsterDrop("Mineral de Oro", "12%"),
                MonsterDrop("Cuarzo de Fuego", "3%"),
                MonsterDrop("Huevo de Slime Rojo", "1%"),
                MonsterDrop("Carbón", "10%")
            ),
            description = "Agresivo y veloz. Habita en las zonas volcánicas de la mina."
        ),
        MonsterEntity(
            id = "mon_slime_purple",
            nameEs = "Slime Púrpura",
            nameEn = "Purple Slime",
            location = "Cueva del Cráneo (Skull Cavern)",
            hp = 410,
            damage = 30,
            defense = 6,
            speed = 3,
            drops = listOf(
                MonsterDrop("Babas", "75%"),
                MonsterDrop("Mineral de Iridio", "15%"),
                MonsterDrop("Lingote de Iridio", "1.2%"),
                MonsterDrop("Huevo de Slime Púrpura", "1%"),
                MonsterDrop("Fragmento Prismático", "0.05%")
            ),
            description = "Sueltan valioso mineral de iridio y con suerte lingotes enteros o fragmentos prismáticos."
        ),
        MonsterEntity(
            id = "mon_bat",
            nameEs = "Murciélago de Cueva",
            nameEn = "Cave Bat",
            location = "Mina (Pisos 1 - 40)",
            hp = 24,
            damage = 7,
            defense = 1,
            speed = 3,
            drops = listOf(
                MonsterDrop("Ala de Murciélago", "60%"),
                MonsterDrop("Baya de Cueva", "10%"),
                MonsterDrop("Cuarzo", "0.5%")
            ),
            description = "Vuela atravesando obstáculos directamente hacia el jugador. Produce alas para hacer pararrayos."
        ),
        MonsterEntity(
            id = "mon_frost_bat",
            nameEs = "Murciélago Helado",
            nameEn = "Frost Bat",
            location = "Mina (Pisos 40 - 80)",
            hp = 36,
            damage = 12,
            defense = 2,
            speed = 3,
            drops = listOf(
                MonsterDrop("Ala de Murciélago", "60%"),
                MonsterDrop("Mena de Hierro", "10%"),
                MonsterDrop("Lágrima Helada", "1%")
            ),
            description = "Invasor nocturno de la cueva congelada."
        ),
        MonsterEntity(
            id = "mon_lava_bat",
            nameEs = "Murciélago de Magma",
            nameEn = "Lava Bat",
            location = "Mina (Pisos 80 - 120)",
            hp = 80,
            damage = 18,
            defense = 3,
            speed = 3,
            drops = listOf(
                MonsterDrop("Ala de Murciélago", "60%"),
                MonsterDrop("Mena de Oro", "10%"),
                MonsterDrop("Cuarzo de Fuego", "2%")
            ),
            description = "Más veloz y dañino en las profundidades de la mina."
        ),
        MonsterEntity(
            id = "mon_skeleton",
            nameEs = "Esqueleto",
            nameEn = "Skeleton",
            location = "Mina (Pisos 71 - 120)",
            hp = 140,
            damage = 25,
            defense = 3,
            speed = 2,
            drops = listOf(
                MonsterDrop("Huesos Prehistóricos", "10%"),
                MonsterDrop("Espada de Hueso", "0.5%"),
                MonsterDrop("Fragmento de Cráneo", "5%"),
                MonsterDrop("Leche Rara", "0.1%")
            ),
            description = "Ataca cuerpo a cuerpo y lanza huesos a distancia. Los esqueletos caen temporalmente al recibir golpes."
        ),
        MonsterEntity(
            id = "mon_shadow_brute",
            nameEs = "Bruto de las Sombras",
            nameEn = "Shadow Brute",
            location = "Mina (Pisos 80 - 120)",
            hp = 160,
            damage = 18,
            defense = 3,
            speed = 2,
            drops = listOf(
                MonsterDrop("Esencia Nula (Void Essence)", "75%"),
                MonsterDrop("Carbón", "15%"),
                MonsterDrop("Elixir de Vida", "1%"),
                MonsterDrop("Anillo de Luz", "0.2%")
            ),
            description = "Guerrero encapuchado de la oscuridad. Dropea esencia nula fundamental para la fabricación."
        ),
        MonsterEntity(
            id = "mon_shadow_shaman",
            nameEs = "Chamán de las Sombras",
            nameEn = "Shadow Shaman",
            location = "Mina (Pisos 80 - 120)",
            hp = 80,
            damage = 17,
            defense = 1,
            speed = 2,
            drops = listOf(
                MonsterDrop("Esencia Nula", "75%"),
                MonsterDrop("Esencia Solar", "10%"),
                MonsterDrop("Manzana", "2%"),
                MonsterDrop("Receta de Anillo", "0.1%")
            ),
            description = "Lanza proyectiles mágicos que reducen tu defensa en -8. Elimínalo con prioridad."
        ),
        MonsterEntity(
            id = "mon_serpent",
            nameEs = "Serpiente del Desierto",
            nameEn = "Serpent",
            location = "Cueva del Cráneo (Skull Cavern)",
            hp = 150,
            damage = 30,
            defense = 0,
            speed = 5,
            drops = listOf(
                MonsterDrop("Diente de Iridio (Iridium Ore)", "30%"),
                MonsterDrop("Comida Picante (Especial)", "8%"),
                MonsterDrop("Fragmento Prismático", "0.15%"),
                MonsterDrop("Bomba Mágica", "5%")
            ),
            description = "Extraordinariamente rápida. Vuela en línea recta haciendo gran daño. Mantén el ritmo con tu espada."
        ),
        MonsterEntity(
            id = "mon_mummy",
            nameEs = "Momia",
            nameEn = "Mummy",
            location = "Cueva del Cráneo",
            hp = 260,
            damage = 30,
            defense = 4,
            speed = 1,
            drops = listOf(
                MonsterDrop("Tela (Cloth)", "20%"),
                MonsterDrop("Mena de Iridio", "10%"),
                MonsterDrop("Bomba", "10%"),
                MonsterDrop("Omnigeoda", "5%"),
                MonsterDrop("Fragmento Prismático", "0.1%")
            ),
            description = "Tras dejarla en el suelo al atacarla, debes rematarla usando una BOMBA para destruirla definitivamente."
        ),
        MonsterEntity(
            id = "mon_dust_sprite",
            nameEs = "Duende del Polvo",
            nameEn = "Dust Sprite",
            location = "Mina (Pisos 40 - 79)",
            hp = 40,
            damage = 6,
            defense = 0,
            speed = 3,
            drops = listOf(
                MonsterDrop("Carbón (Coal)", "50%"),
                MonsterDrop("Cristal de Lágrima Helada", "2%"),
                MonsterDrop("Café", "1%")
            ),
            description = "Criaturas pequeñas en grupos. ¡Derrotar 500 te otorga el valioso Anillo del Ladrón en el Gremio de Cazadores!"
        ),
        MonsterEntity(
            id = "mon_rock_crab",
            nameEs = "Cangrejo de Roca",
            nameEn = "Rock Crab",
            location = "Mina (Pisos 1 - 40)",
            hp = 30,
            damage = 5,
            defense = 10,
            speed = 1,
            drops = listOf(
                MonsterDrop("Cangrejo", "25%"),
                MonsterDrop("Cherry Bomb", "10%"),
                MonsterDrop("Borrador de Roca", "5%")
            ),
            description = "Camuflado como piedra. Usa el pico para romper su caparazón o golpea cuando se mueva."
        ),
        MonsterEntity(
            id = "mon_pepper_rex",
            nameEs = "Dinosaurio Pepper Rex",
            nameEn = "Pepper Rex",
            location = "Pisos Prehistóricos (Cueva del Cráneo)",
            hp = 300,
            damage = 15,
            defense = 2,
            speed = 2,
            drops = listOf(
                MonsterDrop("Huevo de Dinosaurio", "10%"),
                MonsterDrop("Costilla Prehistórica", "10%"),
                MonsterDrop("Huesos Varios", "25%"),
                MonsterDrop("Carne de Dinosaurio", "50%")
            ),
            description = "Escupe fuego a larga distancia. Su huevo puede incubarse en tu gallinero para criar tu propio dinosaurio."
        )
    )

    // =========================================================================
    // 2. RECURSOS Y PISOS DE LA MINA
    // =========================================================================
    val mineRanges = listOf(
        MinesFloorRange(
            rangeName = "Pisos 1 - 39: Las Minas Superficiales",
            floorLevels = "Niveles 1 a 39",
            theme = "Tierra y Roca Natural",
            oresAndMinerals = listOf("Mineral de Cobre", "Cuarzo Básico", "Topacio", "Amatista", "Geoda Normal"),
            geodesAndContainers = listOf("Cajas de Madera", "Barriles", "Geoda Parda"),
            specialDrops = listOf("Casco de Minero (Piso 20+)", "Espada de Madera", "Zapatos de Cuero"),
            description = "Niveles iniciales ideal para conseguir cobre y carbón. El elevador se activa cada 5 pisos."
        ),
        MinesFloorRange(
            rangeName = "Pisos 40 - 79: Las Cavernas Heladas",
            floorLevels = "Niveles 40 a 79",
            theme = "Hielo, Nieve y Cristales Azulados",
            oresAndMinerals = listOf("Mineral de Hierro", "Cuarzo Congelado", "Lágrima Helada", "Aquamarina", "Jade"),
            geodesAndContainers = listOf("Geoda Congelada", "Barriles Helados"),
            specialDrops = listOf("Carbón abundante (Duendes del Polvo)", "Botas de Nieve", "Daga de Cristal"),
            description = "Zona helada donde abundan los duendes del polvo que dropean carbón a gran velocidad."
        ),
        MinesFloorRange(
            rangeName = "Pisos 80 - 120: Las Profundidades de Lava",
            floorLevels = "Niveles 80 a 120",
            theme = "Fuego, Magma y Roca Volcánica",
            oresAndMinerals = listOf("Mineral de Oro", "Cuarzo de Fuego", "Diamante", "Geoda de Magma"),
            geodesAndContainers = listOf("Geoda de Magma", "Cajas Calientes"),
            specialDrops = listOf("Llave de la Calavera (Piso 120)", "Espada de Lava (Tienda Gremio)", "Botas de Fuego"),
            description = "En el piso 120 obtendrás la Llave de la Calavera para desbloquear la Cueva del Cráneo en el Desierto."
        ),
        MinesFloorRange(
            rangeName = "Cueva del Cráneo (Skull Cavern)",
            floorLevels = "Pisos Infinitos (Desierto Calico)",
            theme = "Desertic / Iridio Profundo",
            oresAndMinerals = listOf("Mena de Iridio", "Fragmento Prismático", "Omnigeodas", "Baterías"),
            geodesAndContainers = listOf("Omnigeoda", "Cofres del Tesoro (Piso 10, 20, 50, etc.)"),
            specialDrops = listOf("Sombrero de Vaquero", "Estatua de la Fortuna", "Autosebrador"),
            description = "Cuanto más profundo bajes en la Cueva del Cráneo, mayor será la concentración de menas de Iridio y Fragmentos Prismáticos."
        ),
        MinesFloorRange(
            rangeName = "Mina del Volcán (Isla Jengibre)",
            floorLevels = "Pisos 1 a 10 (Isla Jengibre)",
            theme = "Lava Hirviente y Forja Central",
            oresAndMinerals = listOf("Ceniza Volcánica", "Diente de Dragón", "Nuez Dorada", "Shard Prismático"),
            geodesAndContainers = listOf("Cofres Raros del Volcán"),
            specialDrops = listOf("Encantamientos de Forja (Piso 10)", "Anillo Napalm", "Espada Neandertal"),
            description = "Contiene la Forja Volcánica en el nivel 10 para combinar anillos y encantar herramientas y armas con Fragmentos Prismáticos."
        )
    )

    // =========================================================================
    // 3. ARMAS Y EQUIPAMIENTO DE COMBATE
    // =========================================================================
    val weapons = listOf(
        WeaponItem("wpn_galaxy", "Espada Galáctica", "Galaxy Sword", "Espada", 13, 60, 80, 4, 2.0f, 0, 0, "Lleva un Fragmento Prismático al centro de los 3 pilares del Desierto."),
        WeaponItem("wpn_lava", "Katanas de Lava", "Lava Katana", "Espada", 10, 55, 64, 3, 1.5f, 0, 3, "Comprar en el Gremio de Cazadores por 25,000g tras llegar al piso 120."),
        WeaponItem("wpn_bone", "Espada de Hueso", "Bone Sword", "Espada", 5, 20, 30, 2, 1.2f, 0, 0, "Dropeo de Esqueletos o compra en el Gremio."),
        WeaponItem("wpn_wooden", "Espada de Madera", "Wooden Blade", "Espada", 1, 3, 7, 0, 1.0f, 0, 0, "Marmota inicial regalada por Marlon."),
        WeaponItem("wpn_galaxy_dagger", "Daga Galáctica", "Galaxy Dagger", "Daga", 13, 30, 40, 5, 5.0f, 0, 0, "Comprar en el Gremio tras desbloquear la Espada Galáctica."),
        WeaponItem("wpn_crystal_dagger", "Daga de Cristal", "Crystal Dagger", "Daga", 4, 18, 24, 3, 3.0f, 0, 0, "Cofre del piso 60 de la Mina."),
        WeaponItem("wpn_galaxy_hammer", "Mazo Galáctico", "Galaxy Hammer", "Mazo", 13, 70, 90, -2, 1.0f, 2, 0, "Comprar en el Gremio por 75,000g tras la Espada Galáctica."),
        WeaponItem("wpn_lead_maul", "Maza de Plomo", "Lead Maul", "Mazo", 6, 30, 45, -4, 1.0f, 4, 0, "Cofre del piso 80 de la Mina."),
        WeaponItem("wpn_slingshot", "Tirachinas Maestro", "Master Slingshot", "Tirachinas", 7, 20, 70, 0, 1.0f, 0, 0, "Cofre del piso 70 de la Mina. Usa munición de oro o explosiva.")
    )

    val equipment = listOf(
        EquipmentItem("eq_iridium_band", "Anillo de Iridio", "Iridium Band", "Anillo", "Emite luz (radio 10), atrae ítems magnéticamente (+2m) e incrementa el ataque un +10%.", 0, 0, "Fabricación (Nivel 9 Combate: 5 Lingotes Iridio, 50 Esencias Solares, 50 Nulas)."),
        EquipmentItem("eq_burglar_ring", "Anillo del Ladrón", "Burglar's Ring", "Anillo", "Duplica la probabilidad de dropeo de objetos al derrotar monstruos.", 0, 0, "Recompensa del Gremio por matar 500 Duendes del Polvo."),
        EquipmentItem("eq_vampire_ring", "Anillo de Vampiro", "Vampire Ring", "Anillo", "Restaura 2 Puntos de Vida (HP) por cada monstruo derrotado.", 0, 0, "Recompensa del Gremio por matar 200 Murciélagos."),
        EquipmentItem("eq_magnet_ring", "Anillo Magnetizado", "Magnet Ring", "Anillo", "Incrementa el radio de atracción de ítems en 2 casillas.", 0, 0, "Cofres de la mina o dropeo de monstruos."),
        EquipmentItem("eq_glow_ring", "Anillo Brillante", "Glow Ring", "Anillo", "Emite un halo de luz de 10 casillas alrededor del jugador.", 0, 0, "Cofre del piso 40 o dropeos."),
        EquipmentItem("eq_ruby_ring", "Anillo de Rubí", "Ruby Ring", "Anillo", "Aumenta el daño de ataque en un +10%.", 0, 0, "Comprar en el Gremio por 5,000g o cofres."),
        EquipmentItem("eq_napalm_ring", "Anillo Napalm", "Napalm Ring", "Anillo", "Los monstruos explotan al morir rompiendo piedras circundantes.", 0, 0, "Recompensa por eliminar 250 Serpientes."),
        EquipmentItem("eq_space_boots", "Calzado Espacial", "Space Boots", "Calzado", "Proporciona +4 Defensa y +4 Inmunidad contra estados alterados.", 4, 4, "Cofre del piso 110 de la Mina."),
        EquipmentItem("eq_fire_dragon_boots", "Botas de Fuego", "Firedragon Boots", "Calzado", "Proporciona +3 Defensa y +3 Inmunidad.", 3, 3, "Cofres del piso 80+ o Cueva del Cráneo."),
        EquipmentItem("eq_genie_boots", "Botas de Genio", "Genie Shoes", "Calzado", "Proporciona +1 Defensa y +6 Inmunidad máxima.", 1, 6, "Drops de la Cueva del Cráneo o Cajas del Volcán.")
    )

    // =========================================================================
    // 4. REGALOS FAVORITOS Y DATOS DE NPC
    // =========================================================================
    val npcs = listOf(
        // --- SOLTEROS Y SOLTERAS ---
        NpcGiftInfo(
            id = "npc_abigail",
            nameEs = "Abigail",
            nameEn = "Abigail",
            birthday = "Otoño 13",
            isCandidate = true,
            lovedGifts = listOf("Amatista", "Pastel de Mora", "Calabaza", "Pez Globo", "Sopa de Choclo"),
            likedGifts = listOf("Cuarzo", "Todas las Gemas", "Café", "Azafrán"),
            hatedGifts = listOf("Acebo", "Arcilla", "Babas"),
            location = "Tienda de Pierre (Pueblo Pelícano)",
            description = "Le encanta la aventura, tocar la batería y comer amatistas.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Abigail.png"
        ),
        NpcGiftInfo(
            id = "npc_alex",
            nameEs = "Alex",
            nameEn = "Alex",
            birthday = "Verano 13",
            isCandidate = true,
            lovedGifts = listOf("Cena Completa", "Plato de Salmón", "Huevo Dorado"),
            likedGifts = listOf("Todos los Huevos (excepto Vacío)", "Café"),
            hatedGifts = listOf("Acebo", "Materia Gris", "Cuarzo"),
            location = "Casa de George y Evelyn (Pueblo Pelícano)",
            description = "Apasionado del deporte y el fitness. Sueña con ser atleta profesional.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Alex.png"
        ),
        NpcGiftInfo(
            id = "npc_elliott",
            nameEs = "Elliott",
            nameEn = "Elliott",
            birthday = "Otoño 5",
            isCandidate = true,
            lovedGifts = listOf("Cangrejo", "Pluma de Pato", "Langosta", "Sopa de Mariscos", "Tinta de Calamar"),
            likedGifts = listOf("Vino", "Calamar", "Todas las Frutas de Árbol"),
            hatedGifts = listOf("Amaranto", "Cuarzo", "Narciso"),
            location = "Cabaña en la Playa",
            description = "Escritor poético que vive solo junto al mar.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Elliott.png"
        ),
        NpcGiftInfo(
            id = "npc_emily",
            nameEs = "Emily",
            nameEn = "Emily",
            birthday = "Primavera 27",
            isCandidate = true,
            lovedGifts = listOf("Amatista", "Aquamarina", "Rubí", "Esmeralda", "Jade", "Topacio", "Tela", "Lana"),
            likedGifts = listOf("Cuarzo", "Diente de León", "Narciso"),
            hatedGifts = listOf("Pescado frito", "Amapola"),
            location = "Casa de Emily y Haley / Salón Fruto del Estrellero",
            description = "Ama la moda, confeccionar ropa con su telar y las gemas espirituales.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Emily.png"
        ),
        NpcGiftInfo(
            id = "npc_haley",
            nameEs = "Haley",
            nameEn = "Haley",
            birthday = "Primavera 14",
            isCandidate = true,
            lovedGifts = listOf("Girasol", "Tarta de Coco", "Fruta de la Palma", "Tarta de Rosa"),
            likedGifts = listOf("Narciso", "Taza de Café"),
            hatedGifts = listOf("TODOS los Peces", "Fragmento Prismático", "Chirivía"),
            location = "Casa de Emily y Haley",
            description = "Fotógrafa a la que le encantan las flores brillantes y el coco dulce.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Haley.png"
        ),
        NpcGiftInfo(
            id = "npc_harvey",
            nameEs = "Harvey",
            nameEn = "Harvey",
            birthday = "Invierno 14",
            isCandidate = true,
            lovedGifts = listOf("Café", "Pepinillos", "Aceite de Trufa", "Vino", "Jugo de Tomate"),
            likedGifts = listOf("Todas las Frutas", "Chirivía", "Seta silvestre"),
            hatedGifts = listOf("Coral", "Queso de Cabra picante"),
            location = "Clínica médica del Pueblo",
            description = "El médico del pueblo que promueve la vida saludable y adora el buen café.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Harvey.png"
        ),
        NpcGiftInfo(
            id = "npc_leah",
            nameEs = "Leah",
            nameEn = "Leah",
            birthday = "Invierno 23",
            isCandidate = true,
            lovedGifts = listOf("Ensalada", "Queso de Cabra", "Trufa", "Vino", "Seta Mágica"),
            likedGifts = listOf("Todas las Bayas", "Diente de León", "Madera Driftwood"),
            hatedGifts = listOf("Pan", "Pizza", "Mermelada industrial"),
            location = "Cabaña en el Bosque Tizón",
            description = "Escultora que vive en la naturaleza y ama los productos orgánicos.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Leah.png"
        ),
        NpcGiftInfo(
            id = "npc_maru",
            nameEs = "Maru",
            nameEn = "Maru",
            birthday = "Verano 10",
            isCandidate = true,
            lovedGifts = listOf("Lingote de Iridio", "Batería", "Tarta de Fresa", "Pudín de Ruibarbo", "Diamante"),
            likedGifts = listOf("Lingote de Cobre", "Lingote de Hierro", "Cuarzo"),
            hatedGifts = listOf("Miel", "Pepinillos"),
            location = "Carpintería de Robin (Montaña)",
            description = "Inventora prodigio que trabaja como enfermera en la clínica.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Maru.png"
        ),
        NpcGiftInfo(
            id = "npc_penny",
            nameEs = "Penny",
            nameEn = "Penny",
            birthday = "Otoño 2",
            isCandidate = true,
            lovedGifts = listOf("Amapola", "Mermelada de Melón", "Tarta de Amapola", "Esmeralda", "Diamante"),
            likedGifts = listOf("Todas las Flores", "Leche", "Diente de León"),
            hatedGifts = listOf("Cerveza", "Vino", "Lúpulo", "Pez Globo"),
            location = "Remolque junto al río / Biblioteca",
            description = "Enseña a los niños del pueblo y sueña con formar una familia apacible.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Penny.png"
        ),
        NpcGiftInfo(
            id = "npc_sam",
            nameEs = "Sam",
            nameEn = "Sam",
            birthday = "Verano 17",
            isCandidate = true,
            lovedGifts = listOf("Pizza", "Tarta de Ojo de Buey", "Cactus", "Tigre de Fuego"),
            likedGifts = listOf("Todos los Huevos", "Refresco de Cola"),
            hatedGifts = listOf("Pescado", "Carbón", "Mena de Hierro"),
            location = "Casa de Jodi y Kent (Pueblo Pelícano)",
            description = "Músico enérgico al que le encanta el skateboarding y la pizza.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Sam.png"
        ),
        NpcGiftInfo(
            id = "npc_sebastian",
            nameEs = "Sebastian",
            nameEn = "Sebastian",
            birthday = "Invierno 10",
            isCandidate = true,
            lovedGifts = listOf("Lágrima Helada", "Sopa de Calamar", "Huevos de Vacío", "Obsidiana"),
            likedGifts = listOf("Cuarzo", "Café", "Pipa de Calabaza"),
            hatedGifts = listOf("Todas las Flores", "Arcilla", "Tortilla"),
            location = "Sótano de la Carpintería",
            description = "Programador freelance apasionado de las motocicletas y los ambientes oscuros.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Sebastian.png"
        ),
        NpcGiftInfo(
            id = "npc_shane",
            nameEs = "Shane",
            nameEn = "Shane",
            birthday = "Primavera 20",
            isCandidate = true,
            lovedGifts = listOf("Cerveza", "Pizza", "Pimiento Picante", "Tarta de Pimiento"),
            likedGifts = listOf("Todos los Huevos", "Frutas"),
            hatedGifts = listOf("Pepinillos", "Cuarzo"),
            location = "Casa de Marnie / JojaMart",
            description = "Trabaja en JojaMart y adora a las gallinas azules.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Shane.png"
        ),

        // --- OTROS ALDEANOS ---
        NpcGiftInfo(
            id = "npc_caroline",
            nameEs = "Caroline",
            nameEn = "Caroline",
            birthday = "Invierno 7",
            isCandidate = false,
            lovedGifts = listOf("Té Verde", "Tarta de Carambola", "Tarta de Pescado", "Narciso"),
            likedGifts = listOf("Todas las Flores", "Café"),
            hatedGifts = listOf("Mayonesa", "Seta de Cueva"),
            location = "Tienda de Pierre",
            description = "Esposa de Pierre. Le encanta tomar té en su invernadero privado.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Caroline.png"
        ),
        NpcGiftInfo(
            id = "npc_clint",
            nameEs = "Clint",
            nameEn = "Clint",
            birthday = "Invierno 26",
            isCandidate = false,
            lovedGifts = listOf("Amatista", "Aquamarina", "Esmeralda", "Rubí", "Topacio", "Jade", "Lingote de Iridio", "Alcachofa Frita"),
            likedGifts = listOf("Lingote de Cobre", "Lingote de Hierro"),
            hatedGifts = listOf("Todas las Flores", "Amapola"),
            location = "Herrería de Pelícano",
            description = "El herrero local. Rompe geodas y mejora tus herramientas de trabajo.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Clint.png"
        ),
        NpcGiftInfo(
            id = "npc_demetrius",
            nameEs = "Demetrius",
            nameEn = "Demetrius",
            birthday = "Verano 19",
            isCandidate = false,
            lovedGifts = listOf("Fresa", "Sopa de Pescado", "Arroz con Leche", "Helado"),
            likedGifts = listOf("Todas las Frutas", "Huevos"),
            hatedGifts = listOf("Acebo", "Lúpulo"),
            location = "Carpintería de Robin",
            description = "Científico que estudia la fauna y flora local en su laboratorio.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Demetrius.png"
        ),
        NpcGiftInfo(
            id = "npc_dwarf",
            nameEs = "Enano",
            nameEn = "Dwarf",
            birthday = "Verano 22",
            isCandidate = false,
            lovedGifts = listOf("Amatista", "Aquamarina", "Esmeralda", "Jade", "Rubí", "Topacio", "Lava Pendiente"),
            likedGifts = listOf("Cuarzo", "Pergamino Enano"),
            hatedGifts = listOf("Basura", "Leche"),
            location = "Mina (Entrada Este)",
            description = "Habitante subterráneo. Necesitas los 4 pergaminos enanos para entender su idioma.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Dwarf.png"
        ),
        NpcGiftInfo(
            id = "npc_evelyn",
            nameEs = "Evelyn",
            nameEn = "Evelyn",
            birthday = "Invierno 20",
            isCandidate = false,
            lovedGifts = listOf("Chocolate", "Remolacha", "Tulipán", "Rosa de Hada", "Diamante"),
            likedGifts = listOf("Todas las Flores", "Leche"),
            hatedGifts = listOf("Pescado", "Ajo", "Picante"),
            location = "Casa de Evelyn y George",
            description = "Abuela de Alex. Le encanta la jardinería y hornear galletas para el pueblo.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Evelyn.png"
        ),
        NpcGiftInfo(
            id = "npc_george",
            nameEs = "George",
            nameEn = "George",
            birthday = "Otoño 24",
            isCandidate = false,
            lovedGifts = listOf("Puerro", "Champiñón Frito"),
            likedGifts = listOf("Delfinio", "Narciso"),
            hatedGifts = listOf("Flores silvestres", "Arcilla"),
            location = "Casa de Evelyn y George",
            description = "Abuelo de Alex. Cascarrabias pero entrañable una vez ganas su confianza.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/George.png"
        ),
        NpcGiftInfo(
            id = "npc_gus",
            nameEs = "Gus",
            nameEn = "Gus",
            birthday = "Verano 8",
            isCandidate = false,
            lovedGifts = listOf("Naranja", "Escargot", "Tarta de Pescado", "Diamante"),
            likedGifts = listOf("Todas las Frutas", "Café"),
            hatedGifts = listOf("Coliflor", "Babas"),
            location = "Salón Fruto del Estrellero",
            description = "Dueño del salón del pueblo. Amable cocinero que sirve comida a todos los vecinos.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Gus.png"
        ),
        NpcGiftInfo(
            id = "npc_jas",
            nameEs = "Jas",
            nameEn = "Jas",
            birthday = "Verano 4",
            isCandidate = false,
            lovedGifts = listOf("Rosa de Hada", "Ciruela de Hada", "Pudín de Ciruela"),
            likedGifts = listOf("Leche", "Miel", "Narciso"),
            hatedGifts = listOf("Café", "Cerveza", "Pescado", "Arcilla"),
            location = "Rancho de Marnie",
            description = "Niña tímida que vive en el rancho con su tía Marnie y su padrino Shane.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Jas.png"
        ),
        NpcGiftInfo(
            id = "npc_jodi",
            nameEs = "Jodi",
            nameEn = "Jodi",
            birthday = "Otoño 11",
            isCandidate = false,
            lovedGifts = listOf("Tarta de Chocolate", "Pancakes", "Verduras Salteadas", "Diamante"),
            likedGifts = listOf("Todas las Frutas", "Leche"),
            hatedGifts = listOf("Ajo", "Materia Gris"),
            location = "Casa de Jodi",
            description = "Madre dedicada de Sam y Vincent. Mantiene la casa ordenada.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Jodi.png"
        ),
        NpcGiftInfo(
            id = "npc_kent",
            nameEs = "Kent",
            nameEn = "Kent",
            birthday = "Primavera 4",
            isCandidate = false,
            lovedGifts = listOf("Avellana Tostada", "Comida Risotto", "Sombra de Ojo"),
            likedGifts = listOf("Todos los Huevos", "Cerveza"),
            hatedGifts = listOf("Palomitas de Maíz", "Tortilla"),
            location = "Casa de Jodi (Llega en el Año 2)",
            description = "Padre de Sam y marido de Jodi. Regresa de la guerra en el Año 2.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Kent.png"
        ),
        NpcGiftInfo(
            id = "npc_krobus",
            nameEs = "Krobus",
            nameEn = "Krobus",
            birthday = "Invierno 1",
            isCandidate = false,
            lovedGifts = listOf("Huevo de Vacío", "Calabaza de Vacío", "Diamante", "Iridio", "Pez Espada"),
            likedGifts = listOf("Esencia Solar", "Esencia Nula", "Cuarzo"),
            hatedGifts = listOf("Todas las Flores", "Pescado cocinado"),
            location = "Las Alcantarillas (Sewer)",
            description = "Amigable habitante de las sombras que vende ítems raros en las alcantarillas.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Krobus.png"
        ),
        NpcGiftInfo(
            id = "npc_leo",
            nameEs = "Leo",
            nameEn = "Leo",
            birthday = "Verano 26",
            isCandidate = false,
            lovedGifts = listOf("Mango", "Plátano", "Pluma de Pato", "Pastel de Pescado"),
            likedGifts = listOf("Nuez de Coco", "Frutas"),
            hatedGifts = listOf("Lúpulo", "Cerveza"),
            location = "Isla Jengibre / Casa del Árbol",
            description = "Niño criado por loros en Isla Jengibre que se traslada al pueblo.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Leo.png"
        ),
        NpcGiftInfo(
            id = "npc_lewis",
            nameEs = "Lewis",
            nameEn = "Lewis",
            birthday = "Primavera 7",
            isCandidate = false,
            lovedGifts = listOf("Tarta de Otoño", "Pimiento Picante", "Té Verde", "Sopa de Verduras"),
            likedGifts = listOf("Blueberry", "Cerveza"),
            hatedGifts = listOf("Acebo", "Arcilla"),
            location = "Casa del Alcalde",
            description = "El alcalde de Pueblo Pelícano durante más de 20 años.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Lewis.png"
        ),
        NpcGiftInfo(
            id = "npc_linus",
            nameEs = "Linus",
            nameEn = "Linus",
            birthday = "Invierno 8",
            isCandidate = false,
            lovedGifts = listOf("Ñame Asado", "Plato de Marisco", "Seta de Cueva", "Coco"),
            likedGifts = listOf("Todas las Frutas Silvestres", "Diente de León"),
            hatedGifts = listOf("Basura", "Piedra"),
            location = "Tienda de campaña en la Montaña",
            description = "Nómada ermitaño que vive feliz en sintonía con la naturaleza.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Linus.png"
        ),
        NpcGiftInfo(
            id = "npc_marnie",
            nameEs = "Marnie",
            nameEn = "Marnie",
            birthday = "Otoño 18",
            isCandidate = false,
            lovedGifts = listOf("Tarta de Calabaza", "Pastel de Fresa", "Diamante", "Huevos"),
            likedGifts = listOf("Leche", "Cuarzo"),
            hatedGifts = listOf("Arcilla", "Babas"),
            location = "Rancho de Marnie",
            description = "Cuida de los animales de granja y te vende ganado y suministros.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Marnie.png"
        ),
        NpcGiftInfo(
            id = "npc_pam",
            nameEs = "Pam",
            nameEn = "Pam",
            birthday = "Primavera 18",
            isCandidate = false,
            lovedGifts = listOf("Cerveza", "Hidromiel", "Chirivía", "Sopa de Chirivía", "Cactus"),
            likedGifts = listOf("Todas las Frutas", "Leche"),
            hatedGifts = listOf("Pulpo", "Pez Globo"),
            location = "Remolque / Autobús del Desierto",
            description = "Conductora del autobús del Desierto de Calico y habitual del salón.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Pam.png"
        ),
        NpcGiftInfo(
            id = "npc_pierre",
            nameEs = "Pierre",
            nameEn = "Pierre",
            birthday = "Primavera 26",
            isCandidate = false,
            lovedGifts = listOf("Plato de Mariscos", "Sopa de Calamar"),
            likedGifts = listOf("Todas las Cosechas", "Diente de León"),
            hatedGifts = listOf("Joja Cola", "Ajo"),
            location = "Tienda General de Pierre",
            description = "Dueño de la tienda de semillas local. Compite abiertamente con JojaMart.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Pierre.png"
        ),
        NpcGiftInfo(
            id = "npc_robin",
            nameEs = "Robin",
            nameEn = "Robin",
            birthday = "Otoño 21",
            isCandidate = false,
            lovedGifts = listOf("Melocotón", "Tarta de Manzana", "Espagetti"),
            likedGifts = listOf("Madera", "Frutas"),
            hatedGifts = listOf("Babas", "Materia Gris"),
            location = "Carpintería (Montaña)",
            description = "Carpintera del pueblo. Construye y mejora tus edificios de granja.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Robin.png"
        ),
        NpcGiftInfo(
            id = "npc_sandy",
            nameEs = "Sandy",
            nameEn = "Sandy",
            birthday = "Otoño 15",
            isCandidate = false,
            lovedGifts = listOf("Flor de Crocus", "Girasol", "Guisante Dulce", "Tarta de Manzana"),
            likedGifts = listOf("Cactus", "Fruta de la Palma"),
            hatedGifts = listOf("Acebo", "Basura"),
            location = "Tienda Oasis (Desierto de Calico)",
            description = "Regenta la tienda Oasis en el desierto y vende semillas exóticas.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Sandy.png"
        ),
        NpcGiftInfo(
            id = "npc_vincent",
            nameEs = "Vincent",
            nameEn = "Vincent",
            birthday = "Primavera 10",
            isCandidate = false,
            lovedGifts = listOf("Uvas", "Tarta de Mora", "Caramelo de Miel"),
            likedGifts = listOf("Cerezas", "Leche"),
            hatedGifts = listOf("Arcilla", "Café", "Pescado"),
            location = "Casa de Jodi",
            description = "El hijo pequeño de Jodi. Le encanta jugar con Jas y buscar bichos.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Vincent.png"
        ),
        NpcGiftInfo(
            id = "npc_willy",
            nameEs = "Willy",
            nameEn = "Willy",
            birthday = "Verano 24",
            isCandidate = false,
            lovedGifts = listOf("Bagre", "Esturión", "Diamante", "Sopa de Marisco", "Lingote de Iridio"),
            likedGifts = listOf("Todos los Peces", "Cerveza"),
            hatedGifts = listOf("Pan", "Ajo"),
            location = "Tienda de Pesca (Playa)",
            description = "Pescador experimentado. Vende cañas, cebos y accesorios de pesca.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Willy.png"
        ),
        NpcGiftInfo(
            id = "npc_wizard",
            nameEs = "Mago (Rasmodius)",
            nameEn = "Wizard",
            birthday = "Invierno 17",
            isCandidate = false,
            lovedGifts = listOf("Seta Solar", "Esencia Nula", "Esencia Solar", "Superpepinillo", "Fragmento Prismático"),
            likedGifts = listOf("Todas las Gemas", "Cuarzo"),
            hatedGifts = listOf("Basura", "Arcilla"),
            location = "Torre del Mago (Oeste del Bosque)",
            description = "Místico erudito que estudia las fuerzas arcanas de Stardew Valley.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Wizard.png"
        ),

        // --- ESPECIALES ---
        NpcGiftInfo(
            id = "npc_gunther",
            nameEs = "Gunther",
            nameEn = "Gunther",
            birthday = "Desconocido",
            isCandidate = false,
            lovedGifts = listOf("Donaciones al Museo"),
            likedGifts = emptyList(),
            hatedGifts = emptyList(),
            location = "Museo y Biblioteca de Pelícano",
            description = "Curador del museo. Te recompensa por donar minerales y artefactos.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Gunther.png"
        ),
        NpcGiftInfo(
            id = "npc_marlon",
            nameEs = "Marlon",
            nameEn = "Marlon",
            birthday = "Desconocido",
            isCandidate = false,
            lovedGifts = listOf("Tarta de Raíces", "Sopa de Espada"),
            likedGifts = emptyList(),
            hatedGifts = emptyList(),
            location = "Gremio de Aventureros",
            description = "Líder del Gremio de Aventureros. Vende espadas y recompensa tus cacerías.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Marlon.png"
        ),
        NpcGiftInfo(
            id = "npc_morris",
            nameEs = "Morris",
            nameEn = "Morris",
            birthday = "Desconocido",
            isCandidate = false,
            lovedGifts = emptyList(),
            likedGifts = emptyList(),
            hatedGifts = emptyList(),
            location = "Supermercado JojaMart",
            description = "Gerente local de JojaMart. Ofrece proyectos comunitarios mercantilizados.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Morris.png"
        ),
        NpcGiftInfo(
            id = "npc_mr_qi",
            nameEs = "Sr. Qi",
            nameEn = "Mr. Qi",
            birthday = "Desconocido",
            isCandidate = false,
            lovedGifts = emptyList(),
            likedGifts = emptyList(),
            hatedGifts = emptyList(),
            location = "Club de Calico / Nogal en Isla Jengibre",
            description = "Misteriosa figura que organiza desafíos especiales en todo el archipiélago.",
            imageUrl = "https://es.stardewvalleywiki.com/Especial:FilePath/Mr._Qi.png"
        )
    )
}
