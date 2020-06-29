package edu.uc.reedws.musiclink

import org.junit.Test

import org.junit.Assert.*

import edu.uc.reedws.musiclink.dto.PlaylistDTO
import edu.uc.reedws.musiclink.dto.SongDTO

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PlaylistDataUnitTest {
    @Test
    fun playlist_isCreatedWithName() {
        var playlist: PlaylistDTO = PlaylistDTO(name="Rock")
        assertEquals(playlist.name, "Rock")
    }

}
