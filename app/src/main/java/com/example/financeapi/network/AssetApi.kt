package com.example.financeapi.network

//import com.example.financeapi.data.Asset
import com.example.financeapi.data.AssetResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

const val BASE_URL = "https://api.stockdata.org/v1/data/"

//quem converte o Json
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//quem faz a requisição
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface AssetApiService {

    //@GET("quote?symbols=AAPL,TSLA,MSFT&api_token=zm6829VatkP1dSzl10tnDTCt8w1F7TWxyJRDuKwo")
    //suspend fun getAssets(): AssetResponse
    @GET("quote")
    suspend fun getAssets(@QueryMap options: Map<String, String>): AssetResponse
}

object AssetApi {
    val retrofitService: AssetApiService by lazy {
        retrofit.create(AssetApiService::class.java)
    }
}