package com.example.premiereapplication

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.premiereapplication.ui.theme.PremiereApplicationTheme
import com.example.premiereapplication.ui.theme.VertDeau
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)

            PremiereApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    Navigation(windowSizeClass)
                }
            }
        }
    }
}

sealed class Destination(val destination: String, val label: String, val icon: Int, val description: String) {
    object Profil: Destination("profil", "Mon Profil", R.drawable.user, "Profil")
    object Films: Destination("films", "Films", R.drawable.films, "Films")
    object Film: Destination("film/{id}", "Film", R.drawable.films, "Détails du film")
    object SearchFilms: Destination("searchfilms", "SearchFilms", R.drawable.films, "Recherche films")
    object ResultFilm: Destination("resultfilm/{id}", "ResultFilm", R.drawable.films, "Résultat film")
    object Series: Destination("series", "Séries", R.drawable.series, "Séries")
    object Serie: Destination("serie/{id}", "Série", R.drawable.series, "Détails de la série")
    object SearchSeries: Destination("searchseries", "SearchSeries", R.drawable.films, "Recherche films")
    object ResultSerie: Destination("resultserie/{id}", "ResultFilm", R.drawable.films, "Résultat film")

    object Actors : Destination("actors", "Acteurs", R.drawable.acteurs, "Acteurs")
    object Actor : Destination("actor/{id}", "Acteur", R.drawable.acteurs, "Détails d'un acteur")
    object SearchActors: Destination("searchactors", "SearchActors", R.drawable.acteurs, "Recherche acteurs")
    object ResultActor: Destination("resultactor/{id}", "ResultActor", R.drawable.acteurs, "Résultat acteur")
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController();
    val navBackStackEntry by navController.currentBackStackEntryAsState();

    // currentDestination représente l'étiquette de la destination actuelle
    var currentDestination = navBackStackEntry?.destination?.route;

    val destinations = listOf(
        Destination.Profil,
        Destination.Films,
        Destination.Series,
        Destination.Actors
    );

    val viewModel = MainViewModel()

    var showSearchBar by remember { mutableStateOf(false) }
    var showFloatingButton by remember { mutableStateOf(true) }

    // text = Texte qui va s'afficher au fur et à mesure que la personne écrit
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    var view = "portrait"

    val classeHauteur = windowSizeClass.heightSizeClass
    val classeLargeur = windowSizeClass.widthSizeClass
    var selectedItem by remember { mutableStateOf(0) }

