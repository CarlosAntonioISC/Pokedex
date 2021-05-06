package app.carlosisc.pokedex.view.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.carlosisc.pokedex.domain.models.Pokemon
import com.carlosisc.pokedex.R

open class BaseFragment(layout: Int) : Fragment(layout), OnAdapterClickListener<Pokemon>{
    override fun onClickItem(item: Pokemon) {
        val bundle = Bundle()
        bundle.putParcelable("pokemon", item)
        findNavController().navigate(R.id.detail_pokemon_fragment, bundle)
    }
}