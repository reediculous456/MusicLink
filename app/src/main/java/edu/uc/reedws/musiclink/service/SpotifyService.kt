package edu.uc.reedws.musiclink.service

import kaaes.spotify.webapi.android.SpotifyApi
import kaaes.spotify.webapi.android.SpotifyService
import kaaes.spotify.webapi.android.models.Track

class SpotifyService {
    private final val accessToken = "BQCFobsZv7I8tMolyRY6h3UtkmIkA_Ew7fHc25MjCe4O8Qdws55RpzRkGX1APhOl5Ih6A2LjbZ68NkbU_ME"
    private val api = SpotifyApi()
    private var service: SpotifyService

    init {
        api.setAccessToken(accessToken)
        service = api.service
    }

    fun searchSongByName(name: String): List<Track> {
        return service.searchTracks(name).tracks.items
    }

    fun getSongById(id: String): Track {
        return service.getTrack(id)
    }
}