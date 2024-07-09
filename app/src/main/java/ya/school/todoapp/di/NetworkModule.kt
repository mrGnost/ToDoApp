package ya.school.todoapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ya.school.todoapp.BuildConfig
import ya.school.todoapp.data.network.TodoApi
import javax.inject.Singleton

/**
 * Модуль для настройки клиента для взаимодействия с API бэкенда
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideRecommendationApi(
        client: OkHttpClient,
        converterFactory: Converter.Factory
    ): TodoApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(converterFactory)
        .build()
        .create(TodoApi::class.java)

    @Provides
    @Singleton
    fun provideOkHttp(
        interceptors: Set<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient {
        var builder = OkHttpClient()
            .newBuilder()
        for (interceptor in interceptors) {
            builder = builder.addInterceptor(interceptor)
        }
        return builder.build()
    }

    @Provides
    fun provideConverterFactory(): Converter.Factory = GsonConverterFactory.create()

    @Provides
    @IntoSet
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        )

    @Provides
    @IntoSet
    fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $AUTH_TOKEN")
            .build()
        chain.proceed(newRequest)
    }


    companion object {
        private const val BASE_URL = "https://hive.mrdekk.ru/todo/"
        private const val AUTH_TOKEN = "Amrod"
    }
}