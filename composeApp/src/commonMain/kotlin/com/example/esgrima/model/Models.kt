package com.example.esgrima.model

import androidx.compose.runtime.*

enum class Arma(val nombre: String) {
    ESPADA("Espada"), FLORETE("Florete"), SABLE("Sable")
}

data class Tirador(
    val id: String = "",
    val nombre: String = "",
    val club: String = "",
    val numeroLicencia: String = ""
)

data class Arbitro(
    val id: String = "",
    val nombre: String = "",
    val numeroLicencia: String = "",
    val especialidades: List<Arma> = emptyList()
)

class Asalto(
    val tirador1: Tirador,
    val tirador2: Tirador,
    arbitro: Arbitro? = null,
    pista: String? = null
) {
    var tocados1 by mutableStateOf(0)
    var tocados2 by mutableStateOf(0)
    var arbitro by mutableStateOf(arbitro)
    var pista by mutableStateOf(pista)
    var terminado by mutableStateOf(false)
}

class Poule(
    val id: Int,
    val tiradores: List<Tirador>
) {
    val asaltos = mutableStateListOf<Asalto>()
    var arbitro by mutableStateOf<Arbitro?>(null)
    var pista by mutableStateOf<String?>(null)
}

class Competicion {
    var nombre by mutableStateOf("")
    var organizador by mutableStateOf("")
    var fecha by mutableStateOf("")
    var lugar by mutableStateOf("")
    var arma by mutableStateOf(Arma.ESPADA)
    val tiradores = mutableStateListOf<Tirador>()
    val arbitros = mutableStateListOf<Arbitro>()
    val poules = mutableStateListOf<Poule>()
}
