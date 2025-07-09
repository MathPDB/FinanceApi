package com.example.financeapi.data

data class Asset(
    val ticker: String,
    val name: String,
    val currency: String,
    val price: Double,
    val day_high: Double,
    val day_low: Double,
    val day_open: Double,
    val day_change: Double
)
