package com.example.pokedex.domain.data

import com.example.pokedex.domain.contracts.DataSource
import com.example.pokedex.domain.entities.PokemonEntity
import com.example.pokedex.domain.models.PokemonFullInfo
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.utils.AppDatabase
import com.example.pokedex.utils.Resource
import com.example.pokedex.utils.RetrofitClient

class DataSourceImpl(private val appDatabase: AppDatabase): DataSource {

    override suspend fun getPokemonListToAPI(): Resource<MutableList<Pokemon>> {
        val pokemonListResponse = RetrofitClient.webService.getPokemonList(limit = 1118, offset = 0)

        //get the pokemon image in another api passing the pokemon id
        pokemonListResponse.results.forEach { pokeInfo ->
            val url = pokeInfo.url.split("/")
            val id = url[url.size - 2]
            pokeInfo.id = id.toInt()
            pokeInfo.image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
        }

        return Resource.Success(
                pokemonListResponse.results
        )
    }

    override suspend fun getPokemonFullInfoToAPI(name: String): Resource<PokemonFullInfo> {
        val result = RetrofitClient.webService.getPokemonByName(name)
        return Resource.Success(result)
    }

    override suspend fun savePokemonIntoRoom(pokemonEntity: PokemonEntity) {
        appDatabase.pokemonDAO().insertFavoritePokemon(pokemonEntity)
    }

    override suspend fun getFavoritesPokemon(): Resource<MutableList<PokemonEntity>> {
        return Resource.Success(appDatabase.pokemonDAO().getFavoritesPokemons())
    }
}