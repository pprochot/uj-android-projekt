package com.github.pprochot.uj.android.mappers

import com.github.pprochot.uj.android.domain.response.CategoryResponse
import com.github.pprochot.uj.android.realmmodels.Category

class CategoryMapper : RealmModelMapper<CategoryResponse, Category> {

    override fun mapList(categories: List<CategoryResponse>): List<Category> {
        return categories.map { map(it) }.toList()
    }

    override fun map(categoryResponse: CategoryResponse): Category {
        val category = Category()
        category.id = categoryResponse.id
        category.name = categoryResponse.name
        category.description = categoryResponse.description
        return category
    }
}