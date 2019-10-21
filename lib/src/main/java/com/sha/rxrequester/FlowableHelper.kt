package com.sha.rxrequester

import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.Functions
import io.reactivex.internal.operators.flowable.FlowableInternalHelper
import io.reactivex.internal.subscribers.LambdaSubscriber
import org.reactivestreams.Subscriber

internal object FlowableHelper {

    fun <T> createFlowableSubscriber(onNext: Consumer<in T>): Subscriber<T> {
        return LambdaSubscriber(
                onNext,
                Functions.ON_ERROR_MISSING,
                Functions.EMPTY_ACTION,
                FlowableInternalHelper.RequestMax.INSTANCE
        )
    }

}