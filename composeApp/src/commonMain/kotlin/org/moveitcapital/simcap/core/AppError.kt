package org.moveitcapital.simcap.core

import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.runBlocking
import kotlinx.io.IOException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class AppError : Throwable() {
    data class Network(override val message: String) : AppError()
    data class Timeout(override val message: String) : AppError()
    data class Http(val response: AppResponse) : AppError()
    data class Unknown(val error: Throwable?) : AppError()
    data class Custom(override val message: String) : AppError()
    data class Session(override val message: String) : AppError()
}

fun Throwable.toAppError(): AppError = when (this) {
    is AppError -> this
    is SocketTimeoutException -> AppError.Timeout(Exeptions.TIMEOUT)
    is ConnectTimeoutException, is IOException -> AppError.Network(Exeptions.NO_INTERNET)
    is ClientRequestException, is ServerResponseException -> {
        val error = getAppResponseOrError(this.response)
        if (error is AppError.Http && error.response.code == 401) {
            AppError.Session("La sesion expiró")
        } else error
    }
    else -> AppError.Unknown(this)
}

fun getAppResponseOrError(bodyProvider: HttpResponse): AppError = runBlocking {
    val response = bodyProvider.body<ErrorResponse>()
    response.status?.let {
        AppError.Http(response.toAppResponse())
    } ?: AppError.Http(bodyProvider.body<AppResponse>())
}

@Serializable
data class AppResponse(
    @SerialName("messageCode") val code: Int = 0,
    val message: String = "",
)

@Serializable
data class ErrorResponse(
    val status: Int? = null,
    val error: String = "",
) {
    fun toAppResponse() = AppResponse(
        code = status ?: 0,
        message = error
    )
}