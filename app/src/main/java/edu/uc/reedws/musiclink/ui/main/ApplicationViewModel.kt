package edu.uc.reedws.musiclink.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import edu.uc.reedws.musiclink.dto.PlaylistDTO
import edu.uc.reedws.musiclink.dto.SongDTO
import edu.uc.reedws.musiclink.service.PlaylistService
import edu.uc.reedws.musiclink.service.SpotifyService
import kaaes.spotify.webapi.android.models.TracksPager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApplicationViewModel(application: Application) : AndroidViewModel(application) {
    private var _playlistService: PlaylistService = PlaylistService(application)
    private var _spotifyService: SpotifyService = SpotifyService()

    lateinit var playlists: LiveData<List<PlaylistDTO>>
    var songSearch: MutableLiveData<List<SongDTO>> = MutableLiveData()

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

    fun searchSongsByName(name: String) {
        viewModelScope.launch {
            val tracks = _spotifyService.searchSongByName(name)
            songSearch.postValue(tracks)
        }
    }
}