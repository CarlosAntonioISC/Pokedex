package app.carlosisc.pokedex.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import app.carlosisc.pokedex.domain.repository.RepoPokemon
import app.carlosisc.pokedex.utils.Resource
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