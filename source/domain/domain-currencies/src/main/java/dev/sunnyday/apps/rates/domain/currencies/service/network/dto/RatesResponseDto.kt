package dev.sunnyday.apps.rates.domain.currencies.service.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class RatesResponseDto(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)