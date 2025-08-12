package com.htr.htrr.data.remote

import com.htr.htrr.domain.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("user")
    suspend fun getUser(): Response<User>
}