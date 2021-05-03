package com.example.pokedex.domain.contracts

import com.example.pokedex.domain.entities.PokemonEntity
import com.example.pokedex.domain.models.PokemonFullInfo
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.utils.Resource

interface DataSource {
    suspend fun getPokemonListToAPI(): Resource<MutableList<Pokemon>>
    suspend fun getPokemonFullInfoToAPI(name: String): Resource<PokemonFullInfo>
    suspend fun savePokemon(pokemonEntity: PokemonEntity)
    suspend fun deletePokemon(id: Int)
    suspend fun getFavoritesPokemon(): Resource<MutableList<PokemonEntity>>
    suspend fun getFavoritePokemonById(id: Int): Resource<PokemonEntity>
}



