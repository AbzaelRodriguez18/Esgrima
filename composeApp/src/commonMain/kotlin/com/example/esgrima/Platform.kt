package com.example.esgrima

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun formatDouble(value: Double, decimals: Int): String
