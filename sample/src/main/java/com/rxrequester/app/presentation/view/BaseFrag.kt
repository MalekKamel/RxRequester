package com.rxrequester.app.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

abstract class BaseFrag<VM: BaseViewModel> : Fragment() {

    abstract val vm: VM

    abstract var layoutId: Int
    protected open fun doOnViewCreated() {}
    protected fun doOnResume() {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            doOnViewCreated()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onResume() {
        super.onResume()
        try {
            doOnResume()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun <T : View> findViewById(@IdRes id: Int): T {
        return activity!!.findViewById(id)
    }
}