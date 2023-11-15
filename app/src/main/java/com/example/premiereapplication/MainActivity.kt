package com.example.premiereapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.premiereapplication.ui.theme.PremiereApplicationTheme
import com.example.premiereapplication.ui.theme.VertDeau
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)

            PremiereApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Navigation(windowSizeClass)
                }
            }
        }
    }
}

sealed class Destination(val destination: String, val label: String, val icon: Int, val description: String) {
    object Profil : Destination("profil", "Mon Profil", R.drawable.user, "Profil")
    object Films : Destination("films", "Films", R.drawable.films, "Films")
    object Series : Destination("series", "Séries", R.drawable.series, "Séries")
    object Acteurs : Destination("acteurs", "Acteurs", R.drawable.acteurs, "Acteurs")
}

@Composable
fun Navigation(windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController();
    val navBackStackEntry by navController.currentBackStackEntryAsState();

    // currentDestination représente l'étiquette de la destination actuelle
    val currentDestination = navBackStackEntry?.destination?.route;

    val destinations = listOf(Destination.Profil, Destination.Films, Destination.Series, Destination.Acteurs);

    val viewModel: MainViewModel = MainViewModel()

    // Le Scaffold rajoute des fonctionnalités : bottomBar (barre de navigation) et NavHost (ce qu'il y a au-dessus de la bottomBar)
    // C'est le Scaffold qui va charger le composant Profil
    Scaffold(
        bottomBar = {
            // Si la vue courante n'est pas la page Profil, alors on affiche la barre de navigation
            if(currentDestination != "profil") BottomNavigation(backgroundColor = VertDeau) {
            destinations.forEach { screen ->
                BottomNavigationItem(
                    icon = { Icon(painter = painterResource(screen.icon), screen.description, modifier = Modifier.fillMaxSize(0.42F)) },
                    label = { Text(screen.label) },
                    selected =
                    currentDestination == screen.destination,
                    onClick = { navController.navigate(screen.destination) })
            }}
        }) { innerPadding ->
        NavHost(navController, startDestination = Destination.Profil.destination,
            Modifier.padding(innerPadding)) {
            composable(Destination.Profil.destination) { Profil(windowSizeClass, navController) }
            composable(Destination.Films.destination) { Films(viewModel) }
            composable(Destination.Series.destination) { Series() }
            composable(Destination.Acteurs.destination) { Acteurs() }
        }
    }
}