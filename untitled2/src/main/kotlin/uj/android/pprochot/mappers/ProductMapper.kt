package uj.android.pprochot.mappers

import uj.android.pprochot.models.dto.catalog.CategoryResponse
import uj.android.pprochot.models.dto.product.ProductResponse
import uj.android.pprochot.models.entity.Product

class ProductMapper {
    fun toResponse(product: Product): ProductResponse {
        val category = product.category
        return ProductResponse(
            product.id.value,
            product.name,
            product.description,
            product.cost,
            CategoryResponse(category.id.value, category.name, category.description)
        )
    }
}