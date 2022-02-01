package uj.android.pprochot.models.dto.catalog

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(val id: Int, val name: String, val description: String)