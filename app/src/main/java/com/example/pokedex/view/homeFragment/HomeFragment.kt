package com.example.pokedex.view.homeFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentHomeBinding
import com.example.pokedex.domain.data.DataSourceImpl
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.domain.repository.RepoPokemonImpl
import com.example.pokedex.utils.Resource
import com.example.pokedex.viewModel.HomeViewModel
import com.example.pokedex.viewModel.VMFactory


class HomeFragment : Fragment(R.layout.fragment_home), OnPokemonClickListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel> { VMFactory(RepoPokemonImpl(DataSourceImpl()))}


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        initViewModel()
    }

    private fun initViewModel() {
        viewModel.getPokemonList.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Loading -> {
                    binding.pbFragmentHome.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.pbFragmentHome.visibility = View.GONE
                    initRecyclerView(it.data)
                }
                is Resource.Failure -> {
                    binding.pbFragmentHome.visibility = View.GONE
                    Toast.makeText(context, "Algo salio mal", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initRecyclerView(data: List<Pokemon>) {
        binding.rvPokemos.layoutManager = GridLayoutManager(context, 2)
        binding.rvPokemos.adapter = HomeAdapter(data, this)
        binding.rvPokemos.setHasFixedSize(true)


        binding.rvPokemos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    Log.d("scroll", "${recyclerView.computeVerticalScrollOffset()}")
                    //Haga puedes icorporar la logica que deseas
                }
            }
        })
    }


    override fun onClick(item: Pokemon) {
        Toast.makeText(context, "click", Toast.LENGTH_SHORT).show()
    }


}