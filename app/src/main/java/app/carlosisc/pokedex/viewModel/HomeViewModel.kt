package app.carlosisc.pokedex.viewModel

import androidx.lifecycle.*
import app.carlosisc.pokedex.domain.repository.RepoPokemon
import app.carlosisc.pokedex.utils.Resource
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