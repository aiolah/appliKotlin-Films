package com.example.premiereapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.premiereapplication.ui.theme.VertDeau

@Composable
fun Krokmou() {
    Image(
        painterResource(id = R.drawable.krokmou),
        contentDescription = "Photo de profil Krokmou",
        modifier = Modifier
            .clip(CircleShape)
            .size(175.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun Titre(content: String) {
    Text(
        text = content,
        fontSize = 35.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(20.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun Header() {
    Krokmou()
    Titre(content = "Aïolah Vaïti")
}

@Composable
fun SousTitre(content: String) {
    Text(
        text = content,
        fontSize = 15.sp,
        fontWeight = FontWeight.Light,
        textAlign = TextAlign.Center
    )
}

@Composable
fun SousTitreItalique(content: String) {
    Text(
        text = content,
        fontSize = 15.sp,
        fontWeight = FontWeight.Light,
        modifier = Modifier.padding(2.dp),
        fontStyle = FontStyle.Italic,
        textAlign = TextAlign.Center
    )
}

@Composable
fun SousTitres() {
    SousTitre(content = "Étudiante en 3e année de BUT MMI")
    SousTitreItalique(content = "Parcours développement web et dispositifs interactifs")
}

@Composable
fun Link(content: String) {
    Text(
        text = content,
        textAlign = TextAlign.Left
    )
}

@Composable
fun IconMail() {
    Image(
        painterResource(id = R.drawable.mail),
        contentDescription = "Icône mail",
        modifier = Modifier.size(25.dp)
    )
}

@Composable
fun IconWeb() {
    Image(
        painterResource(id = R.drawable.web),
        contentDescription = "Icône web",
        modifier = Modifier.size(25.dp)
    )
}

@Composable
fun Reseaux() {
    Row {
        IconMail()
        Link(content = "   aiolah.vaiti@gmail.com", )
    }

    Spacer(modifier = Modifier.height(5.dp))

    Row() {
        IconWeb()
        Link(content = "   aiolah-vaiti.fr")
    }
}

@Composable
fun ButtonExplorer(navController: NavHostController) {
    Button(
        onClick = { navController.navigate("films") },
        colors = ButtonDefaults.buttonColors(backgroundColor = VertDeau)
    ) {
        Text(text = "Explorer", color = Color.White)
    }
}

@Composable
fun Profil(classes: WindowSizeClass, navController: NavHostController) {
    val classeHauteur = classes.heightSizeClass
    val classeLargeur = classes.widthSizeClass
    when (classeLargeur) {
        // Téléphone en mode portrait
        WindowWidthSizeClass.Compact ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(50.dp)
            ) {
                Header()
                SousTitres()

                Spacer(modifier = Modifier.height(35.dp))

                Column() {
                    Reseaux()
                }

                Spacer(modifier = Modifier.height(35.dp))

                ButtonExplorer(navController)
            }
        // Téléphone en mode paysage
        else ->
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxSize()) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Header()
                    SousTitres()
                }

                Column(verticalArrangement = Arrangement.Center) {
                    Reseaux()
                    Spacer(modifier = Modifier.height(20.dp))
                    ButtonExplorer(navController)
                }

            }
    }
}