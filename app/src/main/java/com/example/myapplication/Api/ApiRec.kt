package com.example.myapplication.Api

import com.example.myapplication.Model.News
import retrofit2.Call
import retrofit2.http.GET

interface ApiRec {
    @GET("api/News")
    fun GetNews(): Call <List<News>>
}