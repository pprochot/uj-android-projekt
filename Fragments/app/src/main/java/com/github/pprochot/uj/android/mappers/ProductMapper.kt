package com.github.pprochot.uj.android.mappers

import com.github.pprochot.uj.android.domain.response.ProductResponse
import com.github.pprochot.uj.android.realmmodels.Category
import com.github.pprochot.uj.android.realmmodels.Money
import com.github.pprochot.uj.android.realmmodels.Product
import javax.inject.Inject

class ProductMapper @Inject constructor(private val categoryMapper: CategoryMapper) :
    RealmModelMapper<ProductResponse, Product> {

    override fun mapList(products: List<ProductResponse>): List<Product> {
        return products.map { map(it) }.toList()
    }

    override fun map(productResponse: ProductResponse): Product {
        val product = Product()
        product.id = productResponse.id
        product.name = productResponse.name
        product.description = productResponse.description
        product.cost = Money(productResponse.cost)
        product.category = categoryMapper.map(productResponse.category)
        return product
    }
}