package com.example.pokedex.domain.models
import com.google.gson.annotations.SerializedName

data class Pokemon (
        val abilities: List<Ability>,
        val forms: List<Species>,
        val name: String,
        val order: Long,
        val sprites: Sprites,
        val stats: List<Stat>,
        val types: List<Type>,
        val weight: Long
)

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

        @SerializedName("back_female")
        val backFemale: String,

        @SerializedName("back_shiny")
        val backShiny: String,

        @SerializedName("back_shiny_female")
        val backShinyFemale: String,

        @SerializedName("front_default")
        val frontDefault: String,

        @SerializedName("front_female")
        val frontFemale: String,

        @SerializedName("front_shiny")
        val frontShiny: String,

        @SerializedName( "front_shiny_female")
        val frontShinyFemale: String,

        val other: Other? = null,
        val animated: Sprites? = null
)

data class Other (
        @SerializedName("dream_world")
        val dreamWorld: DreamWorld,

        @SerializedName("official-artwork")
        val officialArtwork: OfficialArtwork
)

data class DreamWorld (
        @SerializedName("front_default")
        val frontDefault: String,

        @SerializedName("front_female")
        val frontFemale: String? = null
)

data class OfficialArtwork (
        @SerializedName("front_default")
        val frontDefault: String
)

data class Stat (
        @SerializedName("base_stat")
        val baseStat: Long,

        val effort: Long,
        val stat: Species
)

data class Type (
        val slot: Long,
        val type: Species
)
