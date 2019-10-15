package com.restaurants.app.presentation.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Sha on 4/20/17.
 */

abstract class BaseRecyclerAdapter<M, VH
: BaseViewHolder<M>>(var list: MutableList<M>) : RecyclerView.Adapter<VH>(){

    protected var isLoadingAdded: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return getViewHolder(parent, viewType)
    }

    abstract fun getViewHolder(viewGroup: ViewGroup, viewType: Int): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        try {
            val item = list[position]
            holder.item = item
            holder.bindView(item)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun item(position: Int): M {
        return list[position]
    }

    fun addAll(items: List<M>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun replace(items: MutableList<M>) {
        list = items
        notifyDataSetChanged()
    }

    fun list(): List<M> {
        return list
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }


}
