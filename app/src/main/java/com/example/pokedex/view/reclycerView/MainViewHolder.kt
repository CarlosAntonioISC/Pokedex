package com.example.pokedex.view.reclycerView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.pokedex.databinding.PokemonItemBinding
import com.example.pokedex.domain.models.Pokemon
import com.example.pokedex.view.base.BaseViewHolder
import com.example.pokedex.R
import java.util.*


class MainViewHolder(itemView: View, private val onItemClickListener: OnPokemonClickListener)
    : BaseViewHolder<Pokemon>(itemView) {

    private val binding = PokemonItemBinding.bind(itemView)

    override fun bind(item: Pokemon, position: Int, context: Context) {

        with(binding) {
            tvNamePokemon.text = item.name.capitalize(Locale.ENGLISH)

            loadImage(context, item, ivPokemon, cvPokemon)

            rlItemPokemon.setOnClickListener {
                onItemClickListener.onPokemonClick(item)
            }
        }
    }

    private fun loadImage(context: Context, item: Pokemon, ivPokemon: ImageView, cvPokemon: CardView){
        Glide.with(context)
                .asBitmap()
                .load(item.image)
                .into(object : CustomTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        ivPokemon.setImageBitmap(resource)

                        Palette.from(resource).generate {
                            val color = it?.getMutedColor(ContextCompat.getColor(context, R.color.default_background_color))
                            item.color = color!!
                            cvPokemon.setCardBackgroundColor(color)
                        }
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
    }
}
