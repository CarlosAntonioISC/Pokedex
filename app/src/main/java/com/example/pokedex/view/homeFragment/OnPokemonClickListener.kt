package com.example.pokedex.view.homeFragment

import com.example.pokedex.domain.models.Pokemon

interface OnPokemonClickListener {
    fun onClick(item: Pokemon)
}