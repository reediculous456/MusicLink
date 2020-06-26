package edu.uc.reedws.musiclink.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.uc.reedws.musiclink.dto.PlaylistDTO
import edu.uc.reedws.musiclink.service.PlaylistService
import kotlinx.coroutines.launch

class ApplicationViewModel(application: Application): AndroidViewModel(application) {
    private var _playlistService: PlaylistService = PlaylistService(application)

    fun fetchPlaylists() {
        viewModelScope.launch {
            _playlistService.fetchPlaylists()
        }
    }

    fun createPlaylist(name: String): PlaylistDTO {
        return _playlistService.createPlaylist(name)
    }
}