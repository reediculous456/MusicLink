package edu.uc.reedws.musiclink

import org.junit.Assert.*

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import edu.uc.reedws.musiclink.dto.PlaylistDTO
import edu.uc.reedws.musiclink.service.PlaylistService
import edu.uc.reedws.musiclink.ui.main.MainViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PlaylistLocalDatabaseTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit var mvm: MainViewModel

    var playlistService = mockk<PlaylistService>()

    private fun createMockData() {
        var playlistData = MutableLiveData<ArrayList<PlaylistDTO>>()
        var allPlaylists = ArrayList<PlaylistDTO>()

        var rockPlaylist = PlaylistDTO("Rock")
        allPlaylists.add(rockPlaylist)
        var rapPlaylist = PlaylistDTO("Rap")
        allPlaylists.add(rapPlaylist)
        var softPlaylist = PlaylistDTO("Soft")
        allPlaylists.add(softPlaylist)

        playlistData.postValue(allPlaylists)

        every { playlistService.fetchPlaylists() } returns playlistData

        mvm._playlistService = playlistService
    }

    @Test
    fun createAndSavePlaylist() {
        var playlistFound = false;
        mvm = MainViewModel()
        createMockData()
        mvm.createPlaylist("Rock")
        mvm.playlists.observeForever {
            assertNotNull(it)
            assertTrue(it.size > 0)
            it.forEach {
                if (it.name == "Rock") {
                    playlistFound = true
                }
            }
        }
        assertTrue(playlistFound)
    }
}