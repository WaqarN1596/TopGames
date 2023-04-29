package com.waqar.topgames.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.waqar.topgames.Model.Game
import com.waqar.topgames.R
import com.waqar.topgames.ui.theme.TopGamesTheme
import com.waqar.topgames.viewModel.GameViewModel
import com.waqar.topgames.viewModel.MainViewModel
import com.waqar.topgames.viewModel.SearchWidgetState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, mainViewModel: MainViewModel, gameViewModel1: GameViewModel = viewModel()) {


    val searchWidgetState by mainViewModel.searchWidgetState
    val searchTextState by mainViewModel.searchTextState

    TopGamesTheme {
        Scaffold(
            modifier = Modifier,
            topBar = {
                MainAppBar(
                    searchWidgetState = searchWidgetState,
                    searchTextState = searchTextState,
                    onTextChange = {
                        mainViewModel.updateSearchTextState(newValue = it)
                    },
                    onCloseClicked = {
                        mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                    },
                    onSearchClicked = {

                        gameViewModel1.getGameList(platform = null)
                    },
                    onSearchTriggered = {
                        mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                    }
                )
            },

            ) { padding ->
            Box(Modifier.padding(padding)) {
                GameList(onClick = {
                    val item = Game(
                        it.developer,
                        it.freetogameProfileUrl,
                        it.gameUrl,
                        it.genre,
                        it.id,
                        it.platform,
                        it.publisher,
                        it.releaseDate,
                        it.shortDescription,
                        it.thumbnail,
                        it.title
                    )
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "item",
                        value = item
                    )
                    navController.navigate(Screen.Detail.route)
                }, platform = "all")
            }
        }
    }
}

@Composable
fun GameList(onClick: (Game) -> Unit, viewModel: GameViewModel = viewModel(), platform: String?, ) {
    viewModel.getGameList(platform = platform)


    LazyColumn {
        items(items = viewModel.gameListResponse) { item ->
            GameItem(game = item, onClick)


        }
    }
}

@Composable
fun MainAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            DefaultAppBar(
                onSearchClicked = onSearchTriggered
            )
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(onSearchClicked: () -> Unit, gameViewModel1: GameViewModel = viewModel()) {

    var showMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text("Top Games", fontSize = 25.sp)
        },
        actions = {
            IconButton(onClick = {onSearchClicked()}
            ){
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(
                    painter = painterResource(id = R.drawable.round_filter_list_24),
                    contentDescription = "Filter"
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
                modifier = Modifier,

            ) {
                MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
                    DropdownMenuItem(
                        text = { Text("All") },
                        onClick = { gameViewModel1.getGameList("all") }
                    )
                    DropdownMenuItem(
                        text = { Text("PC") },
                        onClick = { gameViewModel1.getGameList(platform = "pc") })
                    DropdownMenuItem(
                        text = { Text(text = "Browser") },
                        onClick = { gameViewModel1.getGameList(platform = "browser") })

                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                fontSize = 20.sp
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent
                ,cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            ))
    }
}
