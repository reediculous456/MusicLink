package edu.uc.reedws.musiclink.service

import edu.uc.reedws.musiclink.dto.SongDTO
import kaaes.spotify.webapi.android.SpotifyApi
import kaaes.spotify.webapi.android.SpotifyService
import kaaes.spotify.webapi.android.models.Track
import kaaes.spotify.webapi.android.models.TracksPager
import retrofit.Callback
import retrofit.RetrofitError
import retrofit.client.Response
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class SpotifyService {
    private val accessToken = "BQAj_yC559VpFWO6CXgHaLxEL8EkqudR-VqOVRasRA8ZoQF-9lRRI-FDayOZ04VVbfCS5whc2TgfXj_NhjA"
    private val api = SpotifyApi()
    private var service: SpotifyService

    init {
        api.setAccessToken(accessToken)
        service = api.service
    }

    /**
     * Search a song track by name
     *
     */
    suspend fun searchSongByName(name: String): List<SongDTO> {
        return suspendCoroutine {
            service.searchTracks(name, object: Callback<TracksPager?> {
                override fun success(t: TracksPager?, response: Response?) {
                    if(t === null) {
                        it.resumeWithException(Exception("No response found"))
                    }
                    val songs: List<SongDTO> = t!!.tracks.items.map { track -> trackToSong(track) }
                    it.resume(songs)
                }

                override fun failure(error: RetrofitError?) {
                    it.resumeWithException(Exception(error))
                }
            })
        }
    }

    /**
     * Get a song's details by id
     *
     */
    suspend fun getSongById(id: String) {
        suspendCoroutine <SongDTO> {
            service.getTrack(id, object: Callback<Track?> {
                override fun success(t: Track?, response: Response?) {
                    if(t == null) {
                        it.resumeWithException(Exception("No response found"))
                    }
                    val song = trackToSong(t!!)
                    it.resume(song)
                }

                override fun failure(error: RetrofitError?) {
                    it.resumeWithException(Exception(error))
                }
            })
        }
    }

    /**
     * Convert a Spotify track object to SongDTO
     *
     */
    private fun trackToSong(track: Track) = SongDTO(
        track.name,
        track.album.name,
        track.artists.joinToString(", ") { it.name },
        track
    )
}