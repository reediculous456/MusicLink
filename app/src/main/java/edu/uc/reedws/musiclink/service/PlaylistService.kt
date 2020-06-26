package edu.uc.reedws.musiclink.service

import android.app.Application
import android.util.Log
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import edu.uc.reedws.musiclink.dao.AppDatabase
import edu.uc.reedws.musiclink.dao.ILocalPlaylistDAO
import edu.uc.reedws.musiclink.dto.PlaylistDTO
import java.lang.Exception

class PlaylistService(application: Application) {
    private val application = application

    fun fetchPlaylists(): MutableLiveData<ArrayList<PlaylistDTO>> {
        return MutableLiveData()
    }

    fun createPlaylist(name: String): PlaylistDTO {
        var playlist = PlaylistDTO(name)
        savePlaylist(playlist)
        return playlist
    }

    private fun savePlaylist(playlist: PlaylistDTO) {
        try {
            var localPlaylistDAO = getLocalPlaylistDAO()
            localPlaylistDAO.savePlaylist(playlist!!)
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
    }

    internal fun getLocalPlaylistDAO(): ILocalPlaylistDAO {
        val db = Room.databaseBuilder(application, AppDatabase::class.java, "db").build()
        return db.localPlaylistDAO()
    }
}