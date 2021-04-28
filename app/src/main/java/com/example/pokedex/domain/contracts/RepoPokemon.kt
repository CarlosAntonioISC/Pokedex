package com.example.pokedex.domain.contracts

import com.example.pokedex.domain.models.PokemonInfo
import com.example.pokedex.utils.Resource

interface RepoPokemon {
    suspend fun getPokemonList(): Resource<MutableList<PokemonInfo>>
}