package space.chuumong.bookticket.di

import org.koin.dsl.module
import space.chuumong.bookticket.viewmodel.ChoiceTicketViewModel
import space.chuumong.bookticket.viewmodel.TimeCourseViewModel

val viewModelModule = module {
    factory { ChoiceTicketViewModel() }

    factory { TimeCourseViewModel(get()) }
}