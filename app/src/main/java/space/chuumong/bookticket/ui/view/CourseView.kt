package space.chuumong.bookticket.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import space.chuumong.bookticket.R
import space.chuumong.bookticket.data.model.TimeStock

class CourseView(context: Context, attr: AttributeSet? = null) : FrameLayout(context, attr) {

    companion object {
        private const val LEFT_MARGIN = 4

        private const val COURSE_STATE_NORMAL = 1
        private const val COURSE_STATE_CONFUSION = 2
        private const val COURSE_STATE_SOLD_OUT = -1

    }

    private val tvCourse: TextView
    private val tvCourseState: TextView

    var timeStock: TimeStock? = null
        set(value) {
            value ?: return
            tvCourse.text = value.slotName
            tvCourseState.text = getCourseState(value.resvRate)
            isSelected = value.isSelect
            field = value
        }

    init {
        val view = View.inflate(context, R.layout.view_course, this)
        tvCourse = view.findViewById(R.id.tv_course)
        tvCourseState = view.findViewById(R.id.tv_course_state)
    }

    fun setLayoutParams(isSetLeftMargin: Boolean) {
        val layoutParams =
            LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        layoutParams.weight = 1f

        if (isSetLeftMargin) {
            layoutParams.leftMargin = LEFT_MARGIN.toPx()
        }

        super.setLayoutParams(layoutParams)
    }

    private fun getCourseState(state: Int): String {
        return when (state) {
            COURSE_STATE_NORMAL -> context.getString(R.string.choice_time_course_ticket_normal)
            COURSE_STATE_CONFUSION -> context.getString(R.string.choice_time_course_ticket_confusion)
            COURSE_STATE_SOLD_OUT -> context.getString(R.string.choice_time_course_ticket_sold_out)
            else -> context.getString(R.string.choice_time_course_ticket_clearance)
        }
    }
}
