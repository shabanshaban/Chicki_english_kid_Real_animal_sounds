package com.farad.entertainment.kidsanimalenglish.data.manager.responce

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class BaseResponse<T>(
    @field:Json(name = "Message")
    val message: String?,
    @field:Json(name = "Data")
    val data: T?,
)

@Keep
data class MessageServer(
    @field:Json(name = "ShowType")
    val showType: ShowMessageType? = ShowMessageType.Toast,
    @field:Json(name = "Status")
    val status: Status = Status.Error,
    @field:Json(name = "Text")
    val text: String = "Error Server",
    @Transient
    val offline: Boolean = false,
)

@Keep
enum class Status {
    @field:Json(name="Success")
    Success,
    @field:Json(name="Error")
    Error,
    @field:Json(name="Info")
    Info,
    @field:Json(name="Warning")
    Warning,
    @field:Json(name="Unknown")
    Unknown,
}

@Keep
enum class ShowMessageType {
    @field:Json(name="Dialog")
    Dialog,
    @field:Json(name="Toast")
    Toast,
    @field:Json(name="SnackBar")
    SnackBar,
    @field:Json(name="None")
    None,
}