package space.chuumong.bookticket.utils

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject


object RxBus {

    private val publisher = PublishSubject.create<Any>()

    private val subscriptionMap = hashMapOf<Any, CompositeDisposable>()

    fun post(event: Any) {
        if (publisher.hasObservers()) {
            publisher.onNext(event)
        }
    }

    fun isRegister(lifecycle: Any): Boolean {
        return subscriptionMap.containsKey(lifecycle)
    }

    fun <T> register(lifecycle: Any, type: Class<T>, action: Consumer<T>): Disposable {
        val subscription =
            publisher.ofType(type).observeOn(AndroidSchedulers.mainThread()).subscribe(action)
        getCompositeDisposable(lifecycle).add(subscription)
        return subscription
    }

    fun unregister(lifecycle: Any) {
        val compositeDisposable = subscriptionMap.remove(lifecycle)
        compositeDisposable?.dispose()
    }

    private fun getCompositeDisposable(any: Any): CompositeDisposable {
        var compositeDisposable = subscriptionMap[any]
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
            subscriptionMap[any] = compositeDisposable
        }

        return compositeDisposable
    }
}