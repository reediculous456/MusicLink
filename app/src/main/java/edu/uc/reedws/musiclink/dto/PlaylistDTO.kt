package edu.uc.reedws.musiclink.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class PlaylistDTO(
    var name: String = "",
    var Songs: String = "",
    var ownerId: String = "",
    @PrimaryKey(autoGenerate = true) var id: Int = 0
) {
    override fun toString() = name
}