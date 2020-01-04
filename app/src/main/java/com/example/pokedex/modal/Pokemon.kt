package com.example.pokedex.modal

data class Pokemon(
    val abilities: Array<Abilities>,
    val base_experience: Int,
    val form: Array<Name_Url>,
    val game_indices: Array<Game_indices>,
    val height: Int,
    val held_items: Array<Held_items>,
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val moves: Array<Moves>,
    val name: String,
    val order: Int,
    val species: Name_Url,
    val sprites: Sprites,
    val stats: Array<Stats>,
    val types: Array<Types>,
    val weight: Int
)