package com.example.pokedex.view.homeFragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentHomeBinding
import com.example.pokedex.domain.data.DataSourceImpl
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.repository.RepoPokemonImpl
import com.example.pokedex.utils.AppDatabase
import com.example.pokedex.utils.Resource
import com.example.pokedex.view.base.BaseFragment
import com.example.pokedex.viewModel.HomeViewModel
import com.example.pokedex.viewModel.VMFactory
import java.util.*


class HomeFragment: BaseFragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private var adapter: HomeAdapter? = null

    private val viewModel by viewModels<HomeViewModel> {
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
        binding = FragmentHomeBinding.bind(view)
        initUI()
    }


    private fun initUI() {
        setupSearchView()
        initRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getPokemonList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.includeLogo.flProgressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.includeLogo.flProgressBar.visibility = View.GONE
                    adapter = HomeAdapter(it.data, this, requireContext())
                    binding.rvPokemos.adapter = adapter
                }
                is Resource.Failure -> {
                    binding.includeLogo.flProgressBar.visibility = View.GONE
                    Toast.makeText(context, "Algo salio mal", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvPokemos.layoutManager = GridLayoutManager(context, 2)
        binding.rvPokemos.setHasFixedSize(true)
    }

    override fun onPokemonClick(item: Pokemon) {
        binding.svPokemon.onActionViewCollapsed()
        super.onPokemonClick(item)
    }

    private fun setupSearchView() {

        binding.svPokemon.setOnSearchClickListener {
            binding.tvNameApp.visibility = View.INVISIBLE
        }

        binding.svPokemon.setOnCloseListener {
            binding.tvNameApp.visibility = View.VISIBLE
            binding.svPokemon.onActionViewCollapsed()
            true
        }

        binding.svPokemon.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                // iconify the SearchView when the focus is lost
                binding.svPokemon.isIconified = true
            }
        }
        binding.svPokemon.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.tvNameApp.visibility = View.VISIBLE
                binding.svPokemon.onActionViewCollapsed()
                onPokemonClick(Pokemon(name = query!!.toLowerCase(Locale.ROOT)))
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

}