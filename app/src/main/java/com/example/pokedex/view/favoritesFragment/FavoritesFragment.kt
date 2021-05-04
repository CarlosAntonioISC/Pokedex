package com.example.pokedex.view.favoritesFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentFavoritesBinding
import com.example.pokedex.domain.datasource.DataSourceImpl
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.repository.RepoPokemonImpl
import com.example.pokedex.domain.datasource.database.AppDatabase
import com.example.pokedex.utils.Resource
import com.example.pokedex.view.base.BaseFragment
import com.example.pokedex.view.reclycerView.MainAdapter
import com.example.pokedex.viewModel.FavoritesPokemonViewModel
import com.example.pokedex.viewModel.VMFactory


class FavoritesFragment : BaseFragment(R.layout.fragment_favorites) {

    private lateinit var binding: FragmentFavoritesBinding
    private var adapter: MainAdapter? = null

    private val viewModel by viewModels<FavoritesPokemonViewModel> {
        VMFactory(
            RepoPokemonImpl(
                DataSourceImpl(AppDatabase.getDatabase(requireActivity().applicationContext))
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
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer { it ->
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

                            Log.d("",listPokemon.toString())
                            adapter =
                                MainAdapter(
                                    listPokemon as MutableList<Pokemon>,
                                    this,
                                    requireContext()
                                )
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