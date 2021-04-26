package com.example.pokedex.view.homeFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.view.base.BaseViewHolder

class HomeAdapter(private val listPokemon: List<Pokemon>,
                  private val onItemClickListener: OnPokemonClickListener)
    : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HomeViewHolder(layoutInflater.inflate(R.layout.pokemon_item, parent, false),
                onItemClickListener)
    }

    override fun getItemCount(): Int = listPokemon.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is HomeViewHolder -> holder.bind(listPokemon[position], position)
        }
    }
}