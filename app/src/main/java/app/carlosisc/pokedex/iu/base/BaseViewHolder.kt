package app.carlosisc.pokedex.iu.base

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(itemView: View): RecyclerView.ViewHolder(itemView){
    protected val mContext: Context = itemView.context
    abstract fun bind(item: T, position: Int, listener: OnAdapterClickListener<T>)
}