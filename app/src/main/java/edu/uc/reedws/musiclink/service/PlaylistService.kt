package edu.uc.reedws.musiclink.service

import android.app.Application
import android.util.Log
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.lifecycle.LiveData
import androidx.room.Room
import edu.uc.reedws.musiclink.dao.AppDatabase
import edu.uc.reedws.musiclink.dao.ILocalPlaylistDAO
import edu.uc.reedws.musiclink.dto.PlaylistDTO
import java.lang.Exception

class PlaylistService(application: Application) {
    private val application = application
    private lateinit var localPlaylistDAO: ILocalPlaylistDAO

    init {
        getLocalPlaylistDAO()
    }

    fun fetchPlaylists(): LiveData<List<PlaylistDTO>> {
        return localPlaylistDAO.getPlaylists()
    }

    suspend fun createPlaylist(name: String): PlaylistDTO {
        val playlist = PlaylistDTO(name)
        try {
            savePlaylist(playlist)
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
        return playlist
    }

    private suspend fun savePlaylist(playlist: PlaylistDTO) {
        try {
            localPlaylistDAO.savePlaylist(playlist!!)
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
    }

    private fun getLocalPlaylistDAO() {
        val db = Room.databaseBuilder(application, AppDatabase::class.java, "db").build()
        localPlaylistDAO = db.localPlaylistDAO()
    }
}