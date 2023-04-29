package com.waqar.topgames.view


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.waqar.topgames.HyperlinkText
import com.waqar.topgames.Model.Game
import com.waqar.topgames.ui.theme.TopGamesTheme


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailedScreen(navController: NavHostController, result: Game) {
    TopGamesTheme {
        Scaffold(
            modifier = Modifier,
            topBar = {
                TopAppBar(
                    title = { Text(result.title) },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(Icons.Filled.ArrowBack, "Back Arrow")
                        }

                    }
                )
            }
        ) { padding ->
            Column(Modifier.padding(padding)) {
                Card(
                    modifier = Modifier
                        .padding(5.dp)
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                ) {
                    GlideImage(
                        contentScale = ContentScale.FillBounds,
                        model = result.thumbnail,
                        contentDescription = result.shortDescription,
                        modifier = Modifier
                            .padding(7.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                    )

                    Column(modifier = Modifier.padding(vertical = 7.dp, horizontal = 10.dp)) {
                        Text(text = result.shortDescription)
                        Spacer(modifier = Modifier.height(10.dp))
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Developer: ", fontWeight = FontWeight.Bold)
                        Text(text = result.developer)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Genre: ", fontWeight = FontWeight.Bold)
                        Text(text = result.genre)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Platform: ", fontWeight = FontWeight.Bold)
                        Text(text = result.platform)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Publisher: ", fontWeight = FontWeight.Bold)
                        Text(text = result.publisher)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Game Url: ", fontWeight = FontWeight.Bold)
                        HyperlinkText(
                            fullText = result.gameUrl,
                            theWebsite = result.gameUrl,
                            linkPart = result.gameUrl,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Free to Game Url: ", fontWeight = FontWeight.Bold)
                        HyperlinkText(
                            fullText = result.freetogameProfileUrl,
                            theWebsite = result.freetogameProfileUrl,
                            linkPart = result.freetogameProfileUrl,
                        )
                    }
                }
            }
        }
    }
}
