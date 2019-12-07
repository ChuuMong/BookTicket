package space.chuumong.bookticket.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"

    private const val KOREAN_DATE_WITH_WEEK_OF_DAY = "yyyy년 MM월 dd일 (E)"


    fun getCurrentDate(): String {
        val date = Calendar.getInstance().time
        return SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.KOREA).format(date)
    }

    fun getKoreanCurrentDateWithWeekOfDay(): String {
        val date = Calendar.getInstance().time
        return SimpleDateFormat(KOREAN_DATE_WITH_WEEK_OF_DAY, Locale.KOREA).format(date)
    }
}