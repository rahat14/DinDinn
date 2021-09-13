package com.syntex_error.dindinn.models

data class OrderResponse(
    val `data`: List<Orders>,
    val id: String,
    val status: Status
)