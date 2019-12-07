package space.chuumong.bookticket.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class TimeCourseInfoResponse(
    @SerializedName("timeDesc")
    val ticketDesc: String,
    @SerializedName("prices")
    val prices: List<TicketPriceResponse>?,
    @SerializedName("slotStocks")
    val courses: List<CourseResponse>?
)

data class TicketPriceResponse(
    @SerializedName("discountPrice")
    val price: Int,
    @SerializedName("discountDesc")
    val desc: String,
    @SerializedName("discountBuyCount")
    val discountBuyCount: Int
)

data class CourseResponse(
    @SerializedName("orderNo")
    val orderNo: Int,
    @SerializedName("extId")
    val extId: String,
    @SerializedName("extProductId")
    val extProductId: String,
    @SerializedName("slotName")
    val slotName: String,
    @SerializedName("timeSlotStocks")
    val timeStocks: List<TimeStockResponse>?
)

data class TimeStockResponse(
    @SerializedName("extTimeId")
    val extTimeId: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("totalStock")
    val totalStock: Int,
    @SerializedName("stockStatus")
    val stockStatus: String,
    @SerializedName("resvRate")
    val resvRate: Int
)