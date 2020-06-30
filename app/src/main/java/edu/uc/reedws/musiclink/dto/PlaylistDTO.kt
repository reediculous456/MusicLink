package edu.uc.reedws.musiclink.dto

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName="playlists", indices = [Index("name"), Index("songs"), Index("ownerId"), Index("id")])
data class PlaylistDTO(var name: String = "", var songs: String = "", var ownerId: String = "", @PrimaryKey(autoGenerate = true) var id: Int = 0) {
    override fun toString() = name
}