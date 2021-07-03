package app.carlosisc.pokedex.ui.reclycerView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import app.carlosisc.pokedex.domain.models.Pokemon
import app.carlosisc.pokedex.ui.base.BaseViewHolder
import app.carlosisc.pokedex.ui.base.OnAdapterClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.carlosisc.pokedex.R
import com.carlosisc.pokedex.databinding.PokemonItemBinding
import com.bumptech.glide.request.transition.Transition


class MainViewHolder(itemView: View) : BaseViewHolder<Pokemon>(itemView) {

    private val binding = PokemonItemBinding.bind(itemView)

    override fun bind(item: Pokemon, position: Int, listener: OnAdapterClickListener<Pokemon>) {
        with(binding){
            tvNamePokemon.text = item.name.capitalize()

            loadImage(mContext, item, ivPokemon, cvPokemon)
            rlItemPokemon.setOnClickListener {
                listener.onClickItem(item)
            }
        }
    }

    private fun loadImage(
        context: Context,
        item: Pokemon,
        ivPokemon: ImageView,
        cvPokemon: CardView
    ) {
        Glide.with(context)
            .asBitmap()
            .load(item.image)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    ivPokemon.setImageBitmap(resource)

                    Palette.from(resource).generate { palette ->
                        val color = palette?.getMutedColor(
                            ContextCompat.getColor(
                                context,
                                R.color.default_background_color
                            )
                        )
                        item.color = color!!
                        cvPokemon.setCardBackgroundColor(color)
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}
