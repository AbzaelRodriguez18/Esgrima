package com.example.esgrima

import kotlin.math.pow
import kotlin.math.roundToInt

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()

actual fun formatDouble(value: Double, decimals: Int): String {
    val multiplier = 10.0.pow(decimals)
    val rounded = (value * multiplier).roundToInt().toDouble() / multiplier
    val s = rounded.toString()
    val dotIndex = s.indexOf('.')
    return if (dotIndex == -1) {
        s + "." + "0".repeat(decimals)
    } else {
        val currentDecimals = s.length - dotIndex - 1
        if (currentDecimals < decimals) {
            s + "0".repeat(decimals - currentDecimals)
        } else {
            s
        }
    }
}
