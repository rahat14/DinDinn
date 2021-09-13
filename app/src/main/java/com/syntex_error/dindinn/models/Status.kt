package com.syntex_error.dindinn.models

data class Status(
    val message: String,
    val statusCode: Int,
    val success: Boolean
)