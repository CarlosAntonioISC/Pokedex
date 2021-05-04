package com.example.pokedex.view.reclycerView

import com.example.pokedex.domain.models.Pokemon

interface OnPokemonClickListener {
    fun onPokemonClick(item: Pokemon)
}