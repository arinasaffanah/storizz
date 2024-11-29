package com.dicoding.picodiploma.loginwithanimation.data.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(
    val error: Boolean,
    val message: String,
    val loginResult: LoginResult?
)

data class LoginResult(
    val userId: String,
    val name: String,
    val token: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)


interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("register")
    fun register(@Body registerRequest: RegisterRequest): Call<Unit>
}
