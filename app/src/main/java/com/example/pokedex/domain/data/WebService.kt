package com.example.pokedex.domain.data

import com.example.pokedex.domain.models.PokemonFullInfo
import com.example.pokedex.domain.models.PokemonListResponse
import retrofit2.http.*

interface WebService {

    //pokemon?limit=100&offset=0
    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int, @Query("offset") offset: Int): PokemonListResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name") name:String): PokemonFullInfo
}