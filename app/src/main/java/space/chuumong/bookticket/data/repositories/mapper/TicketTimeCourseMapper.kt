package space.chuumong.bookticket.data.repositories.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import space.chuumong.bookticket.data.model.TimeCourseInfo
import space.chuumong.bookticket.data.remote.model.response.Response
import io.reactivex.functions.Function
import space.chuumong.bookticket.data.model.Course
import space.chuumong.bookticket.data.model.TicketPrice
import space.chuumong.bookticket.data.model.TimeStock
import space.chuumong.bookticket.data.remote.model.response.TimeCourseInfoResponse

class TicketTimeCourseMapper(private val gson: Gson) {

    fun toTimeCourseInfoEntity() = Function<Response, TimeCourseInfo> { response ->
        val info = gson.convertTimeCourseInfoResponse(response)
        val prices = mutableListOf<TicketPrice>()
        info.prices?.forEach { prices.add(TicketPrice(it.price, it.desc, it.discountBuyCount)) }

        val courses = mutableListOf<Course>()
        info.courses?.forEach { course ->
            course.timeStocks?.forEach { timeStock ->
                val addTimeStock = TimeStock(
                    course.orderNo,
                    timeStock.time,
                    course.extId,
                    course.extProductId,
                    course.slotName,
                    timeStock.extTimeId,
                    timeStock.stock,
                    timeStock.totalStock,
                    timeStock.stockStatus,
                    timeStock.resvRate
                )

                val tempStock = courses.find { it.time == timeStock.time }
                if (tempStock == null) {
                    courses.add(Course(timeStock.time, ArrayList(mutableListOf(addTimeStock))))
                } else {
                    tempStock.timeStocks.add(addTimeStock)
                }
            }
        }

        TimeCourseInfo(info.ticketDesc.replace("\\n", "\n"), prices, courses)
    }
}

fun Gson.convertTimeCourseInfoResponse(response: Response): TimeCourseInfoResponse {
    return fromJson(toJson(response.data), object : TypeToken<TimeCourseInfoResponse>() {}.type)
}