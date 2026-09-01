package org.moveitcapital.simcap.data.remote.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.moveitcapital.simcap.core.AppResponse
import org.moveitcapital.simcap.core.ResultHandler
import org.moveitcapital.simcap.core.map
import org.moveitcapital.simcap.core.safeCall
import org.moveitcapital.simcap.data.remote.request.UpdatePasswordRequest
import org.moveitcapital.simcap.data.remote.response.ProfileApiResponse
import org.moveitcapital.simcap.domain.mapper.toDomain
import org.moveitcapital.simcap.domain.model.Profile

class AccountService (private val client: HttpClient){

    suspend fun updatePassword(
        token: String,
        request: UpdatePasswordRequest
    ): ResultHandler<String> = safeCall {
        client.post("/TokenDS-0.0.1/api/v1/administracion/cambioPassword") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body<AppResponse>()
    }.map { it.message }

    suspend fun getProfile(
        token: String,
        userId: Int,
        companyId: Int,
    ): ResultHandler<Profile> = safeCall {
        client.get("Administracion-1.0/api/v1/administracion/personaPerfil/$userId/$companyId") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
        }.body<ProfileApiResponse>()
    }.map { it.toDomain() }
}