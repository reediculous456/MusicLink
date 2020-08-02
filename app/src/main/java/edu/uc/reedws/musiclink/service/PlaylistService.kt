package edu.uc.reedws.musiclink.service

import android.app.Application
import android.util.Log
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.lifecycle.LiveData
import androidx.room.Room
import edu.uc.reedws.musiclink.dao.AppDatabase
import edu.uc.reedws.musiclink.dao.ILocalPlaylistDAO
import edu.uc.reedws.musiclink.dto.PlaylistDTO

class PlaylistService(application: Application) {
    private val application = application
    private lateinit var localPlaylistDAO: ILocalPlaylistDAO

    init {
        getLocalPlaylistDAO()
    }

    /**
     * Fetch the playlists added by user
     *
     */
    fun fetchPlaylists(): LiveData<List<PlaylistDTO>> {
        return localPlaylistDAO.getPlaylists()
    }

    /**
     * Create the playlist and save the playlist into room database
     *
     */
    suspend fun createPlaylist(name: String): PlaylistDTO {
        val playlist = PlaylistDTO(name)
        try {
            savePlaylist(playlist)
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
        return playlist
    }

    /**
     * Save or insert the new playlist inside room database
     *
     */
    private suspend fun savePlaylist(playlist: PlaylistDTO) {
        try {
            localPlaylistDAO.savePlaylist(playlist)
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
    }

    /**
     * Builds the database instance and retrieves the DAO instance from it
     *
     */
    private fun getLocalPlaylistDAO() {
        val db = Room.databaseBuilder(application, AppDatabase::class.java, "db").build()
        localPlaylistDAO = db.localPlaylistDAO()
    }
}
