package com.sha.rxrequester.exception

/**
 * interface to be implemented in server error contract model
 */
interface ErrorMessage {
    fun errorMessage(): String
}