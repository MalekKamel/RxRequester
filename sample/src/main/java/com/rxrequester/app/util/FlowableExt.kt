package com.rxrequester.app.util

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.disposeBy(disposable: CompositeDisposable) {
    disposable.add(this)
}