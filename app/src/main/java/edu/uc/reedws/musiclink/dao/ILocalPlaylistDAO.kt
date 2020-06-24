package edu.uc.reedws.musiclink.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import edu.uc.reedws.musiclink.dto.PlaylistDTO

@Dao
interface ILocalPlaylistDAO {

    @Query("SELECT * FROM playlist")
    fun getPlaylists(): LiveData<List<PlaylistDTO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePlaylist(playlist: PlaylistDTO)

    @Delete
    fun deletePlaylist(playlist: PlaylistDTO)
}