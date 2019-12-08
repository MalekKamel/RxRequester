package com.sha.rxrequester.exception

import com.sha.rxrequester.Presentable
import com.sha.rxrequester.RxRequester
import io.reactivex.functions.Consumer

data class InterceptorArgs(
        val presentable: Presentable,
        val serverErrorContract: Class<*>?,
        var inlineHandling: ((Throwable) -> Boolean)?
)

class ExceptionInterceptor(private val args: InterceptorArgs) : Consumer<Throwable> {

    override fun accept(throwable: Throwable) {
        throwable.printStackTrace()
        args.presentable.hideLoading()

        // inline handling of the error
        if (args.inlineHandling?.invoke(throwable) == true) return

        ExceptionProcessor.process(
                throwable = throwable,
                presentable = args.presentable,
                serverErrorContract = args.serverErrorContract
        )
    }


}