    when (classeLargeur) {
        // Téléphone en mode portrait
        WindowWidthSizeClass.Compact ->
            // Le Scaffold rajoute des fonctionnalités : bottomBar (barre de navigation) et NavHost (ce qu'il y a au-dessus de la bottomBar)
            // C'est le Scaffold qui va charger le composant Profil
            Scaffold(
                topBar = {
                    if (currentDestination != "profil") {
                        if (showSearchBar == true) {
                            SearchBar(
                                query = query,
                                onQueryChange = {
                                    query = it
                                },
                                onSearch = {
                                    active = false
                                    if (currentDestination == "films") {
                                        navController.navigate(Destination.SearchFilms.destination)
                                    } else if (currentDestination == "series") {
                                        navController.navigate(Destination.SearchSeries.destination)
                                    } else if (currentDestination == "actors") {
                                        navController.navigate(Destination.SearchActors.destination)
                                    }
                                },
                                active = active,
                                onActiveChange = {
                                    active = it
                                },
                                placeholder = {
                                    if (currentDestination == "films") {
                                        Text("Barbie")
                                    } else if (currentDestination == "series") {
                                        Text("L'Attaque des Titans")
                                    } else if (currentDestination == "actors") {
                                        Text("Eddie Redmayne")
                                    }
                                },
                                leadingIcon = {
                                    IconButton(
                                        onClick = {
                                            val previousBackStackEntry =
                                                navController.previousBackStackEntry
                                            val previousDestination =
                                                previousBackStackEntry?.destination
                                            val previousRoute = previousDestination?.route

                                            // Si la route précédente est une route principale alors on n'affiche plus la search bar
                                            if (previousRoute == "films" || previousRoute == "series" || previousRoute == "actors") {
                                                showSearchBar = false
                                            }

                                            // Si on clique sur la flèche retour de la barre de recherche et qu'on est sur une page principale, on ne l'affiche plus. Sinon, on revient en arrière
                                            if(currentDestination == "films" || currentDestination == "series" || currentDestination == "actors") {
                                                showSearchBar = false
                                            }
                                            else {
                                                navController.popBackStack()
                                            }
                                        }
                                    ) {
                                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                                    }
                                },
                                trailingIcon = {
                                    Icon(
                                        Icons.Default.MoreVert,
                                        contentDescription = null
                                    )
                                },
                                colors = SearchBarDefaults.colors(
                                    containerColor = Color.LightGray,
                                    dividerColor = Color.LightGray,
                                    inputFieldColors = TextFieldDefaults.colors()
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                            }
                        } else {
                            TopAppBar(
                                colors = topAppBarColors(
                                    containerColor = VertDeau,
                                    titleContentColor = Color.White,
                                ),
                                title = {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.padding(7.dp)
                                    ) {
                                        Text("FilmsApp")

                                        if (currentDestination == "films" || currentDestination == "film/{id}") {
                                            Text(" - Films")
                                        } else if (currentDestination == "series" || currentDestination == "serie/{id}") {
                                            Text(" - Séries")
                                        }
                                        if (currentDestination == "actors") {
                                            Text(" - Acteurs")
                                        }
                                    }
                                },
                                actions = {
                                    IconButton(onClick = { showSearchBar = true; query = "" }) {
                                        Image(
                                            painterResource(id = R.drawable.loupe),
                                            contentDescription = "Icône loupe"
                                        )
                                    }
                                }
                            )
                        }
                    }
                },
                bottomBar = {
                    // Si la vue courante n'est pas la page Profil, alors on affiche la barre de navigation
                    if (currentDestination != "profil") {
                        BottomNavigation(backgroundColor = VertDeau) {
                            destinations.forEach { screen ->
                                BottomNavigationItem(
                                icon = {
                                    Icon(
                                        painter = painterResource(screen.icon),
                                        screen.description,
                                        modifier = Modifier.fillMaxSize(0.42F)
                                    )
                                },
                                label = { Text(screen.label) },
                                selected =
                                currentDestination == screen.destination,
                                onClick = {
                                    query = ""
                                    showSearchBar = false
                                    navController.navigate(screen.destination)
                                })
                            }
                        }
                    }
                }) { innerPadding ->
                NavHost(
                    navController, startDestination = Destination.Profil.destination,
                    Modifier.padding(innerPadding)
                ) {
                    // Pages principales
                    composable(Destination.Profil.destination) {
                        Profil(
                            windowSizeClass,
                            navController
                        )
                    }
                    composable(Destination.Films.destination) {
                        Films(
                            viewModel,
                            navController,
                            view,
                            numberColumns(view)
                        )
                    }
                    composable(Destination.Series.destination) {
                        Series(
                            viewModel,
                            navController,
                            view,
                            numberColumns(view)
                        )
                    }
                    composable(Destination.Actors.destination) {
                        Actors(
                            viewModel,
                            navController,
                            view,
                            numberColumns(view)
                        )
                    }

                    // Détails films, séries et acteurs
                    composable(
                        Destination.Film.destination,
                        arguments = listOf(navArgument("id") { type = NavType.StringType })
                    ) { navBackStackEntry ->
                        /* Extracting the id from the route */
                        var id = navBackStackEntry.arguments?.getString("id")
                        Film(viewModel, id, navController, view, numberColumns(view))
                    }
                    composable(
                        Destination.Serie.destination,
                        arguments = listOf(navArgument("id") { type = NavType.StringType })
                    ) { navBackStackEntry ->
                        val id = navBackStackEntry.arguments?.getString("id")
                        Serie(viewModel, id)
                    }
                    composable(
                        Destination.Actor.destination,
                        arguments = listOf(navArgument("id") { type = NavType.StringType })
                    ) { navBackStackEntry ->
                        var id = navBackStackEntry.arguments?.getString("id")
                        Actor(viewModel, id, navController, view, numberColumns(view))
                    }

                    // Résultats de recherche films et séries
                    composable(Destination.SearchFilms.destination) {
                        SearchFilms(
                            viewModel,
                            navController,
                            query,
                            view,
                            numberColumns(view)
                        )
                    }
                    composable(
                        Destination.ResultFilm.destination,
                        arguments = listOf(navArgument("id") { type = NavType.StringType })
                    ) { navBackStackEntry ->
                        val id = navBackStackEntry.arguments?.getString("id")
                        ResultFilm(viewModel, id, navController, view, numberColumns(view))
                    }
                    composable(Destination.SearchSeries.destination) {
                        SearchSeries(
                            viewModel,
                            navController,
                            query,
                            view,
                            numberColumns(view)
                        )
                    }
                    composable(
                        Destination.ResultSerie.destination,
                        arguments = listOf(navArgument("id") { type = NavType.StringType })
                    ) { navBackStackEntry ->
                        val id = navBackStackEntry.arguments?.getString("id")
                        ResultSerie(viewModel, id)
                    }
                    composable(Destination.SearchActors.destination) {
                        SearchActors(
                            viewModel,
                            navController,
                            query,
                            view,
                            numberColumns(view)
                        )
                    }
                    composable(
                        Destination.ResultActor.destination,
                        arguments = listOf(navArgument("id") { type = NavType.StringType })
                    ) { navBackStackEntry ->
                        val id = navBackStackEntry.arguments?.getString("id")
                        ResultActor(viewModel, id, navController, view, numberColumns(view))
                    }
                }
            }

        else ->
            // Téléphone en mode paysage
            Row {
                view = "paysage"

                Scaffold(
                    topBar = {
                        if (currentDestination != "profil") {
                            if (showSearchBar == true) {
                                SearchBar(
                                    query = query,
                                    onQueryChange = {
                                        query = it
                                    },
                                    onSearch = {
                                        active = false
                                        if (currentDestination == "films") {
                                            navController.navigate(Destination.SearchFilms.destination)
                                        } else if (currentDestination == "series") {
                                            navController.navigate(Destination.SearchSeries.destination)
                                        } else if (currentDestination == "actors") {
                                            navController.navigate(Destination.SearchActors.destination)
                                        }
                                    },
                                    active = active,
                                    onActiveChange = {
                                        active = it
                                    },
                                    placeholder = {
                                        if (currentDestination == "films") {
                                            Text("Barbie")
                                        } else if (currentDestination == "series") {
                                            Text("L'Attaque des Titans")
                                        } else if (currentDestination == "actors") {
                                            Text("Eddie Redmayne")
                                        }
                                    },
                                    leadingIcon = {
                                        IconButton(
                                            onClick = {
                                                val previousBackStackEntry =
                                                    navController.previousBackStackEntry
                                                val previousDestination =
                                                    previousBackStackEntry?.destination
                                                val previousRoute = previousDestination?.route

                                                // Si la route précédente est une route principale alors on n'affiche plus la search bar
                                                if (previousRoute == "films" || previousRoute == "series" || previousRoute == "actors") {
                                                    showSearchBar = false
                                                }

                                                // Si on clique sur la flèche retour de la barre de recherche et qu'on est sur une page principale, on ne l'affiche plus. Sinon, on revient en arrière
                                                if(currentDestination == "films" || currentDestination == "series" || currentDestination == "actors") {
                                                    showSearchBar = false
                                                }
                                                else {
                                                    navController.popBackStack()
                                                }
                                            }
                                        ) {
                                            Icon(Icons.Default.ArrowBack, contentDescription = null)
                                        }
                                    },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Default.MoreVert,
                                            contentDescription = null
                                        )
                                    },
                                    content = {},
                                    colors = SearchBarDefaults.colors(
                                        containerColor = Color.LightGray,
                                        dividerColor = Color.LightGray,
                                        inputFieldColors = TextFieldDefaults.colors()
                                    ),
                                    modifier = Modifier.fillMaxWidth().padding(30.dp, 0.dp)
                                )
                            }
                        }
                    },
                    floatingActionButton = {
                        if(currentDestination != "profil" && showSearchBar == false) {
                            FloatingActionButton(
                                onClick = { showSearchBar = true; showFloatingButton = false; query = "" },
                                content = {
                                    Icon(Icons.Filled.Search, "Barre de recherche")
                                },
                                containerColor = VertDeau,
                                contentColor = Color.White,
                                modifier = Modifier
                                    .align(Alignment.Bottom)
                            )
                        }
                    }
                ) {
                    Row {
                        if (currentDestination != "profil") {
                            NavigationRail {
                                destinations.forEachIndexed { index, item ->
                                    NavigationRailItem(
                                        icon = {
                                            Icon(
                                                painter = painterResource(item.icon),
                                                item.description
                                            )
                                        },
                                        label = { Text(item.label) },
                                        selected = selectedItem == index,
                                        onClick = { selectedItem = index; navController.navigate(item.destination) }
                                    )
                                }
                            }
                        }

                        NavHost(navController, startDestination = Destination.Profil.destination) {
                            // Pages principales
                            composable(Destination.Profil.destination) {
                                Profil(
                                    windowSizeClass,
                                    navController
                                )
                            }
                            composable(Destination.Films.destination) {
                                Films(
                                    viewModel,
                                    navController,
                                    view,
                                    numberColumns(view)
                                )
                            }
                            composable(Destination.Series.destination) {
                                Series(
                                    viewModel,
                                    navController,
                                    view,
                                    numberColumns(view)
                                )
                            }
                            composable(Destination.Actors.destination) {
                                Actors(
                                    viewModel,
                                    navController,
                                    view,
                                    numberColumns(view)
                                )
                            }

                            // Détails films, séries et acteurs
                            composable(
                                Destination.Film.destination,
                                arguments = listOf(navArgument("id") { type = NavType.StringType })
                            ) { navBackStackEntry ->
                                /* Extracting the id from the route */
                                var id = navBackStackEntry.arguments?.getString("id")
                                Film(viewModel, id, navController, view, numberColumns(view))
                            }
                            composable(
                                Destination.Serie.destination,
                                arguments = listOf(navArgument("id") { type = NavType.StringType })
                            ) { navBackStackEntry ->
                                val id = navBackStackEntry.arguments?.getString("id")
                                Serie(viewModel, id)
                            }
                            composable(
                                Destination.Actor.destination,
                                arguments = listOf(navArgument("id") { type = NavType.StringType })
                            ) { navBackStackEntry ->
                                var id = navBackStackEntry.arguments?.getString("id")
                                Actor(viewModel, id, navController, view, numberColumns(view))
                            }

                            // Résultats de recherche films et séries
                            composable(Destination.SearchFilms.destination) {
                                SearchFilms(
                                    viewModel,
                                    navController,
                                    query,
                                    view,
                                    numberColumns(view)
                                )
                            }
                            composable(
                                Destination.ResultFilm.destination,
                                arguments = listOf(navArgument("id") { type = NavType.StringType })
                            ) { navBackStackEntry ->
                                val id = navBackStackEntry.arguments?.getString("id")
                                ResultFilm(viewModel, id, navController, view, numberColumns(view))
                            }
                            composable(Destination.SearchSeries.destination) {
                                SearchSeries(
                                    viewModel,
                                    navController,
                                    query,
                                    view,
                                    numberColumns(view)
                                )
                            }
                            composable(
                                Destination.ResultSerie.destination,
                                arguments = listOf(navArgument("id") { type = NavType.StringType })
                            ) { navBackStackEntry ->
                                val id = navBackStackEntry.arguments?.getString("id")
                                ResultSerie(viewModel, id)
                            }
                            composable(Destination.SearchActors.destination) {
                                SearchActors(
                                    viewModel,
                                    navController,
                                    query,
                                    view,
                                    numberColumns(view)
                                )
                            }
                            composable(
                                Destination.ResultActor.destination,
                                arguments = listOf(navArgument("id") { type = NavType.StringType })
                            ) { navBackStackEntry ->
                                val id = navBackStackEntry.arguments?.getString("id")
                                ResultActor(viewModel, id, navController, view, numberColumns(view))
                            }
                        }
                    }
                }
            }
    }
}

fun numberColumns(view: String): Int {
    var numberColumns = 0

    if(view == "portrait")
    {
        numberColumns = 2
    }
    else if(view == "paysage") {
        numberColumns = 3
    }

    return numberColumns
}