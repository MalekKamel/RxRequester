package com.sha.rxrequester.exception.handler.resumable

sealed class ResumeResult<out R> {

    object Resume : ResumeResult<Nothing>()
    data class Error(val exception: Exception) : ResumeResult<Nothing>()

}