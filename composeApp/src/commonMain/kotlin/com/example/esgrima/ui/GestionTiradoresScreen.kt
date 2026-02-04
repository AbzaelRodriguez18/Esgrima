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
import com.example.esgrima.model.Tirador

@Composable
fun GestionTiradoresScreen(tiradores: MutableList<Tirador>) {
    var nombre by remember { mutableStateOf("") }
    var club by remember { mutableStateOf("") }
    var licencia by remember { mutableStateOf("") }
    
    // Usamos un State para forzar la recomposición cuando cambia la lista mutable
    var listaActualizada by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Gestión de Tiradores", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre Completo") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = club,
            onValueChange = { club = it },
            label = { Text("Club") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = licencia,
            onValueChange = { licencia = it },
            label = { Text("Número de Licencia") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Button(
            onClick = {
                if (nombre.isNotBlank() && club.isNotBlank() && licencia.isNotBlank()) {
                    tiradores.add(Tirador(tiradores.size.toString(), nombre, club, licencia))
                    nombre = ""
                    club = ""
                    licencia = ""
                    listaActualizada++
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Añadir Tirador")
        }

        Spacer(modifier = Modifier.height(16.dp))

        key(listaActualizada) {
            LazyColumn {
                items(tiradores) { tirador ->
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
                                Text(tirador.nombre, style = MaterialTheme.typography.titleMedium)
                                Text(tirador.club, style = MaterialTheme.typography.bodySmall)
                                Text("Licencia: ${tirador.numeroLicencia}", style = MaterialTheme.typography.bodySmall)
                            }
                            IconButton(onClick = { 
                                tiradores.remove(tirador)
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
