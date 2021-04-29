package com.example.pokedex.domain.data

import com.example.pokedex.domain.contracts.DataSource
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.models.PokemonInfo
import com.example.pokedex.utils.Resource
import com.example.pokedex.utils.RetrofitClient

class DataSourceImpl: DataSource {

    override suspend fun getPokemonListToAPI(): Resource<MutableList<PokemonInfo>> {

        val pokemonListResponse = RetrofitClient.webService.getPokemonList(limit = 1118, offset = 0)

        //get the pokemon image in another api passing the pokemon id
        pokemonListResponse.results.forEach { pokeInfo ->
            val url = pokeInfo.url.split("/")
            val id = url[url.size - 2]
            pokeInfo.image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
        }

        return Resource.Success(
                pokemonListResponse.results
        )
    }

    override suspend fun getPokemonFullInfoToAPI(name: String): Resource<Pokemon> {

        val result = RetrofitClient.webService.getPokemonByName(name)
        return Resource.Success(result)
    }
}