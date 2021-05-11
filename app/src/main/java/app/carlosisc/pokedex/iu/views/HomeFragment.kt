package app.carlosisc.pokedex.iu.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import app.carlosisc.pokedex.domain.datasource.DataSourceImpl
import app.carlosisc.pokedex.domain.models.Pokemon
import app.carlosisc.pokedex.domain.repository.RepoPokemonImpl
import app.carlosisc.pokedex.domain.datasource.database.AppDatabase
import app.carlosisc.pokedex.utils.Resource
import app.carlosisc.pokedex.iu.base.BaseFragment
import app.carlosisc.pokedex.iu.reclycerView.MainAdapter
import app.carlosisc.pokedex.viewModel.HomeViewModel
import app.carlosisc.pokedex.viewModel.VMFactory
import com.carlosisc.pokedex.R
import com.carlosisc.pokedex.databinding.FragmentHomeBinding
import java.util.*


class HomeFragment: BaseFragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private var adapter: MainAdapter? = null

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
                    adapter = MainAdapter(this)
                    adapter?.updateData(it.data)
                    binding.rvPokemos.adapter = adapter
                }
                is Resource.Failure -> {
                    binding.includeLogo.flProgressBar.visibility = View.GONE
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvPokemos.layoutManager = GridLayoutManager(context, 2)
        binding.rvPokemos.setHasFixedSize(true)
    }

    override fun onClickItem(item: Pokemon) {
        binding.svPokemon.onActionViewCollapsed()
        super.onClickItem(item)
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
                onClickItem(Pokemon(name = query!!.toLowerCase(Locale.ROOT)))
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

}