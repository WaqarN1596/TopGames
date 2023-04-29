package com.waqar.topgames.viewModel

import android.app.DownloadManager.Query
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waqar.topgames.Model.Game
import com.waqar.topgames.Network.ApiService
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    var gameListResponse: List<Game> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    fun getGameList(platform: String?) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val gameList = apiService.getGames(platform = platform)
                gameListResponse = gameList
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}
