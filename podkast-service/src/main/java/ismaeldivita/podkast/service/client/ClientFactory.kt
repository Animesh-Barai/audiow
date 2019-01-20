package ismaeldivita.podkast.service.client

import ismaeldivita.podkast.service.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class ClientFactory @Inject constructor() {

    companion object {
        const val CONNECT_TIMEOUT_IN_SECOND: Long = 60
        const val WRITE_TIMEOUT_IN_SECOND: Long = 60
        const val READ_TIMEOUT_IN_SECOND: Long = 60
    }

    fun newInstance(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
        .addInterceptor(getLoggingInterceptor())
        .build()

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val logLevel = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
        return HttpLoggingInterceptor(Logger { Timber.d(it) }).apply { level = logLevel }
    }

}