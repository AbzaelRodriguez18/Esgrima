package com.example.esgrima.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.esgrima.formatDouble
import com.example.esgrima.model.Competicion
import com.example.esgrima.model.Tirador

data class ResultadoTirador(
    val tirador: Tirador,
    val victorias: Int,
    val asaltos: Int,
    val dados: Int,
    val recibidos: Int
) {
    val indice: Double = if (asaltos > 0) victorias.toDouble() / asaltos else 0.0
    val diferencia: Int = dados - recibidos
}

@Composable
fun ClasificacionScreen(competicion: Competicion) {
    val resultados = calcularClasificacion(competicion)

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Clasificación Post-Poules", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        if (resultados.isEmpty()) {
            Text("No hay resultados suficientes para generar la clasificación.")
        } else {
            LazyColumn {
                item {
                    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                        Text("Pos", modifier = Modifier.width(40.dp), style = MaterialTheme.typography.labelLarge)
                        Text("Nombre", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelLarge)
                        Text("V/M", modifier = Modifier.width(60.dp), style = MaterialTheme.typography.labelLarge)
                        Text("TD-TR", modifier = Modifier.width(60.dp), style = MaterialTheme.typography.labelLarge)
                    }
                    Divider()
                }
                itemsIndexed(resultados) { index, res ->
                    ListItem(
                        headlineContent = { Text("${index + 1}. ${res.tirador.nombre}") },
                        supportingContent = { Text(res.tirador.club) },
                        trailingContent = { 
                            Row {
                                Text(formatDouble(res.indice, 3), modifier = Modifier.width(60.dp))
                                Text("${res.diferencia}", modifier = Modifier.width(40.dp))
                            }
                        }
                    )
                    Divider()
                }
            }
        }
    }
}

fun calcularClasificacion(competicion: Competicion): List<ResultadoTirador> {
    val mapaResultados = mutableMapOf<String, ResultadoTirador>()

    competicion.poules.forEach { poule ->
        poule.asaltos.forEach { asalto ->
            if (asalto.terminado || (asalto.tocados1 > 0 || asalto.tocados2 > 0)) {
                // Tirador 1
                val res1 = mapaResultados.getOrPut(asalto.tirador1.id) { ResultadoTirador(asalto.tirador1, 0, 0, 0, 0) }
                mapaResultados[asalto.tirador1.id] = res1.copy(
                    victorias = res1.victorias + (if (asalto.tocados1 > asalto.tocados2) 1 else 0),
                    asaltos = res1.asaltos + 1,
                    dados = res1.dados + asalto.tocados1,
                    recibidos = res1.recibidos + asalto.tocados2
                )

                // Tirador 2
                val res2 = mapaResultados.getOrPut(asalto.tirador2.id) { ResultadoTirador(asalto.tirador2, 0, 0, 0, 0) }
                mapaResultados[asalto.tirador2.id] = res2.copy(
                    victorias = res2.victorias + (if (asalto.tocados2 > asalto.tocados1) 1 else 0),
                    asaltos = res2.asaltos + 1,
                    dados = res2.dados + asalto.tocados2,
                    recibidos = res2.recibidos + asalto.tocados1
                )
            }
        }
    }

    return mapaResultados.values.sortedWith(
        compareByDescending<ResultadoTirador> { it.indice }
            .thenByDescending { it.diferencia }
            .thenByDescending { it.dados }
    )
}
