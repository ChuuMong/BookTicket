package space.chuumong.bookticket.data.remote.api

import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Path
import space.chuumong.bookticket.data.remote.model.response.Response


interface ApiService {

    @POST("time_course/{date}/{ticketType}")
    fun getTimeCourseInfo(@Path("date") date: String, @Path("ticketType") type: Int): Single<Response>
}