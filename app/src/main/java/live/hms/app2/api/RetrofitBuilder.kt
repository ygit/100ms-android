package live.hms.app2.api

import live.hms.app2.BuildConfig
import live.hms.app2.util.crashlyticsLog
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TAG = "RetrofitBuilder"

private val okHttpClient = OkHttpClient.Builder()
  .addInterceptor(HttpLoggingInterceptor { crashlyticsLog(TAG, it) }.apply {
    level = HttpLoggingInterceptor.Level.BODY
  })
  .callTimeout(10, TimeUnit.SECONDS)
  .readTimeout(5, TimeUnit.SECONDS)
  .writeTimeout(5, TimeUnit.SECONDS)
  .build()

private fun getRetrofit() = Retrofit.Builder()
  .baseUrl(BuildConfig.TOKEN_ENDPOINT)
  .addConverterFactory(GsonConverterFactory.create())
  .client(okHttpClient)
  .build()

val TokenApiService: TokenService by lazy { getRetrofit().create(TokenService::class.java) }