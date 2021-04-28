package com.example.pokedex.domain.repository

import com.example.pokedex.domain.contracts.DataSource
import com.example.pokedex.domain.contracts.RepoPokemon
import com.example.pokedex.domain.models.PokemonInfo
import com.example.pokedex.utils.Resource

class RepoPokemonImpl(private val dataSource: DataSource): RepoPokemon{

    override suspend fun getPokemonList(): Resource<MutableList<PokemonInfo>> {
        return dataSource.getPokemonListToAPI()
    }
}