package edu.uc.reedws.musiclink.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.uc.reedws.musiclink.dto.PlaylistDTO

class MainViewModel : ViewModel() {
    var playlists: MutableLiveData<ArrayList<PlaylistDTO>> =
        MutableLiveData<ArrayList<PlaylistDTO>>()

    // TODO: Implement the ViewModel
}
