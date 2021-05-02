package com.example.pokedex.domain.models
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * classes used to get the data from the api on the endpoint
 * https://pokeapi.co/api/v2/pokemon?limit=100&offset=200
*/

data class PokemonListResponse (
        val count: Long,
        val next: String,
        val previous: Any? = null,
        val results: MutableList<PokemonInfo>
)

@Parcelize
data class PokemonInfo (
        val name: String = "",
        val url: String = "",
        var image: String = "",
        var color: Int = 0,
        var isRandom: Boolean = true
): Parcelable
