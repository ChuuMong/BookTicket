package space.chuumong.bookticket.viewmodel

import androidx.lifecycle.LiveData
import space.chuumong.bookticket.utils.SingleLiveEvent

class ChoiceTicketViewModel : BaseViewModel() {

    private val _onClickOneTimeTicket = SingleLiveEvent<Any>()
    private val _onClickThreeTimeTicket = SingleLiveEvent<Any>()
    private val _onClickMultipleTimeTicket = SingleLiveEvent<Any>()

    val onClickOneTimeTicket: LiveData<Any> get() = _onClickOneTimeTicket
    val onClickThreeTimeTicket: LiveData<Any> get() = _onClickThreeTimeTicket
    val onClickMultipleTimeTicket: LiveData<Any> get() = _onClickMultipleTimeTicket

    fun onClickOneTimeTicket() {
        _onClickOneTimeTicket.call()
    }

    fun onClickThreeTimeTicket() {
        _onClickThreeTimeTicket.call()
    }

    fun onClickMultipleTimeTicket() {
        _onClickMultipleTimeTicket.call()
    }
}