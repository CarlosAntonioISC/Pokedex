package app.carlosisc.pokedex.view.reclycerView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.carlosisc.pokedex.domain.models.Pokemon
import app.carlosisc.pokedex.view.base.BaseViewHolder
import com.carlosisc.pokedex.R

class MainAdapter(private var listPokemon: MutableList<Pokemon>,
                  private val onItemClickListener: OnPokemonClickListener,
                  private val context: Context
)
    : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MainViewHolder(
            layoutInflater.inflate(R.layout.pokemon_item, parent, false),
            onItemClickListener
        )
    }

    override fun getItemCount(): Int = listPokemon.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(listPokemon[position], position, context)
        }
    }
}