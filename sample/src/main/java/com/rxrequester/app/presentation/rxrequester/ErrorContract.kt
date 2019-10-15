package com.rxrequester.app.presentation.rxrequester

import com.sha.rxrequester.exception.ErrorMessage

data class ErrorContract(private val message: String): ErrorMessage {
    override fun errorMessage(): String {
        return message
    }
}