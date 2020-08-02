package edu.uc.reedws.musiclink.dto

import kaaes.spotify.webapi.android.models.Track

data class SongDTO(
    var title: String,
    var album: String,
    var artist: String,
    var track: Track
) {
    override fun toString() = "$title, $album, $artist"
}