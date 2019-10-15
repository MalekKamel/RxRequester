package com.restaurants.app.ui.restaurants

import android.view.ViewGroup
import com.restaurants.app.R
import com.restaurants.app.presentation.view.BaseRecyclerAdapter
import com.restaurants.app.presentation.view.BaseViewHolder
import kotlinx.android.synthetic.main.item_restaurant.view.*
import com.restaurants.app.util.picasso.PicassoUtil
import com.restaurants.app.data.model.Restaurant


/**
 * Created by Sha on 4/20/17.
 */

class RestaurantsAdapter(list: MutableList<Restaurant>) : BaseRecyclerAdapter<Restaurant, RestaurantsAdapter.Vh>(list) {

    override fun getViewHolder(viewGroup: ViewGroup, viewType: Int): Vh {
        return Vh(viewGroup)
    }

    inner class Vh internal constructor(viewGroup: ViewGroup)
        : BaseViewHolder<Restaurant>(viewGroup, R.layout.item_restaurant) {


        override fun bindView(item: Restaurant) {
            itemView.tvName.text = item.name
            itemView.tvCity.text = item.city
            PicassoUtil.load(
                    iv =itemView.ivPoster,
                    url = item.poster
            )
        }

    }
}
