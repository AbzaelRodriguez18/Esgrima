package com.example.esgrima.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.esgrima.model.*

data class ConfiguracionPoule(
    val numPoules: Int,
    val descripcion: String,
    val distribucion: List<Int>
)

@Composable
fun CalculadoraPoulesScreen(competicion: Competicion) {
    val totalTiradores = competicion.tiradores.size
    var opciones by remember(totalTiradores) { mutableStateOf(calcularOpcionesPoules(totalTiradores)) }
    var listaActualizada by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text("Generación de Poules", style = MaterialTheme.typography.headlineMedium)
        Text("Tiradores inscritos: $totalTiradores", style = MaterialTheme.typography.bodyMedium)
        
        Spacer(modifier = Modifier.height(16.dp))

        if (totalTiradores < 3) {
            Text("Se necesitan al menos 3 tiradores para generar poules.", color = MaterialTheme.colorScheme.error)
        } else {
            Text("Selecciona una configuración:", style = MaterialTheme.typography.titleMedium)
            LazyColumn(modifier = Modifier.weight(0.4f)) {
                items(opciones) { opcion ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (competicion.poules.size == opcion.numPoules) 
                                MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("${opcion.numPoules} poules", style = MaterialTheme.typography.titleSmall)
                                Text(opcion.descripcion, style = MaterialTheme.typography.bodySmall)
                            }
                            Button(onClick = {
                                generarPoules(competicion, opcion)
                                listaActualizada++
                            }) {
                                Text("Generar")
                            }
                        }
                    }
                }
            }
        }

        if (competicion.poules.isNotEmpty()) {
            key(listaActualizada) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Asignación de Árbitros y Pistas:", style = MaterialTheme.typography.titleMedium)
                LazyColumn(modifier = Modifier.weight(0.6f)) {
                    items(competicion.poules) { poule ->
                        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Poule #${poule.id} (${poule.tiradores.size} tiradores)", style = MaterialTheme.typography.titleSmall)
                                
                                var expanded by remember { mutableStateOf(false) }
                                Box(modifier = Modifier.padding(vertical = 4.dp)) {
                                    OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
                                        Text(poule.arbitro?.nombre ?: "Asignar Árbitro")
                                    }
                                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                        competicion.arbitros.forEach { arb ->
                                            DropdownMenuItem(
                                                text = { Text(arb.nombre) },
                                                onClick = {
                                                    poule.arbitro = arb
                                                    poule.asaltos.forEach { it.arbitro = arb }
                                                    expanded = false
                                                    listaActualizada++
                                                }
                                            )
                                        }
                                    }
                                }
                                
                                OutlinedTextField(
                                    value = poule.pista ?: "",
                                    onValueChange = { 
                                        poule.pista = it
                                        poule.asaltos.forEach { asalto -> asalto.pista = it }
                                        listaActualizada++
                                    },
                                    label = { Text("Número de Pista") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun generarPoules(competicion: Competicion, configuracion: ConfiguracionPoule) {
    competicion.poules.clear()
    val tiradoresMezclados = competicion.tiradores.shuffled()
    var indice = 0
    
    configuracion.distribucion.forEachIndexed { i, tamano ->
        val tiradoresPoule = tiradoresMezclados.subList(indice, indice + tamano)
        val nuevaPoule = Poule(i + 1, tiradoresPoule.toList())

        for (idx1 in 0 until tiradoresPoule.size) {
            for (idx2 in idx1 + 1 until tiradoresPoule.size) {
                nuevaPoule.asaltos.add(Asalto(tiradoresPoule[idx1], tiradoresPoule[idx2]))
            }
        }
        
        competicion.poules.add(nuevaPoule)
        indice += tamano
    }
}

fun calcularOpcionesPoules(total: Int): List<ConfiguracionPoule> {
    if (total < 3) return emptyList()
    val opciones = mutableListOf<ConfiguracionPoule>()
    for (n in 1..total/3) {
        val base = total / n
        val resto = total % n
        if (base in 5..9 || (n == 1 && total >= 3)) {
            val dist = List(n) { i -> base + (if (i < resto) 1 else 0) }
            val desc = if (resto == 0) "$n grupos de $base" else "$resto de ${base+1} y ${n-resto} de $base"
            opciones.add(ConfiguracionPoule(n, desc, dist))
        }
    }
    return opciones.sortedByDescending { it.numPoules }
}
