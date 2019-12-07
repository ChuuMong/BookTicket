package space.chuumong.bookticket.data.model


data class TimeCourseInfo(
    val ticketDesc: String,
    val prices: List<TicketPrice>,
    val courses: List<Course>
)

data class TicketPrice(
    val price: Int,
    val desc: String,
    val discountBuyCount: Int
)

data class Course(
    val time: String,
    val timeStocks: ArrayList<TimeStock>
)

data class TimeStock(
    val orderNo: Int,
    val time: String,
    val extId: String,
    val extProductId: String,
    val slotName: String,
    val extTimeId: String,
    val stock: Int,
    val totalStock: Int,
    val stockStatus: String,
    val resvRate: Int,
    var isSelect: Boolean = false
)