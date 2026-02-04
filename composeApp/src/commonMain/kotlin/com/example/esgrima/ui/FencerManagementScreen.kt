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
fun FencerManagementScreen() {
    var name by remember { mutableStateOf("") }
    var club by remember { mutableStateOf("") }
    var license by remember { mutableStateOf("") }
    val fencers = remember { mutableStateListOf<Tirador>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Gestión de Tiradores", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = club,
            onValueChange = { club = it },
            label = { Text("Club") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = license,
            onValueChange = { license = it },
            label = { Text("Número de Licencia") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Button(
            onClick = {
                if (name.isNotBlank() && club.isNotBlank() && license.isNotBlank()) {
                    fencers.add(Tirador(fencers.size.toString(), name, club, license))
                    name = ""
                    club = ""
                    license = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Añadir Tirador")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(fencers) { fencer ->
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
                            Text(fencer.nombre, style = MaterialTheme.typography.titleMedium)
                            Text(fencer.club, style = MaterialTheme.typography.bodySmall)
                            Text("Licencia: ${fencer.numeroLicencia}", style = MaterialTheme.typography.bodySmall)
                        }
                        IconButton(onClick = { fencers.remove(fencer) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
            }
        }
    }
}
