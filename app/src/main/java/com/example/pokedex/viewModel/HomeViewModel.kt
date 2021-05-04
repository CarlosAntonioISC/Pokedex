package com.example.pokedex.viewModel

import androidx.lifecycle.*
import com.example.pokedex.domain.repository.RepoPokemon
import com.example.pokedex.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class HomeViewModel(private val repo: RepoPokemon): ViewModel() {


    val getPokemonList = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getPokemonList())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}