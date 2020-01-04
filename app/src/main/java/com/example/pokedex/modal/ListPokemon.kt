package com.example.pokedex.modal

data class ListPokemon(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Name_Url>
)