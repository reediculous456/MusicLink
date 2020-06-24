package edu.uc.reedws.musiclink.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="playlist")
data class PlaylistDTO(var name: String = "", var Songs: ArrayList<SongDTO> = ArrayList(), var ownerId: String = "", @PrimaryKey var id: String = "")