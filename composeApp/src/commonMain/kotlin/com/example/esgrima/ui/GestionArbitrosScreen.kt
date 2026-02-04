package com.example.esgrima.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.esgrima.model.Arbitro
import com.example.esgrima.model.Arma

@Composable
fun GestionArbitrosScreen(arbitros: MutableList<Arbitro>) {
    var nombre by remember { mutableStateOf("") }
    var licencia by remember { mutableStateOf("") }
    val especialidadesSeleccionadas = remember { mutableStateListOf<Arma>() }
    var listaActualizada by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Gestión de Árbitros", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre Completo") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = licencia,
            onValueChange = { licencia = it },
            label = { Text("Número de Licencia") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Especialidades", modifier = Modifier.padding(top = 8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Arma.values().forEach { arma ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = arma in especialidadesSeleccionadas,
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                especialidadesSeleccionadas.add(arma)
                            } else {
                                especialidadesSeleccionadas.remove(arma)
                            }
                        }
                    )
                    Text(arma.nombre)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (nombre.isNotBlank() && licencia.isNotBlank()) {
                    arbitros.add(Arbitro(arbitros.size.toString(), nombre, licencia, especialidadesSeleccionadas.toList()))
                    nombre = ""
                    licencia = ""
                    especialidadesSeleccionadas.clear()
                    listaActualizada++
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Añadir Árbitro")
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        key(listaActualizada) {
            LazyColumn {
                items(arbitros) { arbitro ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(arbitro.nombre, style = MaterialTheme.typography.titleMedium)
                                Text("Licencia: ${arbitro.numeroLicencia}", style = MaterialTheme.typography.bodySmall)
                                Text("Especialidades: ${arbitro.especialidades.joinToString { it.nombre }}", style = MaterialTheme.typography.bodySmall)
                            }
                            IconButton(onClick = { 
                                arbitros.remove(arbitro)
                                listaActualizada++
                             }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}
