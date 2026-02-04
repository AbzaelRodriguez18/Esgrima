package com.example.esgrima.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PouleCalculatorScreen() {
    var numFencers by remember { mutableStateOf("") }
    var numTracks by remember { mutableStateOf("") }
    var pouleOptions by remember { mutableStateOf<List<Pair<Int, Int>>>(emptyList()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = numFencers,
            onValueChange = { numFencers = it },
            label = { Text("Número de tiradores") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = numTracks,
            onValueChange = { numTracks = it },
            label = { Text("Número de pistas") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val fencers = numFencers.toIntOrNull()
            if (fencers != null && fencers > 0) {
                pouleOptions = calculatePouleOptions(fencers)
            }
        }) {
            Text("Calcular Opciones de Poules")
        }
        Spacer(modifier = Modifier.height(16.dp))
        pouleOptions.forEach { (numPoules, fencersPerPoule) ->
            Text("Opción: $numPoules poules de $fencersPerPoule tiradores")
        }
    }
}

fun calculatePouleOptions(totalFencers: Int): List<Pair<Int, Int>> {
    val options = mutableListOf<Pair<Int, Int>>()
    for (numPoules in 1..totalFencers) {
        if (totalFencers % numPoules == 0) {
            val fencersPerPoule = totalFencers / numPoules
            options.add(Pair(numPoules, fencersPerPoule))
        }
    }
    return options
}
