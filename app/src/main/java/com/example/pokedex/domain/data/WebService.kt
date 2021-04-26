package com.example.pokedex.domain.data

import com.example.pokedex.domain.models.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    //pokemon?limit=100&offset=0
    @GET("pokemon")
    suspend fun getPokemons(@Query("limit") limit: Int, @Query("offset") offset: Int): PokemonListResponse
}