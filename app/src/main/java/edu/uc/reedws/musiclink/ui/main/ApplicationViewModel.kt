package edu.uc.reedws.musiclink.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import edu.uc.reedws.musiclink.dto.PlaylistDTO
import edu.uc.reedws.musiclink.service.PlaylistService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApplicationViewModel(application: Application) : AndroidViewModel(application) {
    private var _playlistService: PlaylistService = PlaylistService(application)
    lateinit var playlists: LiveData<List<PlaylistDTO>>

    init {
        fetchPlaylists()
    }

    fun createPlaylist(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _playlistService.createPlaylist(name)
            fetchPlaylists()
        }
    }

    private fun fetchPlaylists() {
        viewModelScope.launch {
            playlists = _playlistService.fetchPlaylists()
        }
    }
}