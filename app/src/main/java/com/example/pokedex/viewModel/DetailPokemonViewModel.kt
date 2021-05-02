package com.example.pokedex.viewModel

import androidx.lifecycle.*
import com.example.pokedex.domain.contracts.RepoPokemon
import com.example.pokedex.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class DetailPokemonViewModel(private val repo: RepoPokemon): ViewModel() {

    private val  namePokemon = MutableLiveData<String>()

    fun setName(name: String) {
        namePokemon.value = name
    }

    val getPokemonInfo = namePokemon.distinctUntilChanged().switchMap {
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(repo.getPokemonFullInfo(it))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }
}