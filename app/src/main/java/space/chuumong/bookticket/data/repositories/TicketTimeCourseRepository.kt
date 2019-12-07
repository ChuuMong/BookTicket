package space.chuumong.bookticket.data.repositories

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import space.chuumong.bookticket.data.model.TicketType
import space.chuumong.bookticket.data.model.TimeCourseInfo
import space.chuumong.bookticket.data.remote.api.ApiService
import space.chuumong.bookticket.data.repositories.mapper.TicketTimeCourseMapper

class TicketTimeCourseRepository(
    private val apiService: ApiService,
    private val mapper: TicketTimeCourseMapper
) {
    
    fun getTimeCourseInfo(date: String, type: TicketType): Single<TimeCourseInfo> {
        return apiService.getTimeCourseInfo(date, type.value)
            .map(mapper.toTimeCourseInfoEntity())
            .observeOn(AndroidSchedulers.mainThread())
    }
}