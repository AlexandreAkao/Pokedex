package com.example.pokedex.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {
//    object pokeApi {
//        private val retrofit = Retrofit.Builder()
//            .baseUrl("https://jsonplaceholder.typicode.com") //https://pokeapi.co/api/v2
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val pokeService: PokeService = retrofit.create<PokeService>(PokeService::class.java)
//    }

    object pokeApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        fun pokeService(): PokeService {
            return retrofit.create(PokeService::class.java)
        }
    }
}