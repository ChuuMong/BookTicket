package space.chuumong.bookticket.utils

import java.text.NumberFormat

fun Int.numberFormat(): String {
    return NumberFormat.getNumberInstance().format(this)
}