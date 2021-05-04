package app.carlosisc.pokedex.domain.datasource

import app.carlosisc.pokedex.domain.models.entities.PokemonEntity
import app.carlosisc.pokedex.domain.models.PokemonFullInfo
import app.carlosisc.pokedex.domain.models.Pokemon
import app.carlosisc.pokedex.utils.Resource

interface DataSource {
    suspend fun getPokemonListToAPI(): Resource<MutableList<Pokemon>>
    suspend fun getPokemonFullInfoToAPI(name: String): Resource<PokemonFullInfo>
    suspend fun savePokemon(pokemonEntity: PokemonEntity)
    suspend fun deletePokemon(id: Int)
    suspend fun getFavoritesPokemon(): Resource<MutableList<PokemonEntity>>
    suspend fun getFavoritePokemonById(id: Int): Resource<PokemonEntity>
}



