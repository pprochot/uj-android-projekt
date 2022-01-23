package com.github.pprochot.uj.android.services

import com.github.pprochot.uj.android.services.ServiceConfiguration.SERVICE_URL
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServicesModule {

    @Provides
    @Singleton
    fun gsonConverterFactory(): GsonConverterFactory {
        val localDateTimeDeserializer = JsonDeserializer { json, typeOfT, context ->
            LocalDateTime.ofInstant(Instant.ofEpochMilli(json.asLong), ZoneId.systemDefault())
        }
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, localDateTimeDeserializer)
            .create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun retrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun userService(retrofit: Retrofit): UserService = retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun categoryService(retrofit: Retrofit): CategoryService =
        retrofit.create(CategoryService::class.java)

    @Provides
    @Singleton
    fun productService(retrofit: Retrofit): ProductService =
        retrofit.create(ProductService::class.java)

    @Provides
    @Singleton
    fun cartService(retrofit: Retrofit): CartService = retrofit.create(CartService::class.java)

    @Provides
    @Singleton
    fun orderService(retrofit: Retrofit): OrderService = retrofit.create(OrderService::class.java)

    @Provides
    @Singleton
    fun serviceContainer(
        userService: UserService,
        categoryService: CategoryService,
        productService: ProductService,
        cartService: CartService,
        orderService: OrderService
    ): ServiceContainer {
        return ServiceContainer(
            userService,
            categoryService,
            productService,
            cartService,
            orderService
        )
    }
}