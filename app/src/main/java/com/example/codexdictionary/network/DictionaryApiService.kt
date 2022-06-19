package com.example.codexdictionary.network

import com.example.codexdictionary.data.DictionaryWord
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/"

private val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply{
    level = HttpLoggingInterceptor.Level.BODY
}

private val client : OkHttpClient = OkHttpClient.Builder().apply{
    addInterceptor(interceptor)
}.build()






private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface DictionaryApiService{
    @GET("{word}")
    fun getWord(@Path("word") queriedWord : String) : Deferred<List<DictionaryWord>>
}

object DictionaryApi{
    val retrofitService : DictionaryApiService by lazy{
        retrofit.create(DictionaryApiService::class.java)
    }
}