package com.example.esgrima.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.esgrima.model.Competicion

@Composable
fun ResultadosPoulesScreen(competicion: Competicion) {
    if (competicion.poules.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Primero genera las poules en la sección correspondiente.")
        }
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(competicion.poules) { poule ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Poule #${poule.id}", style = MaterialTheme.typography.titleLarge)
                    Text("Pista: ${poule.pista ?: "N/A"} - Árbitro: ${poule.arbitro?.nombre ?: "Sin asignar"}", 
                        style = MaterialTheme.typography.bodySmall)
                    
                    Spacer(modifier = Modifier.height(12.dp))

                    poule.asaltos.forEach { asalto ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(asalto.tirador1.nombre, modifier = Modifier.weight(1f))
                            
                            OutlinedTextField(
                                value = if (asalto.tocados1 == 0) "" else asalto.tocados1.toString(),
                                onValueChange = { 
                                    asalto.tocados1 = it.filter { c -> c.isDigit() }.toIntOrNull() ?: 0
                                },
                                modifier = Modifier.width(60.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                                singleLine = true
                            )
                            
                            Text("-", modifier = Modifier.padding(horizontal = 8.dp))
                            
                            OutlinedTextField(
                                value = if (asalto.tocados2 == 0) "" else asalto.tocados2.toString(),
                                onValueChange = { 
                                    asalto.tocados2 = it.filter { c -> c.isDigit() }.toIntOrNull() ?: 0
                                },
                                modifier = Modifier.width(60.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                                singleLine = true
                            )
                            
                            Text(asalto.tirador2.nombre, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
                        }
                    }
                }
            }
        }
    }
}
