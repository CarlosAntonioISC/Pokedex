package com.example.pokedex.domain.contracts

import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.models.PokemonInfo
import com.example.pokedex.utils.Resource

interface DataSource {
    suspend fun getPokemonListToAPI(): Resource<MutableList<PokemonInfo>>
}



