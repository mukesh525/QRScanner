package com.example.gurleensethi.barcodedemo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface Request {

    @GET
    fun performAction(@Url url: String): Call<String>

}

