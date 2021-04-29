package com.example.pokedex.domain.models
import com.google.gson.annotations.SerializedName

data class Pokemon (
        val abilities: List<Ability>,
        val id: Int,
        val moves: List<Moves>,
        val sprites: Sprites,
        val types: List<Type>,
        val weight: Long
)

data class Moves(val move: Move)

data class Move(val name: String)

data class Ability (
        val ability: Species,
        @SerializedName("is_hidden")
        val isHidden: Boolean,
        val slot: Long
)

data class Species (
        val name: String,
        val url: String
)



data class Sprites (
        @SerializedName("back_default")
        val backDefault: String,

        @SerializedName("back_shiny")
        val backShiny: String,

        @SerializedName("front_default")
        val frontDefault: String,

        @SerializedName("front_shiny")
        val frontShiny: String,
)


data class Type (
        val slot: Long,
        val type: Species
)
