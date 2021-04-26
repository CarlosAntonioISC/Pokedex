package com.example.pokedex.view.homeFragment

import android.view.View
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.PokemonItemBinding
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.view.base.BaseViewHolder

class HomeViewHolder(itemView: View, private val onItemClickListener: OnPokemonClickListener): BaseViewHolder<Pokemon>(itemView) {

    private val binding = PokemonItemBinding.bind(itemView)

    override fun bind(item: Pokemon, position: Int) {

        with(binding) {
            Glide.with(itemView).load(item.image).into(ivPokemon)
            rlItemPokemon.setOnClickListener {
                onItemClickListener.onClick(item)
            }
        }
    }


}
