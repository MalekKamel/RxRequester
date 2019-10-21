package com.sha.rxrequester

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class RequestOptions(
        /**
         * callback for handling the error at call site
         * @return true if the error is handled, false otherwise
         * If the error wasn't handled(by returning false), the provided
         * handlers will try to handle the error.
         * If all handlers failed, [Presentable#onHandleErrorFailed(Throwable]
         * will be called
         */
        var inlineHandling: ((Throwable) -> Boolean)? = null,
        /**
         * show loading indicator
         */
        var showLoading: Boolean = true,
        /**
         * subscribeOn scheduler
         */
        var subscribeOnScheduler: Scheduler? = null,
        /**
         * observeOn scheduler
         */
        var observeOnScheduler: Scheduler? = null
){

    fun subscribeOnScheduler(): Scheduler {
        return subscribeOnScheduler ?: Schedulers.io()
    }

    fun observeOnScheduler(): Scheduler {
        return observeOnScheduler ?: AndroidSchedulers.mainThread()
    }

    class Builder {
        private val info = RequestOptions()

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

        fun build(): RequestOptions {
            return info
        }
    }

    companion object {
        fun defaultInfo(): RequestOptions {
            return Builder().build()
        }
    }

}