package com.example.pokedex.domain.repository

import com.example.pokedex.domain.contracts.DataSource
import com.example.pokedex.domain.contracts.RepoPokemon
import com.example.pokedex.domain.entities.PokemonEntity
import com.example.pokedex.domain.models.PokemonFullInfo
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.utils.Resource

class RepoPokemonImpl(private val dataSource: DataSource): RepoPokemon{

    override suspend fun getPokemonList(): Resource<MutableList<Pokemon>> {
        return dataSource.getPokemonListToAPI()
    }
    override suspend fun getPokemonFullInfo(name: String): Resource<PokemonFullInfo> {
        return dataSource.getPokemonFullInfoToAPI(name)
    }

    override suspend fun getFavoritesPokemon(): Resource<MutableList<PokemonEntity>> {
        return dataSource.getFavoritesPokemon()
    }
    override suspend fun savePokemon(pokemonEntity: PokemonEntity) {
        dataSource.savePokemonIntoRoom(pokemonEntity)
    }

}
