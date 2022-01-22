package uj.android.pprochot.service

import org.jetbrains.exposed.sql.transactions.transaction
import uj.android.pprochot.exceptions.ResourceNotFoundException
import uj.android.pprochot.mappers.CategoryMapper
import uj.android.pprochot.models.dto.catalog.CategoryRequest
import uj.android.pprochot.models.dto.catalog.CategoryResponse
import uj.android.pprochot.models.dto.ListResponse
import uj.android.pprochot.models.entity.Category

class CategoryService(private val categoryMapper: CategoryMapper) : CrudService<CategoryRequest, CategoryResponse> {

    override fun create(request: CategoryRequest): CategoryResponse = transaction {
        val category = Category.new {
            name = request.name
            description = request.description
        }
        return@transaction CategoryResponse(category.id.value, category.name, category.description)
    }

    override fun getAll(): ListResponse<CategoryResponse> = transaction {
        val categories = Category.all()
            .map(categoryMapper::toResponse)
            .toList()
        return@transaction ListResponse(categories)
    }

    override fun getById(id: Int): CategoryResponse = transaction {
        val category = Category.findById(id)
            ?: throw ResourceNotFoundException("Catalog with id $id not found")
        return@transaction categoryMapper.toResponse(category)
    }

    override fun update(id: Int, request: CategoryRequest): CategoryResponse = transaction {
        val category = Category.findById(id)
            ?: throw ResourceNotFoundException("Catalog with id $id not found")
        category.apply {
            name = request.name
            description = request.description
        }
        return@transaction categoryMapper.toResponse(category)
    }

    override fun delete(id: Int) = transaction {
        val category = Category.findById(id)
            ?: throw ResourceNotFoundException("Catalog with id $id not found")
        category.delete()
    }
}