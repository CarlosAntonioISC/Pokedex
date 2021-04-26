package com.example.pokedex.domain.models
import com.google.gson.annotations.SerializedName

data class Pokemon(@SerializedName("front_shiny") val image: String)

data class PokemonListResponse (
        val count: Long,
        val next: String,
        val previous: Any? = null,
        val results: List<Result>
)

data class Result (
        val name: String,
        val url: String
)
