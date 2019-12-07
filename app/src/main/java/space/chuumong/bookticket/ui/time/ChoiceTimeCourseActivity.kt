package space.chuumong.bookticket.ui.time

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.viewmodel.ext.android.getViewModel
import space.chuumong.bookticket.R
import space.chuumong.bookticket.data.Result
import space.chuumong.bookticket.data.model.Course
import space.chuumong.bookticket.data.model.TicketType
import space.chuumong.bookticket.data.model.TimeCourseInfo
import space.chuumong.bookticket.data.model.TimeStock
import space.chuumong.bookticket.databinding.ActivityChoiceTimeCourseBinding
import space.chuumong.bookticket.ui.BaseActivity
import space.chuumong.bookticket.ui.adapter.TimeCourseAdapter
import space.chuumong.bookticket.ui.view.showNoTitleTwoButtonsDialog
import space.chuumong.bookticket.utils.DateUtils
import space.chuumong.bookticket.viewmodel.TimeCourseViewModel

class ChoiceTimeCourseActivity : BaseActivity<ActivityChoiceTimeCourseBinding>() {

    companion object {
        private const val TAG = "ChoiceTimeCourse"

        private const val EXTRA_TYPE = "EXTRA_TYPE"

        fun start(activity: Activity, type: TicketType) {
            val intent = Intent(activity, ChoiceTimeCourseActivity::class.java)
            intent.putExtra(EXTRA_TYPE, type)
            activity.startActivity(intent)
        }
    }

    override fun getLayoutId() = R.layout.activity_choice_time_course

    private val timeCourseViewModel: TimeCourseViewModel by lazy { getViewModel() as TimeCourseViewModel }

    private val timeCourseAdapter = TimeCourseAdapter { course, position ->
        selectTimeStock(course, position)
    }

    private val type: TicketType by lazy { intent.getSerializableExtra(EXTRA_TYPE) as TicketType }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.timeCourseViewModel = timeCourseViewModel
        binding.tvDate.text = DateUtils.getKoreanCurrentDateWithWeekOfDay()

        binding.rvTime.itemAnimator = null
        binding.rvTime.layoutManager = LinearLayoutManager(this)
        binding.rvTime.adapter = timeCourseAdapter

        timeCourseViewModel.onClickPrev.observe(this, Observer { finish() })
        timeCourseViewModel.onClickNext.observe(this, Observer {
            showSelectTimeCourse()
        })

        getTimeCourseInfo()
    }

    private fun getTimeCourseInfo() {
        timeCourseViewModel.getTimeCourseInfo(
            DateUtils.getCurrentDate(),
            type,
            object : Result<TimeCourseInfo> {
                override fun onSuccess(result: TimeCourseInfo) {
                    timeCourseAdapter.addAll(result.courses)
                }

                override fun onFail(t: Throwable) {
                    Log.e(TAG, t.message, t)
                    showNoTitleTwoButtonsDialog(
                        getString(R.string.network_error_retry),
                        getString(R.string.retry), {
                            getTimeCourseInfo()
                        },
                        getString(android.R.string.cancel), {
                            finish()
                        })
                }
            })
    }

    private fun showSelectTimeCourse() {
        when (type) {
            TicketType.ONE, TicketType.THREE -> {
                Log.d(TAG, timeCourseViewModel.getSelectTimeCourse().toString())
            }
            TicketType.MULTIPLE -> {
                Log.d(TAG, timeCourseViewModel.getSelectTimeCourses().toString())
            }
        }
    }

    private fun selectTimeStock(course: Course, position: Int) {
        when (type) {
            TicketType.ONE, TicketType.THREE -> {
                timeCourseViewModel.selectOneAndThreeTimeStock(course, position)
            }
            TicketType.MULTIPLE -> {
                timeCourseViewModel.selectMultipleTimeStock(course)
            }
        }
    }
}