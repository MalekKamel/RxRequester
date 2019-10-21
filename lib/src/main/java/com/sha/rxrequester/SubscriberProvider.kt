package com.sha.rxrequester

import io.reactivex.functions.Consumer
import org.reactivestreams.Subscriber

interface SubscriberProvider<T> {
    fun  subscriber(onNext: Consumer<in T>): Subscriber<T>
}

class DefaultSubscriberProvider<T>: SubscriberProvider<T> {
    override fun  subscriber(onNext: Consumer<in T>): Subscriber<T> {
        return FlowableHelper.createFlowableSubscriber(onNext)
    }
}