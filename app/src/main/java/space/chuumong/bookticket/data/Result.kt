package space.chuumong.bookticket.data


interface Result<T> {

    fun onSuccess(result: T)

    fun onFail(t: Throwable)
}