package edu.uc.reedws.musiclink.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.uc.reedws.musiclink.dto.PlaylistDTO
import edu.uc.reedws.musiclink.service.PlaylistService

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var playlists: MutableLiveData<ArrayList<PlaylistDTO>> = MutableLiveData<ArrayList<PlaylistDTO>>()
    var _playlistService: PlaylistService = PlaylistService(application)

    fun fetchPlaylists() {
        playlists = _playlistService.fetchPlaylists()
    }

    fun createPlaylist(name: String) {
        playlists = _playlistService.fetchPlaylists()
    }
    // TODO: Implement the ViewModel
}
