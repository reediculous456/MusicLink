package edu.uc.reedws.musiclink.dto

data class SongDTO(var title: String, var album: String, var artist: String) {
    override fun toString() = "$title $album $artist"
}