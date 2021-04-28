package com.example.pokedex.view.detailPokemon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentDetailPokemonBinding
import com.example.pokedex.domain.models.PokemonInfo

class DetailPokemonFragment : Fragment(R.layout.fragment_detail_pokemon) {

    private lateinit var binding: FragmentDetailPokemonBinding
    private lateinit var pokemonName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            pokemonName = it.getString("pokemon") ?: "Pikachu"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailPokemonBinding.bind(view)

        initUI()
    }

    private fun initUI() {
        binding.tvNamePokemon.text = pokemonName
    }
}