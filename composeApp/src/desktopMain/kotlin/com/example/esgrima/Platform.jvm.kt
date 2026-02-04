package com.example.esgrima

class JvmPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")} on ${System.getProperty("os.name")}"
}

actual fun getPlatform(): Platform = JvmPlatform()

actual fun formatDouble(value: Double, decimals: Int): String {
    return "%.${decimals}f".format(value)
}
