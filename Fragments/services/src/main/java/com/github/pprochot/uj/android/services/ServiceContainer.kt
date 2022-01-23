package com.github.pprochot.uj.android.services

data class ServiceContainer(
    val userService: UserService,
    val categoryService: CategoryService,
    val productService: ProductService,
    val cartService: CartService,
    val orderService: OrderService
)
