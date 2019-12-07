package space.chuumong.bookticket.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import space.chuumong.bookticket.R
import space.chuumong.bookticket.data.model.Course
import space.chuumong.bookticket.data.model.TimeStock
import space.chuumong.bookticket.event.SelectTimeStockEvent
import space.chuumong.bookticket.ui.view.CourseView
import space.chuumong.bookticket.utils.RxBus

class TimeCourseAdapter(private val onClick: (Course, Int) -> Unit) :
    RecyclerView.Adapter<TimeCourseAdapter.TimeCourseViewHolder>() {

    private val courses = mutableListOf<Course>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeCourseViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_time_course, parent, false)
        return TimeCourseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    override fun onBindViewHolder(holder: TimeCourseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int): Course {
        return courses[position]
    }

    fun addAll(items: List<Course>) {
        courses.clear()
        courses.addAll(items)
        notifyDataSetChanged()
    }

    inner class TimeCourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTime = view.findViewById<TextView>(R.id.tv_time)
        private val llCourse = view.findViewById<LinearLayout>(R.id.ll_course)

        fun bind(item: Course) {
            tvTime.text = item.time
            llCourse.removeAllViews()

            item.timeStocks.forEach { timeStock ->
                val view = CourseView(itemView.context)
                llCourse.addView(view)
                view.setLayoutParams(llCourse.childCount > 1)
                view.timeStock = timeStock

                view.setOnClickListener {
                    item.timeStocks.forEach {
                        if (it.extTimeId == timeStock.extTimeId) {
                            it.isSelect = !it.isSelect
                        } else {
                            it.isSelect = false
                        }
                    }

                    notifyItemChanged(adapterPosition)
                    onClick(item, adapterPosition)
                }
            }
        }
    }
}