package com.github.pprochot.uj.android.mappers

data class MapperContainer(
    val userMapper: UserMapper,
    val categoryMapper: CategoryMapper,
    val productMapper: ProductMapper,
    val cartMapper: CartMapper,
    val orderMapper: OrderMapper
)
