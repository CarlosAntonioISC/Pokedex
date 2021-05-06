package app.carlosisc.pokedex.view.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>: RecyclerView.Adapter<BaseViewHolder<T>>(){

    protected var mItems: MutableList<T> = mutableListOf()

    fun updateData(list: MutableList<T>){
        mItems.clear()
        mItems.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mItems.size

    fun getItems() = mItems


    fun getViewFromLayout(viewGroup: ViewGroup, layout: Int): View{
        return LayoutInflater.from(viewGroup.context).inflate(layout, viewGroup, false)
    }

}