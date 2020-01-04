package com.example.pokedex.services

import com.example.pokedex.modal.ListPokemon
import com.example.pokedex.modal.Pokemon
import com.example.pokedex.modal.evolution.Evolution
import com.example.pokedex.modal.specie.Specie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeService {
    @GET("pokemon")
    fun ListPokemon(@Query("offset") offset: Int = 0, @Query("limit") limit: Int = 20): Call<ListPokemon>

    @GET("pokemon/{pokemon}")
    fun pokemon(@Path("pokemon") pokemon: String): Call<Pokemon>

    @GET("pokemon-species/{id}")
    fun pokemonSpecie(@Path("id") id: String): Call<Specie>

    @GET("evolution-chain/{chain}")
    fun evolution(@Path("chain") chain: String): Call<Evolution>
}