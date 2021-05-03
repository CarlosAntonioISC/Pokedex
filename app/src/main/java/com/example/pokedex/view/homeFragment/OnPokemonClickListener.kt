package com.example.pokedex.view.homeFragment

import com.example.pokedex.domain.models.Pokemon

interface OnPokemonClickListener {
    fun onPokemonClick(item: Pokemon)
}