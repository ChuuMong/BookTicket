package space.chuumong.bookticket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import space.chuumong.bookticket.data.Result
import space.chuumong.bookticket.data.model.Course
import space.chuumong.bookticket.data.model.TicketType
import space.chuumong.bookticket.data.model.TimeCourseInfo
import space.chuumong.bookticket.data.model.TimeStock
import space.chuumong.bookticket.data.repositories.TicketTimeCourseRepository
import space.chuumong.bookticket.utils.SingleLiveEvent
import space.chuumong.bookticket.utils.numberFormat
import java.text.NumberFormat

class TimeCourseViewModel(
    private val ticketTimeCourseRepository: TicketTimeCourseRepository
) : BaseViewModel() {

    private val _selectDesc = MutableLiveData<String>()
    val selectDesc: LiveData<String> get() = _selectDesc
    private val _isSelectTimeCourse = MutableLiveData<Boolean>().apply { value = false }
    val isSelectTimeCourse: LiveData<Boolean> get() = _isSelectTimeCourse
    private val _beforeSelectPosition = MutableLiveData<Int>()
    val beforeSelectPosition: LiveData<Int> get() = _beforeSelectPosition
    private val _selectTicketCount = MutableLiveData<Int>()
    val selectTicketCount: LiveData<Int> get() = _selectTicketCount
    private val _selectTicketPrice = MutableLiveData<String>()
    val selectTicketPrice: LiveData<String> get() = _selectTicketPrice
    private val _totalPrice = MutableLiveData<String>()
    val totalPrice: LiveData<String> get() = _totalPrice

    private val _onClickPrev = SingleLiveEvent<Any>()
    val onClickPrev: LiveData<Any> get() = _onClickPrev

    private val _onClickNext = SingleLiveEvent<Any>()
    val onClickNext: LiveData<Any> get() = _onClickNext

    private var timeCourseInfo: TimeCourseInfo? = null
    private val selectCourses = mutableListOf<Course>()
    private var currentPosition = -1

    fun onClickPrev() {
        _onClickPrev.call()
    }

    fun onClickNext() {
        _onClickNext.call()
    }

    fun getTimeCourseInfo(date: String, type: TicketType, result: Result<TimeCourseInfo>) {
        add(
            ticketTimeCourseRepository.getTimeCourseInfo(date, type)
                .subscribe({
                    _selectDesc.value = it.ticketDesc

                    timeCourseInfo = it

                    selectCourses.clear()
                    currentPosition = -1

                    result.onSuccess(it)
                }, {
                    result.onFail(it)
                })
        )
    }

    fun selectOneAndThreeTimeStock(course: Course, position: Int) {
        if (selectCourses.isNotEmpty()) {
            val beforeCourse = selectCourses[0]
            if (beforeCourse.time != course.time) {
                beforeCourse.timeStocks.forEach { it.isSelect = false }
                _beforeSelectPosition.value = currentPosition
            }
            selectCourses.removeAt(0)
        }

        if (course.timeStocks.find { it.isSelect } != null) {
            selectCourses.add(course)
            currentPosition = position
            _isSelectTimeCourse.value = true

            _selectTicketCount.value = selectCourses.size
            _selectTicketPrice.value = timeCourseInfo!!.prices.first().desc
            _totalPrice.value = timeCourseInfo!!.prices.first().price.numberFormat()
        } else {
            _isSelectTimeCourse.value = false
        }
    }

    fun selectMultipleTimeStock(selectCourse: Course) {
        val course = selectCourses.find { it.time == selectCourse.time }
        if (course == null) {
            selectCourses.add(selectCourse)
        } else {
            if (selectCourse.timeStocks.find { it.isSelect } == null) {
                selectCourses.remove(course)
            } else {
                course.timeStocks.forEach { stock ->
                    val timeStock = selectCourse.timeStocks.find { it.isSelect }
                    stock.isSelect = timeStock != null && stock.extTimeId == timeStock.extTimeId
                }
            }
        }

        val price = timeCourseInfo!!.prices.find { it.discountBuyCount == selectCourses.size }
            ?: timeCourseInfo!!.prices.last()

        _isSelectTimeCourse.value = selectCourses.isNotEmpty()
        _selectTicketCount.value = selectCourses.size
        _selectTicketPrice.value = price.desc
        _totalPrice.value = (price.price * selectCourses.size).numberFormat()
    }

    fun getSelectTimeCourse(): TimeStock? {
        return selectCourses.first().timeStocks.find { it.isSelect }
    }

    fun getSelectTimeCourses(): List<TimeStock> {
        val stocks = mutableListOf<TimeStock>()
        selectCourses.forEach { course ->
            val stock = course.timeStocks.find { it.isSelect }
            if (stock != null) {
                stocks.add(stock)
            }
        }

        return stocks
    }
}