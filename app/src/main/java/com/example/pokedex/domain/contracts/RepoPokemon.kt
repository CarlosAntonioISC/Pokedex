package com.example.pokedex.domain.contracts

import com.example.pokedex.domain.entities.PokemonEntity
import com.example.pokedex.domain.models.PokemonFullInfo
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.utils.Resource

interface RepoPokemon {
    suspend fun getPokemonList(): Resource<MutableList<Pokemon>>
    suspend fun getPokemonFullInfo(name: String): Resource<PokemonFullInfo>
    suspend fun savePokemon(pokemonEntity: PokemonEntity)
    suspend fun deletePokemon(id: Int)
    suspend fun getFavoritesPokemon(): Resource<MutableList<PokemonEntity>>
    suspend fun getFavoritePokemonById(id: Int): Resource<PokemonEntity>

}