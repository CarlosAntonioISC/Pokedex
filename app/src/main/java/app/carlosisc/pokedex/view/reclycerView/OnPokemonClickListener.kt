package app.carlosisc.pokedex.view.reclycerView

import app.carlosisc.pokedex.domain.models.Pokemon

interface OnPokemonClickListener {
    fun onPokemonClick(item: Pokemon)
}