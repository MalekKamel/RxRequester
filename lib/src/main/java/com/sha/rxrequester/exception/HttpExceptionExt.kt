package com.sha.rxrequester.exception

import retrofit2.HttpException

fun HttpException.error(): String  {
    return response()?.errorBody()!!.string()
}

fun HttpException.errorCode(): Int? {
    return response()?.code()
}