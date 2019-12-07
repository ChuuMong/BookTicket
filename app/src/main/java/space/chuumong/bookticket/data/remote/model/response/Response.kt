package space.chuumong.bookticket.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: Any
)