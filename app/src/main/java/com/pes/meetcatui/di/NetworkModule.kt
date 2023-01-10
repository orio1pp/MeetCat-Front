package com.pes.meetcatui.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pes.meetcatui.BuildConfig
import com.pes.meetcatui.network.MeetCatApi
import com.pes.meetcatui.network.MeetCatApiInterceptor
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private val json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
    encodeDefaults = true
}

@OptIn(ExperimentalSerializationApi::class)
val networkModule = module {

    single {
        get<Retrofit>().create(MeetCatApi::class.java)
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            //.addInterceptor(get<MeetCatApiInterceptor>())
            .callTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    single {
        MeetCatApiInterceptor(get(), get())
    }
}

private const val BASE_URL = "http://192.168.0.10:8080" //10.4.41.49:8080/"
private const val REQUEST_TIME_OUT = 30L
