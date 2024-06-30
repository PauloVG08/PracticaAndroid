package com.example.pevgapplogin.apiservice

import com.example.pevgapplogin.models.LoginModel
import com.example.pevgapplogin.models.RegistroModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
interface AuthApiService {
    @POST("login")
    fun postLogin(@Body params: LoginModel): Call<ResponseBody>

    @POST("registrar")
    fun postRegistrar(@Body params: RegistroModel): Call<ResponseBody>
}