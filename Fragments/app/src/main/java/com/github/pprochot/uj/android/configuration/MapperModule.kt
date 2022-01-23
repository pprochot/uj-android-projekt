package com.github.pprochot.uj.android.configuration

import com.github.pprochot.uj.android.mappers.CartMapper
import com.github.pprochot.uj.android.mappers.CategoryMapper
import com.github.pprochot.uj.android.mappers.MapperContainer
import com.github.pprochot.uj.android.mappers.OrderMapper
import com.github.pprochot.uj.android.mappers.ProductMapper
import com.github.pprochot.uj.android.mappers.UserMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {

    @Provides
    @Singleton
    fun userMapper(): UserMapper = UserMapper()

    @Provides
    @Singleton
    fun categoryMapper(): CategoryMapper = CategoryMapper()

    @Provides
    @Singleton
    fun productMapper(categoryMapper: CategoryMapper): ProductMapper = ProductMapper(categoryMapper)

    @Provides
    @Singleton
    fun cartMapper(userMapper: UserMapper, productMapper: ProductMapper): CartMapper =
        CartMapper(userMapper, productMapper)

    @Provides
    @Singleton
    fun orderMapper(userMapper: UserMapper, productMapper: ProductMapper): OrderMapper =
        OrderMapper(userMapper, productMapper)

    @Provides
    @Singleton
    fun mapperContainer(
        userMapper: UserMapper,
        categoryMapper: CategoryMapper,
        productMapper: ProductMapper,
        cartMapper: CartMapper,
        orderMapper: OrderMapper,
    ): MapperContainer =
        MapperContainer(userMapper, categoryMapper, productMapper, cartMapper, orderMapper)

}