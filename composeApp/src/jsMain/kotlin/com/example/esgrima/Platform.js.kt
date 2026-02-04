package com.example.esgrima

class JsPlatform: Platform {
    override val name: String = "Web with Kotlin/JS"
}

actual fun getPlatform(): Platform = JsPlatform()

actual fun formatDouble(value: Double, decimals: Int): String {
    return value.asDynamic().toFixed(decimals).toString()
}
