package com.github.pprochot.uj.android.services

import com.github.pprochot.uj.android.services.ServiceConfiguration.SERVICE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServicesModule {

    @Provides
    @Singleton
    fun userService() = createService<UserService>()

    @Provides
    @Singleton
    fun categoryService() = createService<CategoryService>()

    @Provides
    @Singleton
    fun productService() = createService<ProductService>()

    @Provides
    @Singleton
    fun cartService() = createService<CartService>()

    @Provides
    @Singleton
    fun orderService() = createService<OrderService>()

    private inline fun <reified T> createService() : T {
        return Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .build()
            .create(T::class.java)
    }
}