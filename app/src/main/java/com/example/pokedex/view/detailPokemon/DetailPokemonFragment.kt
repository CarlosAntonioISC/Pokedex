package com.example.pokedex.view.detailPokemon


import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
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
import java.util.*


/**
 * This fragment can be used by three destinations,
    from the recycler view with the OnClick action in the home fragment,
    from the searchView in the home fragment
    and from the option of the bottom app bar 'Pokemon'
 */

class DetailPokemonFragment : Fragment(R.layout.fragment_detail_pokemon) {

    private lateinit var binding: FragmentDetailPokemonBinding
    private lateinit var pokemon: PokemonInfo
    private val viewModel by viewModels<DetailPokemonViewModel> { VMFactory(RepoPokemonImpl(DataSourceImpl())) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * If the arguments o fragments are null,
         * get a random pokemon between 1 and 898 from API because bottom nav bar item selected was
         * 'Pokemon'
         */
        if (arguments != null) {
            requireArguments().let {
                pokemon = it.getParcelable("pokemon") ?: PokemonInfo()
            }
        }else {
            pokemon = PokemonInfo(name = (1..898).random().toString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailPokemonBinding.bind(view)

        // add pokemon name to viewModel
        viewModel.setName(pokemon.name)

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getPokemonInfo.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.includeLogo.flProgressBar.visibility = View.VISIBLE
                    binding.nsInfoPokemon.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.includeLogo.flProgressBar.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                    binding.nsInfoPokemon.visibility = View.VISIBLE

                    initUI(it.data)
                }
                is Resource.Failure -> {
                    binding.includeLogo.flProgressBar.visibility = View.GONE
                    binding.nsInfoPokemon.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun initUI(pokemonFullInfo: Pokemon) {

        with(binding) {

            tvIdPokemon.text = "#".plus(pokemonFullInfo.id.toString())
            tvNamePokemon.text = pokemonFullInfo.forms[0].name.capitalize(Locale.ROOT)
            tvType.text = pokemonFullInfo.types.joinToString(separator = ", ", transform = { it.type.name })
            tvWeight.text = pokemonFullInfo.weight.toString()
            tvAbilities.text = pokemonFullInfo.abilities.joinToString(separator = ", ", transform = { it.ability.name })

            val moves = pokemonFullInfo.moves.joinToString(separator = ", ", transform = { it.move.name })
            tvMoves.text = if (moves.isEmpty()) "No moves" else moves


            setBackgroundColor(ivOvalPokemon, pokemonFullInfo)

            loadImage(pokemonFullInfo.sprites.backDefault, ivSprite1)
            loadImage(pokemonFullInfo.sprites.backShiny, ivSprite2)
            loadImage(pokemonFullInfo.sprites.frontDefault, ivSprite3)
            loadImage(pokemonFullInfo.sprites.frontShiny, ivSprite4)

        }

        val image = if (pokemon.image.isEmpty()) {
            pokemonFullInfo.sprites.other.officialArtwork.image
        } else {
            pokemon.image
        }

        loadImage(image, binding.ivPokemon)
    }

    private fun setBackgroundColor(ivOvalPokemon: ImageView, pokemonFullInfo: Pokemon) {
        if (pokemon.color != 0) {
            ivOvalPokemon.background.overrideColor(pokemon.color)
        } else {
            Glide.with(requireContext())
                    .asBitmap()
                    .load(pokemonFullInfo.sprites.other.officialArtwork.image)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            Palette.from(resource).generate {
                                val color = it?.getMutedColor(ContextCompat.getColor(requireContext(), R.color.default_background_color))
                                ivOvalPokemon.background.overrideColor(color!!)
                            }
                        }
                        override fun onLoadCleared(placeholder: Drawable?) {}
                    })
        }    }


    private fun loadImage(url: String, imageView: ImageView) {
        Glide.with(this).load(url).centerCrop().into(imageView)
    }
}
