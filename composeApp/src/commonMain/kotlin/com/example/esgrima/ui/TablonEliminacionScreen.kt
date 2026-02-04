package com.example.esgrima.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.esgrima.model.Competicion
import com.example.esgrima.model.Tirador

@Composable
fun TablonEliminacionScreen(competicion: Competicion) {
    val clasificacion = calcularClasificacion(competicion)
    
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Tablón de Eliminación Directa", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        if (clasificacion.isEmpty()) {
            Text("La clasificación aún no está lista. Complete los resultados de las poules.")
        } else {
            val numTiradores = clasificacion.size
            val tamañoTablon = calcularTamañoTablon(numTiradores)
            
            Text("Tablón de $tamañoTablon", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))

            val emparejamientos = generarEmparejamientosTablon(clasificacion.map { it.tirador }, tamañoTablon)

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(emparejamientos) { pareja ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                Text(pareja.first?.nombre ?: "EXENTO", style = MaterialTheme.typography.bodyLarge)
                                Text("vs", style = MaterialTheme.typography.bodySmall)
                                Text(pareja.second?.nombre ?: "EXENTO", style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun calcularTamañoTablon(n: Int): Int {
    var size = 2
    while (size < n) {
        size *= 2
    }
    return size
}

fun generarEmparejamientosTablon(tiradores: List<Tirador>, tamaño: Int): List<Pair<Tirador?, Tirador?>> {
    val listaCompleta = arrayOfNulls<Tirador>(tamaño)
    for (i in tiradores.indices) {
        listaCompleta[i] = tiradores[i]
    }

    val emparejamientos = mutableListOf<Pair<Tirador?, Tirador?>>()
    // Lógica básica de emparejamiento: 1 vs último, 2 vs penúltimo...
    for (i in 0 until tamaño / 2) {
        emparejamientos.add(Pair(listaCompleta[i], listaCompleta[tamaño - 1 - i]))
    }
    return emparejamientos
}
