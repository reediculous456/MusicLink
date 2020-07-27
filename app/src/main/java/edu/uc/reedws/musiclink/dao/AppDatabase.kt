package edu.uc.reedws.musiclink.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.uc.reedws.musiclink.dto.PlaylistDTO

@Database(entities = [PlaylistDTO::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun localPlaylistDAO(): ILocalPlaylistDAO
}

//Adding note so GitHub will let me make an initial commit