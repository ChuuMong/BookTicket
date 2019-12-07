package space.chuumong.bookticket.di

import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import space.chuumong.bookticket.BuildConfig.BASE_URL
import space.chuumong.bookticket.data.remote.api.ApiService
import space.chuumong.bookticket.data.remote.utils.NetworkErrorInterceptor

private const val OKHTTP_CLIENT = "OKHTTP_CLIENT"
const val LOGGING_INTERCEPTOR = "LOGGING_INTERCEPTOR"
const val ERROR_INTERCEPTOR = "ERROR_INTERCEPTOR"

private val apiModule = module {
    single {
        Retrofit.Builder().client(get<OkHttpClient>(named(OKHTTP_CLIENT)))
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(ApiService::class.java)
    }

    single(named(OKHTTP_CLIENT)) {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>(named(LOGGING_INTERCEPTOR)))
            .addInterceptor(get<NetworkErrorInterceptor>(named(ERROR_INTERCEPTOR)))
            .build()
    }

    single(named(ERROR_INTERCEPTOR)) {
        NetworkErrorInterceptor()
    }

    single(named(LOGGING_INTERCEPTOR)) {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        GsonBuilder().setPrettyPrinting().create()
    }
}

val appModule = listOf(apiModule, dataModule, viewModelModule)