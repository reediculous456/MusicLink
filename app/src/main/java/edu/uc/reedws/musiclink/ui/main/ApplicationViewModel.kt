package edu.uc.reedws.musiclink.ui.main

import android.app.Application
import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import edu.uc.reedws.musiclink.dto.PlaylistDTO
import edu.uc.reedws.musiclink.service.PlaylistService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ApplicationViewModel(application: Application) : AndroidViewModel(application) {
    private var _playlistService: PlaylistService = PlaylistService(application)
    lateinit var playlists: LiveData<List<PlaylistDTO>>
    private var storageRef = FirebaseStorage.getInstance().reference

    init {
        fetchPlaylists()
    }

    suspend fun uploadProfilePhoto(uri: Uri, user: FirebaseUser): String {
        return suspendCoroutine<String> { cont ->
            val imageRef = storageRef.child("images/${user.uid}/${uri.lastPathSegment}")
            imageRef.putFile(uri).apply {
                addOnSuccessListener {
                    imageRef.downloadUrl.apply {
                        addOnSuccessListener { downloadUri ->
                            val profileUpdate = UserProfileChangeRequest.Builder().run {
                                setPhotoUri(downloadUri)
                                build()
                            }
                            user.updateProfile(profileUpdate).apply {
                                addOnSuccessListener {
                                    cont.resume(downloadUri.toString())
                                }
                                addOnFailureListener {
                                    Log.e(TAG, it.message)
                                    cont.resumeWithException(Exception(it))
                                }
                            }
                        }
                        addOnFailureListener {
                            Log.e(TAG, it.message)
                            cont.resumeWithException(Exception(it))
                        }
                    }
                }
                addOnFailureListener {
                    Log.e(TAG, it.message)
                    cont.resumeWithException(Exception(it))
                }
            }
        }
    }

    fun createPlaylist(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _playlistService.createPlaylist(name)
            fetchPlaylists()
        }
    }

    private fun fetchPlaylists() {
        viewModelScope.launch {
            playlists = _playlistService.fetchPlaylists()
        }
    }
}