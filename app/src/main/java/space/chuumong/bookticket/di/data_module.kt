package space.chuumong.bookticket.di

import org.koin.dsl.module
import space.chuumong.bookticket.data.repositories.TicketTimeCourseRepository
import space.chuumong.bookticket.data.repositories.mapper.TicketTimeCourseMapper

val dataModule = module {
    factory { TicketTimeCourseMapper(get()) }
    
    factory { TicketTimeCourseRepository(get(), get()) }
}