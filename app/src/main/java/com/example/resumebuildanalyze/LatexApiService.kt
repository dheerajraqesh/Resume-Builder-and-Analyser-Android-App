package com.example.resumebuildanalyze

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

data class LatexRequest(val latex_code: String)

interface LatexApiService {
    @Headers("Content-Type: application/json")
    @POST("/compile")
    fun compileLatex(@Body request: LatexRequest): Call<ResponseBody>
}
