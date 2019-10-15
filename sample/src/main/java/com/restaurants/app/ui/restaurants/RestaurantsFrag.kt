package com.restaurants.app.ui.restaurants

import com.restaurants.app.R
import com.restaurants.app.presentation.view.BaseFrag
import com.restaurants.app.util.linearLayoutManager
import kotlinx.android.synthetic.main.include_recycler_view_refreshable.*
import org.koin.android.viewmodel.ext.android.viewModel

class RestaurantsFrag : BaseFrag<RestaurantsVm>() {

    override val vm: RestaurantsVm by viewModel()
    override var layoutId: Int = R.layout.frag_search

    companion object {
        fun newInstance(): RestaurantsFrag {
            return RestaurantsFrag()
        }
    }

    override fun doOnViewCreated() {
        super.doOnViewCreated()

        rv.linearLayoutManager(context)

        loadRestaurants()
    }

    private fun loadRestaurants() {
        vm.restaurants {
            rv.adapter = com.restaurants.app.ui.restaurants.RestaurantsAdapter(list = it)
            rv.scheduleLayoutAnimation()
        }
    }

    fun showLoading() {
        activity?.runOnUiThread { swipeRefresh.isRefreshing = true }
    }

    fun hideLoading() {
        activity?.runOnUiThread { swipeRefresh.isRefreshing = false }
    }

    fun onSwipeRefresh() {
        loadRestaurants()
    }
}
