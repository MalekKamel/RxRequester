package com.restaurants.app.util

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Sha on 9/22/17.
 */

fun RecyclerView.linearLayoutManager(context: Context?){
    if (context == null) return
    layoutManager = LinearLayoutManager(context)
}