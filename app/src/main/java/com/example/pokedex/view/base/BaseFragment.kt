package com.example.pokedex.view.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pokedex.R
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.view.reclycerView.OnPokemonClickListener

open class BaseFragment(layout: Int) : Fragment(layout),
    OnPokemonClickListener {

    override fun onPokemonClick(item: Pokemon) {
        val bundle = Bundle()
        bundle.putParcelable("pokemon", item)
        findNavController().navigate(R.id.detail_pokemon_fragment, bundle)
    }
}