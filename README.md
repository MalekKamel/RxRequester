# RxRequester

<img src="https://github.com/ShabanKamell/RxRequester/blob/master/blob/raw/logo.png" height="200">

A simple wrapper for RxJava that helps you:
- [ ] Make clean RxJava requests
- [ ] Handle errors in a clean and effective way.

RxRequester does all the dirty work for you!

### Before RxRequester

``` kotlin
dm.restaurantsRepo.all()
                .doOnSubscribe { showLoading() }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext { hideLoading() }
                .subscribe( {

                }, { error ->

                })
```

### After RxRequester

``` kotlin
requester.request { dm.restaurantsRepo.all() }.subscribe { }
```

#### Gradle:
```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}

dependencies {
        implementation 'com.github.ShabanKamell:RxRequester:x.y.z'
}

```
(Please replace x, y and y with the latest version numbers:  [![](https://jitpack.io/v/ShabanKamell/RxRequester.svg)](https://jitpack.io/#ShabanKamell/RxRequester))

### Usage
#### Setup

``` kotlin
val presentable = object: Presentable {
            override fun showError(error: String) { showError.value = error }
            override fun showError(error: Int) { showErrorRes.value = error }
            override fun showLoading() { toggleLoading.value = true }
            override fun hideLoading() { toggleLoading.value = false }
            override fun onHandleErrorFailed() { showErrorRes.value = R.string.oops_something_went_wrong }
        }

       val requester = RxRequester.create(ErrorContract::class.java, presentable)
```

#### Server Error Contract
RxRequester parsers server error for you and shows the error automatically. Just implement `ErrorMessage`
interface in your server error model and return the error message.

``` kotlin
data class ErrorContract(private val message: String): ErrorMessage {
    override fun errorMessage(): String {
        return message
    }
}
```

#### Handle Errors
```kotlin
            RxRequester.nonHttpHandlers = listOf(
                    IoExceptionHandler(),
                    NoSuchElementHandler(),
                    OutOfMemoryErrorHandler()
            )
            RxRequester.httpHandlers = listOf(
                    TokenExpiredHandler(),
                    ServerErrorHandler()
            )
```

#### Error Handlers
Error handler is a class that extends
`HttpExceptionHandler`
``` kotlin
class ServerErrorHandler : HttpExceptionHandler() {

    override fun supportedExceptions(): List<Int> {
        return listOf(500)
    }

    override fun handle(info: HttpExceptionInfo) {
        info.presentable.showError(R.string.oops_something_went_wrong)
    }
}
```

Or `NonHttpExceptionHandler`
``` kotin
class OutOfMemoryErrorHandler : NonHttpExceptionHandler<OutOfMemoryError>() {

    override fun supportedThrowables(): List<Class<OutOfMemoryError>> {
        return listOf(OutOfMemoryError::class.java)
    }

    override fun handle(info: NonHttpExceptionInfo) {
        info.presentable.showError(R.string.no_memory_free_up_space)
    }
}
```

#### Customizing Requests
RxRequester gives you the full controll over any request
- [ ] Inline error handling
- [ ] Enable/Disable loading indicators
- [ ] Set subscribeOn Scheduler
- [ ] Set observeOn Scheduler

``` kotlin
val requestInfo = RequestInfo.Builder()
                .inlineErrorHandling { false }
                .showLoading(true)
                .subscribeOnScheduler(Schedulers.io())
                .observeOnScheduler(AndroidSchedulers.mainThread())
                .build()
requester.request(requestInfo) { dm.restaurantsRepo.all() }
```

Here're all request options and default values

| **Option** | **Type** | **Default** |
| ------------- | ------------- | ------------- |
| **inlineHandling**           | Lambda       | null |
| **showLoading**              | Boolean      | true |
| **subscribeOnScheduler**     | Scheduler    | Schedulers.io() |
| **observeOnScheduler**       | Scheduler    | AndroidSchedulers.mainThread() |

### Retrying The Request
You can retry the request in any error handler class by calling `HttpExceptionInfo.retryRequest()`.
This is very useful when you receive `401` indicating the token was EXPIRED. To fix the issue, call the refresh token API inside the handler, then retry the request again without interrupting the user. For more, look at `TokenExpiredHandler` in sample module.

### Best Practices
- [ ] Setup `RxRequester` only once in `BaseViewModel` and reuse in the whole app.
- [ ] Initialize error handlers only once.
- [ ] Dispose `RxRequester` in `ViewModel.onCleared()`.

#### Look at 'sample' module for the full code. For more advanced example, [Restaurants Modular Architecture](https://github.com/ShabanKamell/Restaurants)

### License

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
