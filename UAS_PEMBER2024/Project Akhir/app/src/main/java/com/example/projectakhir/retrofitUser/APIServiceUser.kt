package com.example.projectakhir.retrofitUser

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIServiceUser {

    @GET("travel")
    fun getAllTravel(): Call<List<APIResponseUser>>
}