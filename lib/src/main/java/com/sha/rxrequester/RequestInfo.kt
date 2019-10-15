package com.sha.rxrequester

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RequestInfo private constructor(){
    var inlineHandling: ((Throwable) -> Boolean)? = null
    var showLoading: Boolean = true
    var subscribeOnScheduler: Scheduler = Schedulers.io()
    var observeOnScheduler: Scheduler = AndroidSchedulers.mainThread()

    class Builder {
        private val info = RequestInfo()

        fun inlineErrorHandling(callback: ((Throwable) -> Boolean)?): Builder {
            info.inlineHandling = callback
            return this
        }

        fun showLoading(show: Boolean): Builder {
            info.showLoading = show
            return this
        }

        fun subscribeOnScheduler(scheduler: Scheduler): Builder {
            info.subscribeOnScheduler = scheduler
            return this
        }

        fun observeOnScheduler(scheduler: Scheduler): Builder {
            info.observeOnScheduler = scheduler
            return this
        }

        fun build(): RequestInfo {
            return info
        }
    }

    companion object {
        fun defaultInfo(): RequestInfo {
            return Builder().build()
        }
    }

}