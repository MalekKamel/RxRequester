package com.sha.rxrequester.exception


import com.sha.rxrequester.Presentable
import com.sha.rxrequester.RxRequester
import io.reactivex.functions.Consumer

/**
 * Created by Mickey on 4/1/17.
 */

data class InterceptorArgs(
        val requester: RxRequester,
        val presentable: Presentable,
        val serverErrorContract: Class<*>?,
        var inlineHandling: ((Throwable) -> Boolean)?,
        var retryRequest: () -> Unit
)

class RxExceptionInterceptor(private val args: InterceptorArgs) : Consumer<Throwable> {

    override fun accept(throwable: Throwable) {
        throwable.printStackTrace()
        args.presentable.hideLoading()

        // inline handling of the error
        if (args.inlineHandling != null && args.inlineHandling!!(throwable))
            return

        ExceptionProcessor.process(
                throwable = throwable,
                presentable = args.presentable,
                serverErrorContract = args.serverErrorContract,
                retryRequest =  args.retryRequest,
                requester = args.requester
        )
    }


}