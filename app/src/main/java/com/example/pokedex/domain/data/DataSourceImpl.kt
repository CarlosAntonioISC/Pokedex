package com.example.pokedex.domain.data

import android.util.Log
import com.example.pokedex.domain.contracts.DataSource
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.utils.Resource
import com.example.pokedex.utils.RetrofitClient

class DataSourceImpl: DataSource {

    override suspend fun getListPokemon(): Resource<List<Pokemon>> {

        val result = RetrofitClient.webSerive.getPokemons(100,0)
        Log.d("Resultado", result.toString())

        return Resource.Success(listOf(
                Pokemon("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"),
                Pokemon("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png"),
                Pokemon("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png"),
                Pokemon("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png"),
                Pokemon("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/5.png"),
                Pokemon("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/6.png"),
                Pokemon("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/7.png"),
                Pokemon("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/8.png"),
                Pokemon("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/9.png"),
                Pokemon("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/10.png")
        ))
    }

}