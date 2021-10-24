package eu.anifantakis.elections.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import eu.anifantakis.elections.Constants
import eu.anifantakis.elections.NetworkElecResult
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

enum class NETWORK_STATUS {INITIALIZING, CONNECTED, DISCONNECTED}

interface RetrofitService {
    @GET("exporter/api/tabular_new.php")
    suspend fun getElecResults(
        @Query("get") get: String,
        @Query("where") where: String,
        @Query("is") has_value: String,
        @Query("format") format: String,
        @Query("elecid") elecid: String,
        @Query("api_key") apiKey: String
    ): List<NetworkElecResult>
}

object Network{
    private val okHttpClient: OkHttpClient by lazy{
        OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // configure retrofit to parse JSON
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val retrofitService: RetrofitService by lazy{
        retrofit.create(RetrofitService::class.java)
    }
}