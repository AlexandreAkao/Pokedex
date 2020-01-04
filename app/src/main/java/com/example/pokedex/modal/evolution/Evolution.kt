package com.example.pokedex.modal.evolution

import com.example.pokedex.modal.Name_Url

data class Evolution(
    val chain: EvolvesTo
)

data class EvolvesTo(
    val species: Name_Url,
    val evolves_to: List<EvolvesTo>
)