package com.sha.rxrequester

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulerProvider {
    val observeOn: Scheduler
    val subscribeOn: Scheduler
}

object DefSchedulerProvider: SchedulerProvider {
    override val observeOn: Scheduler = AndroidSchedulers.mainThread()
    override val subscribeOn: Scheduler = Schedulers.io()
}

object TestSchedulerProvider: SchedulerProvider {
    override val observeOn: Scheduler = Schedulers.trampoline()
    override val subscribeOn: Scheduler = Schedulers.trampoline()
}

internal fun defaultSubscriber(): SchedulerProvider {
    return RxRequester.defaultSchedulerProvider ?: DefSchedulerProvider
}