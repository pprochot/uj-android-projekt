package uj.android.pprochot.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class ErrorDto(val type: String, val message: String?)