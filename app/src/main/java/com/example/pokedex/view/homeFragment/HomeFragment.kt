package com.example.pokedex.view.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentHomeBinding
import com.example.pokedex.domain.data.DataSourceImpl
import com.example.pokedex.domain.models.PokemonInfo
import com.example.pokedex.domain.repository.RepoPokemonImpl
import com.example.pokedex.utils.Resource
import com.example.pokedex.viewModel.HomeViewModel
import com.example.pokedex.viewModel.VMFactory


class HomeFragment : Fragment(R.layout.fragment_home), OnPokemonClickListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel> { VMFactory(RepoPokemonImpl(DataSourceImpl())) }
    private var adapter: HomeAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        initUI()
    }


    private fun initUI() {
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

    override fun onClick(item: PokemonInfo) {
        val bundle = Bundle()
        bundle.putParcelable("pokemon", item)
        findNavController().navigate(R.id.action_home_fragment_to_detailPokemonFragment, bundle)
    }

}