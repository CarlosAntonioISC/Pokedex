package com.example.pokedex.view.detailPokemon


import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentDetailPokemonBinding
import com.example.pokedex.domain.data.DataSourceImpl
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.models.PokemonInfo
import com.example.pokedex.domain.repository.RepoPokemonImpl
import com.example.pokedex.utils.Drawable.overrideColor
import com.example.pokedex.utils.Resource
import com.example.pokedex.viewModel.DetailPokemonViewModel
import com.example.pokedex.viewModel.VMFactory
import com.google.android.material.snackbar.Snackbar
import java.util.*


class DetailPokemonFragment : Fragment(R.layout.fragment_detail_pokemon) {

    private lateinit var binding: FragmentDetailPokemonBinding
    private lateinit var pokemon: PokemonInfo
    private val viewModel by viewModels<DetailPokemonViewModel> { VMFactory(RepoPokemonImpl(DataSourceImpl())) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            pokemon = it.getParcelable("pokemon") ?: PokemonInfo()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailPokemonBinding.bind(view)
        viewModel.setName(pokemon.name)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getPokemonInfo.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                when (it) {
                    is Resource.Loading -> {
                        binding.includeLogo.flProgressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.includeLogo.flProgressBar.visibility = View.GONE
                        initUI(it.data)
                    }
                    is Resource.Failure -> {
                        binding.includeLogo.flProgressBar.visibility = View.GONE
                        Toast.makeText(context, "Fallo", Toast.LENGTH_SHORT).show()

                    }
                }
        })
    }

    private fun initUI(pokemonFullInfo: Pokemon) {

        with(binding) {

            tvIdPokemon.text = "#".plus(pokemonFullInfo.id.toString())

            tvNamePokemon.text = pokemon.name.capitalize(Locale.US)
            ivOvalPokemon.background.overrideColor(pokemon.color)

            val types = pokemonFullInfo.types.joinToString(separator = ", ", transform = {it.type.name})
            tvType.text = types

            tvWeight.text = pokemonFullInfo.weight.toString()

            loadImage(pokemonFullInfo.sprites.backDefault, ivSprite1)
            loadImage(pokemonFullInfo.sprites.backShiny, ivSprite2)
            loadImage(pokemonFullInfo.sprites.frontDefault, ivSprite3)
            loadImage(pokemonFullInfo.sprites.frontShiny, ivSprite4)

            val abilities = pokemonFullInfo.abilities.joinToString(separator = ", ", transform = {it.ability.name})
            tvAbilities.text = abilities

            val moves = pokemonFullInfo.moves.joinToString(separator = ", ", transform = {it.move.name})
            tvMoves.text = moves
        }

        loadImage(pokemon.image, binding.ivPokemon)
    }


    private fun loadImage(url: String, imageView: ImageView){
        url.let {
            Glide.with(this).load(it).centerCrop().into(imageView)
        }
    }
}
