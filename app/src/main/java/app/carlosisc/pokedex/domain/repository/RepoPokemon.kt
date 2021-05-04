package app.carlosisc.pokedex.domain.repository

import app.carlosisc.pokedex.domain.models.entities.PokemonEntity
import app.carlosisc.pokedex.domain.models.PokemonFullInfo
import app.carlosisc.pokedex.domain.models.Pokemon
import app.carlosisc.pokedex.utils.Resource

interface RepoPokemon {
    suspend fun getPokemonList(): Resource<MutableList<Pokemon>>
    suspend fun getPokemonFullInfo(name: String): Resource<PokemonFullInfo>
    suspend fun savePokemon(pokemonEntity: PokemonEntity)
    suspend fun deletePokemon(id: Int)
    suspend fun getFavoritesPokemon(): Resource<MutableList<PokemonEntity>>
    suspend fun getFavoritePokemonById(id: Int): Resource<PokemonEntity>

}