package app.carlosisc.pokedex.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.carlosisc.pokedex.domain.repository.RepoPokemon

class VMFactory(private val repo: RepoPokemon): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RepoPokemon::class.java).newInstance(repo)
    }
}