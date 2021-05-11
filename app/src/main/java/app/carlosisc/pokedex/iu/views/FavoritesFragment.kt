package app.carlosisc.pokedex.iu.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import app.carlosisc.pokedex.domain.datasource.DataSourceImpl
import app.carlosisc.pokedex.domain.models.Pokemon
import app.carlosisc.pokedex.domain.repository.RepoPokemonImpl
import app.carlosisc.pokedex.domain.datasource.database.AppDatabase
import app.carlosisc.pokedex.utils.Resource
import app.carlosisc.pokedex.iu.base.BaseFragment
import app.carlosisc.pokedex.iu.reclycerView.MainAdapter
import app.carlosisc.pokedex.viewModel.FavoritesPokemonViewModel
import app.carlosisc.pokedex.viewModel.VMFactory
import com.carlosisc.pokedex.R
import com.carlosisc.pokedex.databinding.FragmentFavoritesBinding


class FavoritesFragment : BaseFragment(R.layout.fragment_favorites) {

    private lateinit var binding: FragmentFavoritesBinding
    private var adapter: MainAdapter? = null

    private val viewModel by viewModels<FavoritesPokemonViewModel> {
        VMFactory(
            RepoPokemonImpl(
                DataSourceImpl(
                    AppDatabase.getDatabase(requireActivity().applicationContext)
                )
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoritesBinding.bind(view)
        initRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getFavoritesPokemon()
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                when (it) {
                    is Resource.Loading -> {
                        binding.includeLogo.flProgressBar.visibility = View.VISIBLE
                        binding.tvEmptyList.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        binding.includeLogo.flProgressBar.visibility = View.GONE

                        if (it.data.isNotEmpty()){
                            val listPokemon = it.data.map { pokemon ->
                                Pokemon(
                                    pokemon.id,
                                    pokemon.name,
                                    pokemon.url,
                                    pokemon.image,
                                    pokemon.color
                                )
                            }
                            adapter = MainAdapter(this)
                            adapter?.updateData(listPokemon as MutableList<Pokemon>,)
                            binding.rvFavoritesPokemos.adapter = adapter
                        }else {
                            binding.tvEmptyList.visibility = View.VISIBLE
                        }
                    }
                    is Resource.Failure -> {
                        binding.tvEmptyList.visibility = View.GONE
                        binding.includeLogo.flProgressBar.visibility = View.GONE
                    }
                }
            })
    }

    private fun initRecyclerView() {
        binding.rvFavoritesPokemos.layoutManager = GridLayoutManager(context, 2)
        binding.rvFavoritesPokemos.setHasFixedSize(true)
    }
}