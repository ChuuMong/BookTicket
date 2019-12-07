package space.chuumong.bookticket.databinding

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import space.chuumong.bookticket.R
import space.chuumong.bookticket.utils.empty

@BindingAdapter("android:layout_marginBottom")
fun setMarginBottom(view: View, marginBottom: Float?) {
    marginBottom ?: return

    val params = view.layoutParams as ViewGroup.MarginLayoutParams
    params.bottomMargin = Math.round(marginBottom)
    view.layoutParams = params
}

@BindingAdapter("buttonToSelectTimeCourse")
fun setButtonToSelectTimeCourse(btn: Button, isSelectTicket: Boolean?) {
    isSelectTicket ?: return

    btn.isEnabled = isSelectTicket
    if (isSelectTicket) {
        btn.setBackgroundColor(ContextCompat.getColor(btn.context, R.color.yellow_01))
        btn.text = btn.context.getString(R.string.next)
    } else {
        btn.setBackgroundColor(ContextCompat.getColor(btn.context, android.R.color.darker_gray))
        btn.text = btn.context.getString(R.string.choice_time_course_select_time)
    }
}

@BindingAdapter("textToSelectTicketCount")
fun setTextToSelectTicketCount(tv: TextView, count: Int?) {
    count ?: return

    if (count == 0) {
        tv.text = String.empty()
    } else {
        tv.text = String.format(
            tv.context.getString(R.string.choice_time_course_select_ticket_count_format),
            count
        )
    }
}

@BindingAdapter("beforeSelectPositionNotifyItemChanged")
fun setBeforeSelectPositionNotifyItemChanged(rv: RecyclerView, position: Int?) {
    position ?: return
    if (position == -1) {
        return
    }

    rv.adapter?.notifyItemChanged(position)
}