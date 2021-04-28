package com.example.pokedex.view.homeFragment

import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.models.PokemonInfo

interface OnPokemonClickListener {
    fun onClick(item: PokemonInfo)
}