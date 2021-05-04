package com.example.pokedex.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.pokedex.domain.repository.RepoPokemon
import com.example.pokedex.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class FavoritesPokemonViewModel(private val repo: RepoPokemon): ViewModel() {

    fun getFavoritesPokemon() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getFavoritesPokemon())
        }
        catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}