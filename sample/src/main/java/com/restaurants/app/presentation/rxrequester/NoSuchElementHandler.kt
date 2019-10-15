package com.restaurants.app.presentation.rxrequester

import com.restaurants.app.R
import com.sha.rxrequester.exception.handler.nonhttp.NonHttpExceptionHandler
import com.sha.rxrequester.exception.handler.nonhttp.NonHttpExceptionInfo
import java.util.*

class NoSuchElementHandler : NonHttpExceptionHandler<NoSuchElementException>() {

    override fun supportedThrowables(): List<Class<out NoSuchElementException>> {
        return listOf(NoSuchElementException::class.java)
    }

    override fun handle(info: NonHttpExceptionInfo) {
        info.presentable.showError(R.string.no_data_entered_yet)
    }
}
