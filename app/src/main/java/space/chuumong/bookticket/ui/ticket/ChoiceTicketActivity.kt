package space.chuumong.bookticket.ui.ticket

import android.os.Bundle
import androidx.lifecycle.Observer
import org.koin.android.viewmodel.ext.android.getViewModel
import space.chuumong.bookticket.R
import space.chuumong.bookticket.data.model.TicketType
import space.chuumong.bookticket.databinding.ActivityChoiceTicketBinding
import space.chuumong.bookticket.ui.BaseActivity
import space.chuumong.bookticket.ui.time.ChoiceTimeCourseActivity
import space.chuumong.bookticket.viewmodel.ChoiceTicketViewModel

class ChoiceTicketActivity : BaseActivity<ActivityChoiceTicketBinding>() {

    override fun getLayoutId() = R.layout.activity_choice_ticket

    private val choiceTicketViewModel: ChoiceTicketViewModel by lazy { getViewModel() as ChoiceTicketViewModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.choiceTicketViewModel = choiceTicketViewModel

        choiceTicketViewModel.onClickOneTimeTicket.observe(this, Observer {
            startChoiceTimeActivity(TicketType.ONE)
        })

        choiceTicketViewModel.onClickThreeTimeTicket.observe(this, Observer {
            startChoiceTimeActivity(TicketType.THREE)
        })

        choiceTicketViewModel.onClickMultipleTimeTicket.observe(this, Observer {
            startChoiceTimeActivity(TicketType.MULTIPLE)
        })
    }

    private fun startChoiceTimeActivity(type: TicketType) {
        ChoiceTimeCourseActivity.start(this, type)
    }
}
