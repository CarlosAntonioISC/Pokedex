package app.carlosisc.pokedex.domain.models

import com.google.gson.annotations.SerializedName

data class PokemonFullInfo (
        val abilities: List<Ability>,
        val forms: List<Form>,
        val id: Int,
        val moves: List<Moves>,
        val sprites: Sprites,
        val types: List<Type>,
        val weight: Long,
)

data class Moves(val move: Move)

data class Move(val name: String)

data class Ability (
        val ability: Species,
        @SerializedName("is_hidden")
        val isHidden: Boolean,
        val slot: Long
)

data class Form (
        val name: String,
        val url: String
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

        val other: Other
)

data class Other (
        @SerializedName("official-artwork")
        val officialArtwork: OfficialArtwork
)

data class OfficialArtwork (
        @SerializedName("front_default")
        val image: String
)



data class Type (
        val slot: Long,
        val type: Species
)
