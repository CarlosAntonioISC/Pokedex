package app.carlosisc.pokedex.ui.views


import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.palette.graphics.Palette
import app.carlosisc.pokedex.domain.models.entities.PokemonEntity
import app.carlosisc.pokedex.domain.models.PokemonFullInfo
import app.carlosisc.pokedex.domain.models.Pokemon
import app.carlosisc.pokedex.domain.repository.RepoPokemonImpl
import app.carlosisc.pokedex.domain.datasource.database.AppDatabase
import app.carlosisc.pokedex.utils.Drawable.overrideColor
import app.carlosisc.pokedex.utils.Resource
import app.carlosisc.pokedex.viewModel.DetailPokemonViewModel
import app.carlosisc.pokedex.viewModel.VMFactory
import androidx.lifecycle.Observer
import app.carlosisc.pokedex.domain.datasource.DataSourceImpl
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.carlosisc.pokedex.R
import com.carlosisc.pokedex.databinding.FragmentDetailPokemonBinding
import java.util.*


/**
 * This fragment can be used by three destinations,
from the recycler view with the OnClick action in the home fragment,
from the searchView in the home fragment
and from the option of the bottom app bar 'Pokemon'
 */

class DetailPokemonFragment : Fragment(R.layout.fragment_detail_pokemon) {

    private lateinit var binding: FragmentDetailPokemonBinding
    private lateinit var pokemon: Pokemon

    private val viewModel by viewModels<DetailPokemonViewModel> {
        VMFactory(
            RepoPokemonImpl(
                DataSourceImpl(
                    AppDatabase.getDatabase(requireActivity().applicationContext)
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * If the arguments o fragments are null,
         * get a random pokemon between 1 and 898 from API because bottom nav bar item selected was
         * 'Pokemon'
         */
        pokemon = if (arguments != null) {
            arguments?.getParcelable("pokemon") ?: Pokemon()
        } else {
            val id = (1..898).random()
            Pokemon(id = id, name = id.toString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailPokemonBinding.bind(view)

        // add pokemon name to viewModel
        viewModel.setName(pokemon.name.toLowerCase(Locale.ROOT))

        setupObservers()
    }

    private fun setupObservers() {

        viewModel.getFavoritePokemonById(pokemon.id).observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    //Data can be null
                    pokemon.isFavorite = if(it.data != null) true else false
                }
                else -> {
                    pokemon.isFavorite = false
                }
            }
        })

        viewModel.getPokemonInfo.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.includeLogo.flProgressBar.visibility = View.VISIBLE
                    binding.tvError.visibility = View.GONE
                    binding.nsInfoPokemon.visibility = View.GONE
                }
                is Resource.Success -> {
                    initUI(it.data)
                    binding.includeLogo.flProgressBar.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                    binding.nsInfoPokemon.visibility = View.VISIBLE
                }
                is Resource.Failure -> {
                    binding.includeLogo.flProgressBar.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                    binding.nsInfoPokemon.visibility = View.GONE
                }
            }
        })
    }

    private fun initUI(pokemonFullInfo: PokemonFullInfo) {
        with(binding) {

            cbFavorite.isChecked = pokemon.isFavorite

            val idPokemon = pokemonFullInfo.id
            val namePokemon = pokemonFullInfo.forms[0].name.capitalize()
            val types = pokemonFullInfo.types
            val weight = pokemonFullInfo.weight
            val abilities = pokemonFullInfo.abilities
            val moves = pokemonFullInfo.moves
            val image = pokemonFullInfo.sprites.other.officialArtwork.image
            val allMoves = moves.joinToString(
                separator = ", ",
                transform = { it.move.name })
            val color = setBackgroundColor(
                ivOvalPokemon,
                pokemonFullInfo.sprites.other.officialArtwork.image
            )

            tvIdPokemon.text = "#".plus(idPokemon.toString())
            tvNamePokemon.text = namePokemon
            tvType.text = types.joinToString(
                separator = ", ",
                transform = { it.type.name })
            tvWeight.text = weight.toString()
            tvAbilities.text = abilities.joinToString(
                separator = ", ",
                transform = { it.ability.name })

            tvMoves.text = if (allMoves.isEmpty()) "No moves" else allMoves

            loadImage(pokemonFullInfo.sprites.backDefault, ivSprite1)
            loadImage(pokemonFullInfo.sprites.backShiny, ivSprite2)
            loadImage(pokemonFullInfo.sprites.frontDefault, ivSprite3)
            loadImage(pokemonFullInfo.sprites.frontShiny, ivSprite4)

            loadImage(image, ivPokemon)

            listenerIconFav(cbFavorite, idPokemon, namePokemon, color, image)

        }
    }

    private fun listenerIconFav(
        cbFavorite: CheckBox,
        id: Int,
        name: String,
        color: Int,
        image: String
    ) {
        cbFavorite.setOnClickListener {

            if (pokemon.isFavorite) {
                viewModel.deletePokemon(pokemon.id)
            } else {
                viewModel.savePokemon(
                    PokemonEntity(
                        id = id,
                        name = name,
                        color = color,
                        image = image,
                        url = ""
                    )
                )
            }

            pokemon.isFavorite = !pokemon.isFavorite
        }
    }

    private fun setBackgroundColor(ivOvalPokemon: ImageView, image: String): Int {
        if (pokemon.color != 0) {
            ivOvalPokemon.background.overrideColor(pokemon.color)
            return pokemon.color
        } else {
            var color: Int? = null
            Glide.with(requireContext())
                .asBitmap()
                .load(image)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        Palette.from(resource).generate {
                            color = it?.getMutedColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.default_background_color
                                )
                            )
                            ivOvalPokemon.background.overrideColor(color!!)
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
            return color ?: 0
        }
    }

    private fun loadImage(url: String, imageView: ImageView) {
        Glide.with(this).load(url).centerCrop().into(imageView)
    }
}
