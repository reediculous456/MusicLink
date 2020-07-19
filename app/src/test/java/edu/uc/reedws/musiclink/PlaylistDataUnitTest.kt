package edu.uc.reedws.musiclink

import edu.uc.reedws.musiclink.dto.PlaylistDTO
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PlaylistDataUnitTest {
    @Test
    fun playlist_isCreatedWithName() {
        var playlist: PlaylistDTO = PlaylistDTO(name = "Rock")
        assertEquals(playlist.name, "Rock")
    }

}
