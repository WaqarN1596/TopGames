package com.waqar.topgames.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.waqar.topgames.Model.Game

@Composable
fun GameItem(game: Game, onClick: (Game) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                onClick(game)
            },
        elevation = CardDefaults.elevatedCardElevation()


    ) {

        Row {
            Image(
                painter = rememberAsyncImagePainter(game.thumbnail),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(155.dp)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(15.dp)),
                contentDescription = game.shortDescription,

                )
            Column(Modifier.padding(vertical = 15.dp, horizontal = 10.dp)) {
                Text(text = game.title, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = game.publisher, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = game.developer)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = game.genre)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = game.releaseDate)
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}