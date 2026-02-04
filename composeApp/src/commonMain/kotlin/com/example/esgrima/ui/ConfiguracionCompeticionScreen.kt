package com.example.esgrima.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.esgrima.model.Competicion
import com.example.esgrima.model.Arma

@Composable
fun ConfiguracionCompeticionScreen(competicion: Competicion) {
    var nombre by remember { mutableStateOf(competicion.nombre) }
    var organizador by remember { mutableStateOf(competicion.organizador) }
    var fecha by remember { mutableStateOf(competicion.fecha) }
    var lugar by remember { mutableStateOf(competicion.lugar) }
    var armaSeleccionada by remember { mutableStateOf(competicion.arma) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Configuración de la Competición", style = MaterialTheme.typography.headlineMedium)
        
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it; competicion.nombre = it },
            label = { Text("Nombre de la Competición") },
            modifier = Modifier.fillMaxWidth()
        )
        
        OutlinedTextField(
            value = organizador,
            onValueChange = { organizador = it; competicion.organizador = it },
            label = { Text("Entidad Organizadora") },
            modifier = Modifier.fillMaxWidth()
        )
        
        OutlinedTextField(
            value = fecha,
            onValueChange = { fecha = it; competicion.fecha = it },
            label = { Text("Fecha") },
            modifier = Modifier.fillMaxWidth()
        )
        
        OutlinedTextField(
            value = lugar,
            onValueChange = { lugar = it; competicion.lugar = it },
            label = { Text("Lugar") },
            modifier = Modifier.fillMaxWidth()
        )

        Box {
            OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
                Text("Arma: ${armaSeleccionada.nombre}")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                Arma.values().forEach { arma ->
                    DropdownMenuItem(
                        text = { Text(arma.nombre) },
                        onClick = {
                            armaSeleccionada = arma
                            competicion.arma = arma
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
