package edu.uc.reedws.musiclink.service

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
    private final val accessToken = "BQCFobsZv7I8tMolyRY6h3UtkmIkA_Ew7fHc25MjCe4O8Qdws55RpzRkGX1APhOl5Ih6A2LjbZ68NkbU_ME"
    private val api = SpotifyApi()
    private var service: SpotifyService

    init {
        api.setAccessToken(accessToken)
        service = api.service
    }

    suspend fun searchSongByName(name: String): TracksPager {
        return suspendCoroutine <TracksPager> {
            service.searchTracks(name, object: Callback<TracksPager?> {
                override fun success(t: TracksPager?, response: Response?) {
                    if(t === null) {
                        it.resumeWithException(Exception("No response found"))
                    }
                    it.resume(t!!)
                }

                override fun failure(error: RetrofitError?) {
                    it.resumeWithException(Exception(error));
                }
            })
        }
    }

    suspend fun getSongById(id: String) {
        suspendCoroutine <Track?> {
            service.getTrack(id, object: Callback<Track?> {
                override fun success(t: Track?, response: Response?) {
                    it.resume(t)
                }

                override fun failure(error: RetrofitError?) {
                    it.resumeWithException(Exception(error));
                }
            })
        }
    }
}