package com.kai.momentz.retrofit

import com.kai.momentz.model.request.RegisterRequest
import com.kai.momentz.model.response.RegisterResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("user/register")
    suspend fun registerUser(
        @Part request: RegisterRequest,
    ) : Response<RegisterResponse>
}