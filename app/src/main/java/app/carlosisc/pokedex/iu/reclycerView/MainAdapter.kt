package app.carlosisc.pokedex.iu.reclycerView

import android.view.ViewGroup
import app.carlosisc.pokedex.domain.models.Pokemon
import app.carlosisc.pokedex.iu.base.BaseAdapter
import app.carlosisc.pokedex.iu.base.BaseViewHolder
import app.carlosisc.pokedex.iu.base.OnAdapterClickListener
import com.carlosisc.pokedex.R

class MainAdapter(private val listener: OnAdapterClickListener<Pokemon>): BaseAdapter<Pokemon>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Pokemon> {
        val view = getViewFromLayout(parent, R.layout.pokemon_item)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Pokemon>, position: Int) {
        holder.bind(mItems[position], position, listener)
    }
}