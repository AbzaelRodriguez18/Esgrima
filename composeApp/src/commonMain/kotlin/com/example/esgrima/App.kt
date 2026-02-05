package com.example.esgrima

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.esgrima.model.Competicion
import com.example.esgrima.ui.*

enum class Pantalla(val titulo: String) {
    Login("Autenticación"),
    Configuracion("Configuración"),
    Tiradores("Tiradores"),
    Arbitros("Árbitros"),
    Poules("Generar Poules"),
    Resultados("Resultados Poules"),
    Clasificacion("Clasificación"),
    Tablon("Tablón Final")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    var pantallaActual by remember { mutableStateOf(Pantalla.Login) }
    var estaAutenticado by remember { mutableStateOf(false) }
    val competicion = remember { mutableStateOf(Competicion()) }

    MaterialTheme {
        if (!estaAutenticado) {
            LoginScreen(onLoginSuccess = { estaAutenticado = true; pantallaActual = Pantalla.Configuracion })
        } else {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(pantallaActual.titulo) },
                        actions = {
                            IconButton(onClick = { estaAutenticado = false; pantallaActual = Pantalla.Login }) {
                                Icon(Icons.Filled.ExitToApp, contentDescription = "Cerrar sesión")
                            }
                        }
                    )
                },
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            selected = pantallaActual == Pantalla.Configuracion,
                            onClick = { pantallaActual = Pantalla.Configuracion },
                            icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
                            label = { Text("Inicio") }
                        )
                        NavigationBarItem(
                            selected = pantallaActual == Pantalla.Tiradores,
                            onClick = { pantallaActual = Pantalla.Tiradores },
                            icon = { Icon(Icons.Filled.Person, contentDescription = "Tiradores") },
                            label = { Text("Tiradores") }
                        )
                         NavigationBarItem(
                            selected = pantallaActual == Pantalla.Arbitros,
                            onClick = { pantallaActual = Pantalla.Arbitros },
                            icon = { Icon(Icons.Filled.Face, contentDescription = "Árbitros") },
                            label = { Text("Árbitros") }
                        )
                        NavigationBarItem(
                            selected = pantallaActual == Pantalla.Poules,
                            onClick = { pantallaActual = Pantalla.Poules },
                            icon = { Icon(Icons.Filled.Build, contentDescription = "Poules") },
                            label = { Text("Poules") }
                        )
                        NavigationBarItem(
                            selected = pantallaActual == Pantalla.Resultados,
                            onClick = { pantallaActual = Pantalla.Resultados },
                            icon = { Icon(Icons.Filled.Edit, contentDescription = "Resultados") },
                            label = { Text("Resultados") }
                        )
                        NavigationBarItem(
                            selected = pantallaActual == Pantalla.Clasificacion,
                            onClick = { pantallaActual = Pantalla.Clasificacion },
                            icon = { Icon(Icons.Filled.List, contentDescription = "Clasificación") },
                            label = { Text("Clasif.") }
                        )
                        NavigationBarItem(
                            selected = pantallaActual == Pantalla.Tablon,
                            onClick = { pantallaActual = Pantalla.Tablon },
                            icon = { Icon(Icons.Filled.Star, contentDescription = "Tablón") },
                            label = { Text("Tablón") }
                        )
                    }
                }
            ) { innerPadding ->
                Surface(modifier = Modifier.padding(innerPadding)) {
                    when (pantallaActual) {
                        Pantalla.Login -> {
                        }
                        Pantalla.Configuracion -> ConfiguracionCompeticionScreen(competicion.value)
                        Pantalla.Tiradores -> GestionTiradoresScreen(competicion.value.tiradores)
                        Pantalla.Arbitros -> GestionArbitrosScreen(competicion.value.arbitros)
                        Pantalla.Poules -> CalculadoraPoulesScreen(competicion.value)
                        Pantalla.Resultados -> ResultadosPoulesScreen(competicion.value)
                        Pantalla.Clasificacion -> ClasificacionScreen(competicion.value)
                        Pantalla.Tablon -> TablonEliminacionScreen(competicion.value)
                    }
                }
            }
        }
    }
}
